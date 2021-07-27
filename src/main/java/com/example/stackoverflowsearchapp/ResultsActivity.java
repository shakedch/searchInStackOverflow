package com.example.stackoverflowsearchapp;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.stackoverflowsearchapp.adapters.PostAdapter;
import com.example.stackoverflowsearchapp.model.PageResult;
import com.example.stackoverflowsearchapp.model.Post;
import com.example.stackoverflowsearchapp.network.GetResultsService;
import com.example.stackoverflowsearchapp.network.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultsActivity extends OptionsMenu {
    GetResultsService resultsService;
    List<Post> posts;
    RecyclerView rvPosts;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        rvPosts = findViewById(R.id.recyclerView);
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFERENCES_NAME,0);
        String keyWord = sharedPreferences.getString(MainActivity.EDIT_TEXT_KEY_SEARCH,"");
        rvPosts.setHasFixedSize(true);
        DividerItemDecoration divider = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(getDrawable(R.drawable.dividerbig));
        rvPosts.addItemDecoration(divider);

        //http client (RETROFIT)
        resultsService = RetrofitInstance.getRetrofitInstance().create(GetResultsService.class);

        resultsService.getPosts("desc","creation","stackoverflow", keyWord).enqueue(new Callback<PageResult>() {
            @Override
            public void onResponse(Call<PageResult> call, Response<PageResult> response) {
                posts = response.body().getPosts();

                PostAdapter adapter = new PostAdapter(posts);
                rvPosts.setAdapter(adapter);

                rvPosts.setLayoutManager(new LinearLayoutManager(ResultsActivity.this));

                /*
                for (Post post:posts){
                    Log.d("posts", post.getTitle());
                }
                Log.d("array size", String.valueOf(posts.size()));*/
            }

            @Override
            public void onFailure(Call<PageResult> call, Throwable t) {
                Log.d("error", t.toString());
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("tat2.2","Results activity was paused");

        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFERENCES_NAME,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MainActivity.ACTIVITY_CLASS_KEY_NAME,getClass().getName());

        editor.commit();
    }
}