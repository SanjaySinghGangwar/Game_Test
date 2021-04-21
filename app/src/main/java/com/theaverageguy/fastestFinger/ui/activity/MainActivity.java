package com.theaverageguy.fastestFinger.ui.activity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.theaverageguy.fastestFinger.R;
import com.theaverageguy.fastestFinger.databinding.ActivityMainBinding;
import com.theaverageguy.fastestFinger.service.musicService;
import com.theaverageguy.fastestFinger.modelClasses.AppSharePreference;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int score = 0;
    Boolean alive = false;
    ArrayList<Integer> rand = new ArrayList<>();
    ArrayList<Integer> randWords = new ArrayList<>();
    Random random;
    AppSharePreference sharedPreferences;

    int shuffle;
    int setGrey;
    int checkLife;


    private ActivityMainBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(bind.getRoot());

        intAllComponents();
        intiLevel();

        updateUiEverySecond();
        checkForLife();
        startMusic();
        phoneListener();
    }

    private void startMusic() {
        if (sharedPreferences.getMusic()) {
            startService(new Intent(getApplicationContext(), musicService.class));
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) hideSystemUI();
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }


    private void intiLevel() {
        shuffle = sharedPreferences.getShuffle();
        setGrey = sharedPreferences.getGrey();
        checkLife = sharedPreferences.getCheckLife();
    }


    private void checkForLife() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (alive) {
                    alive = false;
                    checkForLife();
                } else {
                    showGameOver();
                }
            }
        }, checkLife);
    }

    private void intAllComponents() {
        sharedPreferences = new AppSharePreference(this);
        random = new Random();
        bind.zero.setOnClickListener(this);
        bind.two.setOnClickListener(this);
        bind.three.setOnClickListener(this);
        bind.one.setOnClickListener(this);
    }

    private void updateUiEverySecond() {
        rand.clear();
        randWords.clear();

        generateRandom();
        setColorAsPerRandom();
        setGreyColor();


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateUiEverySecond();
            }
        }, shuffle);
    }

    private void setGreyColor() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int greyIndex = random.nextInt(4);
                convertNumberInWordsForGrey(greyIndex);
            }
        }, setGrey);

    }

    private void convertNumberInWordsForGrey(int greyIndex) {
        switch (greyIndex) {
            case 0:
                bind.zero.setBackground(ContextCompat.getDrawable(this, R.drawable.grey));
                break;
            case 1:
                bind.one.setBackground(ContextCompat.getDrawable(this, R.drawable.grey));
                break;
            case 2:
                bind.two.setBackground(ContextCompat.getDrawable(this, R.drawable.grey));
                break;
            case 3:
                bind.three.setBackground(ContextCompat.getDrawable(this, R.drawable.grey));
                break;
        }
    }

    private void setColorAsPerRandom() {

        for (int k = 0; k < rand.size(); k++) {
            convertNumberInWords(rand.get(k));
        }
        setColors();
    }

    private void setColors() {
        bind.zero.setBackground(ContextCompat.getDrawable(this, randWords.get(0)));
        bind.one.setBackground(ContextCompat.getDrawable(this, randWords.get(1)));
        bind.two.setBackground(ContextCompat.getDrawable(this, randWords.get(2)));
        bind.three.setBackground(ContextCompat.getDrawable(this, randWords.get(3)));

    }

    private void convertNumberInWords(int k) {

        switch (k) {
            case 0:
                randWords.add(R.drawable.orange);
                break;
            case 1:
                randWords.add(R.drawable.blue);
                break;
            case 2:
                randWords.add(R.drawable.yellow);
                break;
            case 3:
                randWords.add(R.drawable.green);
                break;
            default:
                break;

        }
    }

    private void generateRandom() {
        for (int i = 0; i < 100; i++) {
            if (rand.size() < 4) {
                int number = random.nextInt(4);
                if (!rand.contains(number)) {
                    rand.add(number);
                }
            } else {
                break;
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zero:
                alive = true;
                sentClickedID(bind.zero.getBackground().getCurrent().getConstantState());
                break;

            case R.id.one:
                alive = true;
                sentClickedID(bind.one.getBackground().getCurrent().getConstantState());
                break;

            case R.id.two:
                alive = true;
                sentClickedID(bind.two.getBackground().getCurrent().getConstantState());
                break;

            case R.id.three:
                alive = true;
                sentClickedID(bind.three.getBackground().getCurrent().getConstantState());
                break;


        }
    }

    private void sentClickedID(Drawable.ConstantState toString) {

        Drawable.ConstantState gray = ContextCompat.getDrawable(this, R.drawable.grey).getConstantState();
        if (!toString.equals(gray)) {
            showGameOver();
        } else {
            score++;
            bind.Score.setText("Score : " + score);
            //Toast.makeText(this, "That WAS A GREY", Toast.LENGTH_SHORT).show();

        }

    }

    private void showGameOver() {
        if(!((MainActivity.this).isFinishing()))
        {
            setHighScore();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            alertDialogBuilder.setMessage("Game Over. Your score is " + score);
            alertDialogBuilder.setNegativeButton("RESTART", (dialog, which) -> {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
            });

            alertDialogBuilder.setPositiveButton("Exit", (dialog, which) -> onBackPressed());
            AlertDialog view = alertDialogBuilder.create();
            view.setCancelable(false);
            view.show();
        }


        //alertDialogBuilder.create().show();

    }

    private void setHighScore() {
        if (score > sharedPreferences.getHighScore()) {
            sharedPreferences.setHighScore(score);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(MainActivity.this, NewGame.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //stopService(new Intent(getApplicationContext(), musicService.class));
        startActivity(intent);
        //super.onBackPressed();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        startMusic();
    }

    @Override
    protected void onPause() {
        if (this.isFinishing()) { //basically BACK was pressed from this activity
            stopService(new Intent(getApplicationContext(), musicService.class));
            //Toast.makeText(DashboardActivity.this, "YOU PRESSED BACK FROM YOUR 'HOME/MAIN' ACTIVITY", Toast.LENGTH_SHORT).show();
        }
        Context context = getApplicationContext();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        if (!taskInfo.isEmpty()) {
            ComponentName topActivity = taskInfo.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                stopService(new Intent(getApplicationContext(), musicService.class));
                //Toast.makeText(DashboardActivity.this, "YOU LEFT YOUR APP", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(DashboardActivity.this, "YOU SWITCHED ACTIVITIES WITHIN YOUR APP", Toast.LENGTH_SHORT).show();
            }
        }
        super.onPause();
    }


    private void phoneListener() {
        PhoneStateListener phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    stopService(new Intent(getApplicationContext(), musicService.class));
                    //Incoming call: Pause music
                } else if (state == TelephonyManager.CALL_STATE_IDLE) {
                    if (sharedPreferences.getMusic()) {
                        startService(new Intent(getApplicationContext(), musicService.class));
                    }                    //Not in call: Play music
                } else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                    //stopService(new Intent(getApplicationContext(), musicService.class));
                    //A call is dialing, active or on hold
                }
                super.onCallStateChanged(state, incomingNumber);
            }
        };
        TelephonyManager mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (mgr != null) {
            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }
}