package com.example.hollo.newsapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;



import com.example.hollo.newsapp.Utils.JsonUtils;
import com.example.hollo.newsapp.Utils.NetworkUtils;
import com.example.hollo.newsapp.models.NewsItem;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {
    private TextView text;
    private TextView mUrlDisplayTextView;
    private TextView mSearchResultsTextView;
    private ProgressBar mProgressBar;
    private static final String TAG = "MainActivity";
    private static final String SEARCH_QUERY_URL_EXTRA = "searchQuery";
    private static final String SEARCH_QUERY_RESULTS = "searchResults";
    private RecyclerView mRecyclerView;
    private NewsAdapter mAdapter;
    private ArrayList<NewsItem> news = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.textBox);
        text.setText(R.string.poweredBy);

        mProgressBar = (ProgressBar)findViewById(R.id.progress);


        mRecyclerView = (RecyclerView) findViewById(R.id.news_recyclerview);
        mAdapter = new NewsAdapter(this,news);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));



    }
    private URL makeQuery(){
       // String query = mSearchBoxEditText.getText().toString();
        URL searchURL = NetworkUtils.buildURL();
        String urlString = searchURL.toString();
        Log.d("mycode",urlString);
        return searchURL;
    }



    class NewsQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            String searchResults = "";
            try {
                searchResults = NetworkUtils.getResponseFromHttpUrl(urls[0]);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return searchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("mycode", s);
            super.onPostExecute(s);
            mProgressBar.setVisibility(View.GONE);
            news = JsonUtils.parseNews(s);
           mAdapter.mNews.addAll(news);
            mAdapter.notifyDataSetChanged();
            Log.d("onPostExecute","news created to json");
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_update) {
            URL url = makeQuery();
            NewsQueryTask task = new NewsQueryTask();
            task.execute(url);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.get_news,menu);
        return true;
    }
//    public void populateRecyclerView(String searchResults){
//        Log.d("mycode", searchResults);
//        news = JsonUtils.parseNews(searchResults);
//        mAdapter.mNews.addAll(news);
//        mAdapter.notifyDataSetChanged();
//    }


}
