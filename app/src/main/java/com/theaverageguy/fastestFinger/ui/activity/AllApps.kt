package com.theaverageguy.fastestFinger.ui.activity

import android.app.ActivityManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import com.theaverageguy.fastestFinger.R
import com.theaverageguy.fastestFinger.databinding.AllAppsBinding
import com.theaverageguy.fastestFinger.service.musicService
import com.theaverageguy.fastestFinger.modelClasses.AppSharePreference
import com.theaverageguy.fastestFinger.modelClasses.AppsModelClass
import com.theaverageguy.fastestFinger.ui.viewHolder.AppsViewHolder
import com.theaverageguy.fastestFinger.utils.Utils.isOnline
import com.theaverageguy.fastestFinger.utils.Utils.showToast


class AllApps : AppCompatActivity() {

    private lateinit var bind: AllAppsBinding
    private lateinit var myRef: DatabaseReference
    lateinit var database: FirebaseDatabase
    var sharedPreferences: AppSharePreference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = AllAppsBinding.inflate(layoutInflater)
        setContentView(bind.root)

        initAllComponents()
        phoneListener()
    }

    private fun initAllComponents() {
        sharedPreferences = AppSharePreference(this)
        database = FirebaseDatabase.getInstance()
        myRef = database.getReference("Common")
            .child("apps")
        bind.recycler.layoutManager = GridLayoutManager(applicationContext, 2)
    }

    override fun onStart() {
        super.onStart()
        if (isOnline(applicationContext)) {
            showToast(applicationContext, "Please Wait ")
            initRecycler()
        } else {
            onBackPressed()
            showToast(applicationContext, "No Internet Connection")
        }
    }

    private fun initRecycler() {
        val option: FirebaseRecyclerOptions<AppsModelClass> =
            FirebaseRecyclerOptions.Builder<AppsModelClass>()
                .setQuery(myRef.orderByChild("name"), AppsModelClass::class.java)
                .build()
        val recyclerAdapter =
            object : FirebaseRecyclerAdapter<AppsModelClass, AppsViewHolder>(option) {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppsViewHolder {
                    val view =
                        LayoutInflater.from(applicationContext)
                            .inflate(R.layout.show_all_apps, parent, false)
                    return AppsViewHolder(view)
                }

                override fun onBindViewHolder(
                    holder: AppsViewHolder,
                    position: Int,
                    model: AppsModelClass
                ) {
                    holder.name.text = model.name
                    holder.quote.text = model.quote
                    Picasso.get()
                        .load(model.image)
                        .into(holder.image)
                    holder.card.setOnClickListener { click ->
                        operationToPerform(model.link)

                    }
                }


            }

        bind.recycler.adapter = recyclerAdapter
        recyclerAdapter.startListening()
    }

    private fun operationToPerform(link: String) {
        stopService(Intent(applicationContext, musicService::class.java))
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(link)
        startActivity(i)
    }

    private fun phoneListener() {
        val phoneStateListener: PhoneStateListener = object : PhoneStateListener() {
            override fun onCallStateChanged(state: Int, incomingNumber: String) {
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    stopService(Intent(applicationContext, musicService::class.java))
                    //Incoming call: Pause music
                } else if (state == TelephonyManager.CALL_STATE_IDLE) {
                    if (sharedPreferences?.music == true) {
                        startService(Intent(applicationContext, musicService::class.java))
                    } //Not in call: Play music
                } else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                    //stopService(new Intent(getApplicationContext(), musicService.class));
                    //A call is dialing, active or on hold
                }
                super.onCallStateChanged(state, incomingNumber)
            }
        }
        val mgr = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        mgr?.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE)
    }

    override fun onPause() {
        if (this.isFinishing) { //basically BACK was pressed from this activity
            stopService(Intent(applicationContext, musicService::class.java))
            //Toast.makeText(DashboardActivity.this, "YOU PRESSED BACK FROM YOUR 'HOME/MAIN' ACTIVITY", Toast.LENGTH_SHORT).show();
        }
        val context = applicationContext
        val am = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val taskInfo = am.getRunningTasks(1)
        if (!taskInfo.isEmpty()) {
            val topActivity = taskInfo[0].topActivity
            if (topActivity!!.packageName != context.packageName) {
                stopService(Intent(applicationContext, musicService::class.java))
                //Toast.makeText(DashboardActivity.this, "YOU LEFT YOUR APP", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(DashboardActivity.this, "YOU SWITCHED ACTIVITIES WITHIN YOUR APP", Toast.LENGTH_SHORT).show();
            }
        }
        super.onPause()
    }

    override fun onRestart() {
        super.onRestart()
        startMusic()
    }

    private fun startMusic() {
        if (sharedPreferences?.music == true) {
            startService(Intent(applicationContext, musicService::class.java))
        }
    }

}