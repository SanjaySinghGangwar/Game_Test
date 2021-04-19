package com.theaverageguy.fastestFinger.ui.activity

import android.app.ActivityManager
import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.theaverageguy.fastestFinger.R
import com.theaverageguy.fastestFinger.databinding.ActivityNewGameBinding
import com.theaverageguy.fastestFinger.service.musicService
import com.theaverageguy.fastestFinger.ui.bottomSheets.HighScore
import com.theaverageguy.fastestFinger.ui.bottomSheets.Rules
import com.theaverageguy.fastestFinger.ui.bottomSheets.level
import com.theaverageguy.fastestFinger.ui.bottomSheets.settingFragment
import com.theaverageguy.fastestFinger.ui.modelClasses.AppSharePreference
import kotlin.system.exitProcess


class NewGame : AppCompatActivity(), View.OnClickListener {

    private lateinit var bind: ActivityNewGameBinding
    var sharedPreferences: AppSharePreference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityNewGameBinding.inflate(layoutInflater)
        setContentView(bind.root)

        initAllComponents()

        phoneListener()
    }

    private fun startMusic() {
        if (sharedPreferences?.music == true) {
            startService(Intent(applicationContext, musicService::class.java))
        }
    }

    private fun initAllComponents() {
        bind.newGame.setOnClickListener(this)
        bind.level.setOnClickListener(this)
        bind.exit.setOnClickListener(this)
        bind.rules.setOnClickListener(this)
        bind.highScore.setOnClickListener(this)
        bind.setting.setOnClickListener(this)
        bind.share.setOnClickListener(this)
        bind.otherApps.setOnClickListener(this)
        sharedPreferences =
            AppSharePreference(
                this
            )
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.newGame -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            R.id.level -> {
                val level = level();
                level.showNow(supportFragmentManager, "Level")
            }
            R.id.rules -> {
                val rules = Rules();
                rules.showNow(supportFragmentManager, "Rues")
            }
            R.id.highScore -> {
                val highScore = HighScore();
                highScore.showNow(supportFragmentManager, "High Score")

            }
            R.id.setting -> {
                val setting = settingFragment()
                setting.showNow(supportFragmentManager, "setting")
            }
            R.id.otherApps -> {
                val intent = Intent(this, AllApps::class.java)
                startActivity(intent)
            }
            R.id.exit -> {
                this@NewGame.finish()
                exitProcess(0)
            }
            R.id.share -> {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Hey, Just found really awesome game on play store,\n\nhttps://play.google.com/store/apps/details?id=com.theaverageguy.fastestFinger"
                )
                sendIntent.type = "text/plain"
                startActivity(Intent.createChooser(sendIntent, resources.getText(R.string.send_to)))

            }
        }
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
}