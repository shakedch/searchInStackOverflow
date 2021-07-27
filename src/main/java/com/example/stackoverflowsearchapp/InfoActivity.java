package com.example.stackoverflowsearchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class InfoActivity extends OptionsMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("tat2.2","Info activity was paused");

        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFERENCES_NAME,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MainActivity.ACTIVITY_CLASS_KEY_NAME,getClass().getName());

        editor.commit();
    }
}