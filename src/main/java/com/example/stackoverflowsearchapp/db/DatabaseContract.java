package com.example.stackoverflowsearchapp.db;

public final class DatabaseContract {

    public static final String DB_NAME="historySearch.db";

    public static abstract class History
    {
        public static final String TABLE_NAME="searches";
        public static final String COLUMN_SEARCH_ID="searchid";
        public static final String COLUMN_SEARCH_KEYWORD="keyword";
        public static final String COLUMN_LATITUDE="latitude";
        public static final String COLUMN_LONGITUDE="longitude";
    }


}
