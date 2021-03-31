package com.theaverageguy.game;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.theaverageguy.game.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int score = 0;
    Boolean alive = false;
    String TAG = "TAG";
    ArrayList<Integer> rand = new ArrayList<>();
    ArrayList<Integer> randWords = new ArrayList<>();
    Random random;

    private ActivityMainBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(bind.getRoot());

        intAllComponents();
        updateUiEverySecond();
        checkForLife();
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
        }, 2000);
    }

    private void intAllComponents() {
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
        }, 2000);
    }

    private void setGreyColor() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int greyIndex = random.nextInt(4);
                convertNumberInWordsForGrey(greyIndex);
            }
        }, 1000);

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
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setMessage("Game Over. Your score is " + score);
        alertDialogBuilder.setNegativeButton("RESTART", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
            }
        });
        alertDialogBuilder.create().show();
    }


}