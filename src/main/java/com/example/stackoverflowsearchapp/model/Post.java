package com.example.stackoverflowsearchapp.model;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Post implements Serializable {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("link")
    @Expose
    private String link;

    @SerializedName("owner")
    @Expose
    private Owner owner;

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getImagePath() throws JSONException {
        return owner.getProfile_image();
    }
}
