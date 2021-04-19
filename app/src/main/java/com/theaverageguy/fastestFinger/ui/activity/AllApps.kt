package com.theaverageguy.fastestFinger.ui.activity

import android.os.Bundle
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
import com.theaverageguy.fastestFinger.ui.modelClasses.AppsModelClass
import com.theaverageguy.fastestFinger.ui.viewHolder.AppsViewHolder

class AllApps : AppCompatActivity() {

    private lateinit var bind: AllAppsBinding
    private lateinit var myRef: DatabaseReference
    lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = AllAppsBinding.inflate(layoutInflater)
        setContentView(bind.root)

        initAllComponents()
    }

    private fun initAllComponents() {
        database = FirebaseDatabase.getInstance()
        myRef = database.getReference("Common")
            .child("apps")
        bind.recycler.layoutManager = GridLayoutManager(applicationContext, 2)
    }

    override fun onStart() {
        super.onStart()
        initRecycler()
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
                }


            }

        bind.recycler.adapter = recyclerAdapter
        recyclerAdapter.startListening()
    }
}