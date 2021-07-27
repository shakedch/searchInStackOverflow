package com.example.stackoverflowsearchapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.bumptech.glide.Glide;
        import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.stackoverflowsearchapp.HistoryActivity;
import com.example.stackoverflowsearchapp.R;
import com.example.stackoverflowsearchapp.model.Post;
import com.example.stackoverflowsearchapp.model.Search;

import org.json.JSONException;

import java.util.List;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> postResults;

    public PostAdapter(List<Post> postResults) {
        this.postResults = postResults;
    }


    @NonNull
    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //the parent is actually the RecyclerView

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //by using inflater.inflate() you create your View from your XML file.
        View postView = inflater.inflate(R.layout.post_card_view, parent, false);

        // Return a new holder instance
        PostViewHolder viewHolder = new PostViewHolder(postView);

        return viewHolder;

    }


    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostViewHolder holder, int position) {

        // Get the data model based on position
        Post post = postResults.get(position);

        // Set item views based on your views and data model
        holder.postTitle.setText(post.getTitle().replaceAll("&#39;",  "\'")); // replace all occurrences of &#39; with '
        holder.postLink.setText(post.getLink());

        try {
            Glide
                    .with(holder.itemView.getContext())
                    .load(post.getImagePath())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)   // cache both original & resized image
                    .centerCrop()
                    .into(holder.ownerImage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.postLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(post.getLink()));
                view.getContext().startActivity(browserIntent);
            }
        });

    }

    // -2.2. implement getItemCount()
    @Override
    public int getItemCount() {
        return this.postResults.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder{

        private final TextView postTitle;
        private final TextView  postLink;
        private final ImageView ownerImage;


        public PostViewHolder(View postView) {
            super(postView);
            //1.1  initialize member variables in constructor
            postTitle = postView.findViewById(R.id.post_title);
            postLink =  postView.findViewById(R.id.post_link);
            ownerImage =  postView.findViewById(R.id.owner_image);

        }
    }
}

