package com.example.stackoverflowsearchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class DispatcherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatcher);


        Class<?> activityClass;
        try{
            SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFERENCES_NAME,0);
            activityClass = Class.forName(sharedPreferences.getString(MainActivity.ACTIVITY_CLASS_KEY_NAME,MainActivity.class.getName()));

        }
        catch (ClassNotFoundException ex){
            activityClass = MainActivity.class;
        }

        startActivity(new Intent(this, activityClass));
    }
}

