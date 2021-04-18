package com.theaverageguy.game.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.theaverageguy.game.R
import com.theaverageguy.game.databinding.ActivityNewGameBinding
import com.theaverageguy.game.ui.bottomSheets.HighScore
import com.theaverageguy.game.ui.bottomSheets.level
import com.theaverageguy.game.ui.bottomSheets.settingFragment
import kotlin.system.exitProcess

class NewGame : AppCompatActivity(), View.OnClickListener {

    private lateinit var bind: ActivityNewGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityNewGameBinding.inflate(layoutInflater)
        setContentView(bind.root)
        initAllComponents()
    }

    private fun initAllComponents() {
        bind.newGame.setOnClickListener(this)
        bind.level.setOnClickListener(this)
        bind.exit.setOnClickListener(this)
        bind.highScore.setOnClickListener(this)
        bind.setting.setOnClickListener(this)
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
            R.id.highScore -> {
                val highScore = HighScore();
                highScore.showNow(supportFragmentManager, "High Score")

            }
            R.id.setting -> {
                val setting = settingFragment()
                setting.showNow(supportFragmentManager, "setting")
            }
            R.id.exit -> {
                this@NewGame.finish()
                exitProcess(0)
            }
        }
    }
}