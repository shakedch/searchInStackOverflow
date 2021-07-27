package com.example.stackoverflowsearchapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;

import java.io.Serializable;

public class Owner implements Serializable {

    @SerializedName("reputation")
    @Expose
    private int reputation;

    public String getProfile_image() {
        return profile_image;
    }

    @SerializedName("user_id")
    @Expose
    private String user_id;

    @SerializedName("user_type")
    @Expose
    private String user_type;

    @SerializedName("profile_image")
    @Expose
    private String profile_image;

    @SerializedName("display_name")
    @Expose
    private String display_name;

    @SerializedName("link")
    @Expose
    private String link;




}
