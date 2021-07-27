package com.example.stackoverflowsearchapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.stackoverflowsearchapp.db.DataBaseHelper;
import com.example.stackoverflowsearchapp.db.DatabaseContract;

public class MainActivity extends OptionsMenu implements LocationListener {
    public static final String PREFERENCES_NAME = "shared pref file";
    public static final String ACTIVITY_CLASS_KEY_NAME = "activity class:";
    public static final String EDIT_TEXT_KEY_SEARCH = "edit text key word";
    private EditText editTextSearch;
    private ProgressBar progressBar;
    private DataBaseHelper dbHelper;
    private SQLiteDatabase dbWrite;

    LocationManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextSearch = findViewById(R.id.editTextSearch);
        progressBar = findViewById(R.id.progressBar);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_NAME, 0);
        String searchWord = sharedPreferences.getString(EDIT_TEXT_KEY_SEARCH, editTextSearch.getText().toString());
        editTextSearch.setText(searchWord);

        dbHelper = new DataBaseHelper(this);
        dbWrite = dbHelper.getWritableDatabase();
    }

    //after clicking the search button
    public void goResults(View view) {
        final String keyWord = editTextSearch.getText().toString();
        if (TextUtils.isEmpty(keyWord)) {
            editTextSearch.setError("Please insert a key word");
            editTextSearch.requestFocus();
            return;
        }
        //save the search in the db
        addSearch(keyWord);
    }

    public void addSearch(String keyword) {

        ContentValues values = new ContentValues();

        //Insert keyword
        values.put(DatabaseContract.History.COLUMN_SEARCH_KEYWORD, keyword);

        //location
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            Toast.makeText(this, "Please enable location permissions", Toast.LENGTH_LONG).show();
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5 * 1000, 5, this);
            Location mikum = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            final double latitude = mikum.getLatitude();
            final double longitude = mikum.getLongitude();

            //TEST האם בכלל נשמר לי נכון
            System.out.println("latitude: " + latitude + " longitude" + longitude);
            Toast.makeText(this, "latitude =" + latitude + "longitude = " + longitude, Toast.LENGTH_SHORT).show();

            //Insert location
            values.put(DatabaseContract.History.COLUMN_LATITUDE, latitude);
            values.put(DatabaseContract.History.COLUMN_LONGITUDE, longitude);


            long newRowID = dbWrite.insert(
                    DatabaseContract.History.TABLE_NAME,
                    null,
                    values
            );

            Toast.makeText(this, "row id =" + newRowID, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), ResultsActivity.class));
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d("tat2.2","main activity pause");

        //save data from edit text
        String editTextStr = editTextSearch.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_NAME,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EDIT_TEXT_KEY_SEARCH, editTextStr);
        editor.putString(ACTIVITY_CLASS_KEY_NAME, getClass().getName());

        //add more data
        editor.apply();
        Log.d("tat2.2",editTextStr+"is saved");
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        final double latitude = location.getLatitude();
        final double longitude = location.getLongitude();
    }
}