package com.example.stackoverflowsearchapp.db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper  extends SQLiteOpenHelper {

    private static final  String TEXT_TYPE =" TEXT"; //TODO check
    private static final String SQL_CREATE_SEARCHES=
            "CREATE TABLE " + DatabaseContract.History.TABLE_NAME + " (" +
                    DatabaseContract.History.COLUMN_SEARCH_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + DatabaseContract.History.COLUMN_SEARCH_KEYWORD + TEXT_TYPE +","
                    + DatabaseContract.History.COLUMN_LATITUDE +" DOUBLE,"
                    + DatabaseContract.History.COLUMN_LONGITUDE +" DOUBLE )";

    private static final String SQL_DELETE_SEARCHES =
            "DROP TABLE IF EXISTS " + DatabaseContract.History.TABLE_NAME;

    public DataBaseHelper(@Nullable Context context) {
        super(context, DatabaseContract.DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_SEARCHES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_SEARCHES);
        onCreate(db);
    }
}
