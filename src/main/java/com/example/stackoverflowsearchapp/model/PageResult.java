package com.example.stackoverflowsearchapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PageResult {
    @SerializedName("items")
    @Expose
    private List<Post> posts = null;

    public List<Post> getPosts() {
        return posts;
    }
}
