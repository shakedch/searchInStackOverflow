package com.example.stackoverflowsearchapp.network;

import com.example.stackoverflowsearchapp.model.PageResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetResultsService {
    //The relative URL of the resource and parameters is specified in the annotation
    @GET("/search")
    Call<PageResult> getPosts(
            @Query("order") String order,
            @Query("sort") String sort,
            @Query("site") String site,
            @Query("tagged") String keyWord);

}

