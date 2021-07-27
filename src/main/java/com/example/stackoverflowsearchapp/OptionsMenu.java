package com.example.stackoverflowsearchapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class OptionsMenu extends AppCompatActivity {
    Menu optionsMenu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        optionsMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,optionsMenu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch ((item.getItemId())){
            case R.id.home:

            case R.id.homePage:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
            case R.id.history:
                startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                break;
            case R.id.info:
                startActivity(new Intent(getApplicationContext(), InfoActivity.class));
                break;


            default:
                throw new IllegalStateException("Unexpected value: " + (item.getItemId()));
        }

        return super.onOptionsItemSelected(item);
    }
}