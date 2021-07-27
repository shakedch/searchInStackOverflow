package com.example.stackoverflowsearchapp.adapters;

import android.content.Context;
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

import com.example.stackoverflowsearchapp.HistoryActivity;
import com.example.stackoverflowsearchapp.MainActivity;
import com.example.stackoverflowsearchapp.R;
import com.example.stackoverflowsearchapp.model.Search;


import java.util.List;
import java.util.Objects;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private static List<Search> searchHistory;

    public HistoryAdapter(List<Search> searchHistory) {
        this.searchHistory = searchHistory;
    }


    @NonNull
    @Override
    public HistoryAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        //the parent is actually the RecyclerView

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //by using inflater.inflate() you create your View from your XML file.
        View searchView = inflater.inflate(R.layout.history_card_view, parent, false);

        // Return a new holder instance
        HistoryViewHolder viewHolder = new HistoryViewHolder(searchView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.HistoryViewHolder holder, int position) {
        holder.bind(this.searchHistory.get(position));
    }

    // -2.2. implement getItemCount()
    @Override
    public int getItemCount() {
        return this.searchHistory.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {

        private final TextView keyWord, id; //latitude, longitude
        private final WebView webViewMap;


        public HistoryViewHolder(View searchView) {
            super(searchView);
            //1.1  initialize member variables in constructor
            keyWord = searchView.findViewById(R.id.key_word);
            id = searchView.findViewById(R.id.search_id);
           // latitude = searchView.findViewById(R.id.search_latitude);
          //  longitude = searchView.findViewById(R.id.search_longitude);
            webViewMap = searchView.findViewById(R.id.webViewMap);
        }

        public void bind (Search search){
            keyWord.setText("Key word: " + search.getKeyWord());
            id.setText("Id: " + search.getId());
            webViewMap.setWebViewClient(new WebViewClient());
            WebSettings webSettings = webViewMap.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webViewMap.getSettings().setLoadWithOverviewMode(true);
            webViewMap.getSettings().setUseWideViewPort(true);
            webViewMap.loadUrl("https://www.google.com/maps/search/?api=1&query=" + search.getLatitude() + "," + search.getLongitude());

            itemView.findViewById(R.id.buttonDelete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //remove from recycler view
                    searchHistory.remove(getAdapterPosition());
                    HistoryActivity.deleteSearch(search.getId(),getAdapterPosition(), searchHistory.size());

                    //remove from recycler view
                    //searchHistory.remove(getAdapterPosition());
                   // HistoryActivity.rvSearches.removeViewAt(getAdapterPosition());
                  //  Objects.requireNonNull(HistoryActivity.rvSearches.getAdapter()).notifyItemRemoved(getAdapterPosition());
                   // HistoryActivity.rvSearches.getAdapter().notifyItemRangeChanged(getAdapterPosition(), searchHistory.size());
                }
            });
        }
    }
}

