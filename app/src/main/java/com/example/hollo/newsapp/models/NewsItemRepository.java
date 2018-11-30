package com.example.hollo.newsapp.models;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.example.hollo.newsapp.Utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.example.hollo.newsapp.Utils.JsonUtils;
import static com.example.hollo.newsapp.Utils.NetworkUtils.buildURL;

public class NewsItemRepository {
    private static NewsItemDAO mNewsDAO;
    private LiveData<List<NewsItem>> mAllNews;

    public NewsItemRepository(Application application) {
        NewsItemDatabase db = NewsItemDatabase.getDatabase(application.getApplicationContext());
        mNewsDAO = db.newsItemDao();
        mAllNews = mNewsDAO.loadAllNewsItems();
    }

    public LiveData<List<NewsItem>> getAllNewsItems() {
        return mAllNews;
    }

    public static void syncNews() {
        new SyncNewsTask(mNewsDAO).execute(buildURL());
    }

    public static class SyncNewsTask extends AsyncTask<URL, Void, String> {
        private NewsItemDAO mAsyncTaskDao;

        SyncNewsTask(NewsItemDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mAsyncTaskDao.clearAll();
        }

        @Override
        protected String doInBackground(URL... urls) {
            String newsSearchResults = "";
            try {
                newsSearchResults = NetworkUtils.getResponseFromHttpUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newsSearchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("mycode", s);
            super.onPostExecute(s);
            ArrayList<NewsItem> news = JsonUtils.parseNews(s);
            mAsyncTaskDao.insert(news);
        }
    }
}
