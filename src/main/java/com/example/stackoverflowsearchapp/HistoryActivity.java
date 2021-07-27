package com.example.stackoverflowsearchapp;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stackoverflowsearchapp.adapters.HistoryAdapter;
import com.example.stackoverflowsearchapp.db.DataBaseHelper;
import com.example.stackoverflowsearchapp.db.DatabaseContract;
import com.example.stackoverflowsearchapp.model.Post;
import com.example.stackoverflowsearchapp.model.Search;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HistoryActivity extends OptionsMenu {
    private static SQLiteDatabase dbRead;
    private DataBaseHelper dbHelper;
    private List<Search> searchesList;
    public static RecyclerView rvSearches;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        rvSearches = findViewById(R.id.recyclerView);
        searchesList = new ArrayList<>();
        dbHelper = new DataBaseHelper(this);
        dbRead = dbHelper.getReadableDatabase();
        rvSearches.setHasFixedSize(true);
        DividerItemDecoration divider = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(getDrawable(R.drawable.dividerbig));
        rvSearches.addItemDecoration(divider);
        readSearch();
        Log.d("count: ", String.valueOf(DatabaseUtils.queryNumEntries(dbRead, "searches")));

    }

    public void readSearch() {
        //long count = DatabaseUtils.queryNumEntries(dbRead, "searches");
        long maxID = DatabaseUtils.longForQuery( dbHelper.getReadableDatabase(), "SELECT max(searchid) FROM searches", null);
        for (int i = 1; i <= maxID; i++) {
            String[] projection = {DatabaseContract.History.COLUMN_SEARCH_ID, DatabaseContract.History.COLUMN_SEARCH_KEYWORD, DatabaseContract.History.COLUMN_LATITUDE, DatabaseContract.History.COLUMN_LONGITUDE};
            String sortOrder = DatabaseContract.History.COLUMN_SEARCH_ID +" DESC";
            String selection = DatabaseContract.History.COLUMN_SEARCH_ID + " LIKE ?";
            String[] selectionArgs = {String.valueOf(i)};
            Cursor cursor = dbRead.query(
                    DatabaseContract.History.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder
            );

            String result = "";
            cursor.moveToFirst();

            for (int j = 0; j < cursor.getCount(); j++) {
                result = "";
                result += cursor.getInt(0) + ", " + cursor.getString(1)  + ", " + cursor.getDouble(2)+ ", " + cursor.getDouble(3);
                Log.d("sqlite: ", result);
                Search search = new Search(cursor.getString(0), cursor.getString(1), cursor.getDouble(2), cursor.getDouble(3));
                searchesList.add(search);
                cursor.moveToNext();
            }
        }
        HistoryAdapter adapter = new HistoryAdapter(searchesList);
        rvSearches.setAdapter(adapter);
        rvSearches.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));

    }

    public static void deleteSearch(String id, int adapterPosition, List searchesArray) {
        searchesArray.remove(adapterPosition);
        rvSearches.removeViewAt(adapterPosition);
        Objects.requireNonNull(rvSearches.getAdapter()).notifyItemRemoved(adapterPosition);
        rvSearches.getAdapter().notifyItemRangeChanged(adapterPosition, searchesArray.size());

        String selection = DatabaseContract.History.COLUMN_SEARCH_ID + " LIKE ?";
        String[] selectionArgs = { id };
        dbRead.delete(
                DatabaseContract.History.TABLE_NAME,
                selection,
                selectionArgs
        );
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d("tat2.2", "history activity was paused");

        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFERENCES_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MainActivity.ACTIVITY_CLASS_KEY_NAME, getClass().getName());

        editor.commit();
    }


}