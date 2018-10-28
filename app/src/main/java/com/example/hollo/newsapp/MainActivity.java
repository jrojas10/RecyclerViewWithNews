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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hollo.newsapp.Utils.JSONutils;
import com.example.hollo.newsapp.Utils.NetworkUtils;
import com.example.hollo.newsapp.models.News;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {
    private EditText mSearchBoxEditText;
    private TextView mUrlDisplayTextView;
    private TextView mSearchResultsTextView;
    private ProgressBar mProgressBar;
    private static final String TAG = "MainActivity";

    private static final String SEARCH_QUERY_RESULTS = "searchResults";
    private RecyclerView mRecyclerView;
    private NewsAdapter mAdapter;
    private ArrayList<News> news = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchBoxEditText = (EditText) findViewById(R.id.searchBox);
        mProgressBar = (ProgressBar)findViewById(R.id.progress);


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mAdapter = new NewsAdapter(this,news);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(savedInstanceState != null && savedInstanceState.containsKey(SEARCH_QUERY_RESULTS)){
            String searchResults = savedInstanceState.getString(SEARCH_QUERY_RESULTS);
            populateRecyclerView(searchResults);
        }

    }
    private URL makeQuery(){
        String query = mSearchBoxEditText.getText().toString();
        URL searchURL = NetworkUtils.buildUrl(query);
        String urlString = searchURL.toString();
        Log.d("mycode",urlString);
        return searchURL;
    }
    class QueryTask extends AsyncTask<URL, Void, String> {

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
            news = JSONutils.makeNewsList(s);
            mAdapter.mNews.addAll(news);
            mAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_update) {
            URL url = makeQuery();
            QueryTask task = new QueryTask();
            task.execute(url);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    public void populateRecyclerView(String searchResults){
        Log.d("mycode", searchResults);
        news = JSONutils.makeNewsList(searchResults);
        mAdapter.mNews.addAll(news);
        mAdapter.notifyDataSetChanged();
    }

}
