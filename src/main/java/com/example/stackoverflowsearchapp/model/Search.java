package com.example.stackoverflowsearchapp.model;

public class Search {
    private String keyWord;
    private String id;
    private double  latitude;
    private double longitude;

    public String getKeyWord() {
        return keyWord;
    }

    public String getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Search(String id, String keyWord, double latitude, double longitude) {
        this.id = id;
        this.keyWord = keyWord;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
