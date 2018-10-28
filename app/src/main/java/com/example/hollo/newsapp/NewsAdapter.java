package com.example.hollo.newsapp;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.TextView;

import com.example.hollo.newsapp.models.News;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {
Context mContext;
ArrayList<News> mNews;



public NewsAdapter(Context context, ArrayList<News> news){
    this.mContext = context;
    this.mNews = news;
}

    public class NewsHolder extends RecyclerView.ViewHolder{
        TextView author;
        TextView title;
        TextView description;
        TextView url;
        TextView urlToImage;
        TextView publishedAt;

        public NewsHolder(View itemView){
            super(itemView);
            author = (TextView) itemView.findViewById(R.id.author);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            url = (TextView) itemView.findViewById(R.id.url);
            urlToImage = (TextView) itemView.findViewById(R.id.url);
            publishedAt =  (TextView) itemView.findViewById(R.id.publishedAt);
        }
        void bind (final int listIndex) {
            author.setText(mNews.get(listIndex).getAuthor());
            title.setText(mNews.get(listIndex).getTitle());
            description.setText(mNews.get(listIndex).getDescription());
            url.setText(mNews.get(listIndex).getUrl());
            urlToImage.setText(mNews.get(listIndex).getUrlToImage());
            publishedAt.setText(mNews.get(listIndex).getPublishedAt());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String urlString = mNews.get(listIndex).getUrl();
                    Intent intent = new Intent(mContext,WebActivity.class);
                    intent.putExtra("urlString",urlString);
                    mContext.startActivity(intent);

                }
            });
        }

    }
    @Override
    public NewsAdapter.NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.item, parent,shouldAttachToParentImmediately);
        NewsHolder viewHolder = new NewsHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(NewsAdapter.NewsHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }
}
