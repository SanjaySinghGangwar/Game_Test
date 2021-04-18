package com.theaverageguy.fastestFinger.utils;


import android.content.Context;
import android.content.SharedPreferences;

public class AppSharePreference {
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    private String APP_SHARED_PREFS;

    public AppSharePreference(Context mContext) {
        this.sharedPreferences = mContext.getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
        this.APP_SHARED_PREFS = "Fastest_Finger";
    }

    public void clearAllPreferences() {
        editor.clear();
        editor.commit();
    }

    public void clearPreferences(String key) {
        editor.remove(key);
        editor.apply();

    }

    public int getShuffle() {
        return sharedPreferences.getInt("shuffle", 2000);
    }

    public void setShuffle(int value) {
        editor.putInt("shuffle", value);
        editor.commit();
    }

    public int getGrey() {
        return sharedPreferences.getInt("grey", 1000);
    }

    public void setGrey(int value) {
        editor.putInt("grey", value);
        editor.commit();
    }

    public int getCheckLife() {
        return sharedPreferences.getInt("checkLife", 2000);
    }

    public void setCheckLife(int value) {
        editor.putInt("checkLife", value);
        editor.commit();
    }

    public int getHighScore() {
        return sharedPreferences.getInt("highScore", 0);
    }

    public void setHighScore(int value) {
        editor.putInt("highScore", value);
        editor.commit();
    }

    public Boolean getAds() {
        return sharedPreferences.getBoolean("ads", false);
    }

    public void setAds(Boolean value) {
        editor.putBoolean("ads", value);
        editor.commit();
    }

    public Boolean getMusic() {
        return sharedPreferences.getBoolean("music", false);
    }

    public void setMusic(Boolean value) {
        editor.putBoolean("music", value);
        editor.commit();
    }


}
