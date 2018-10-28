package com.example.hollo.newsapp.Utils;
import com.example.hollo.newsapp.models.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONutils {

    public  static ArrayList<News> makeNewsList(String jsonResult){
        ArrayList<News> newsList = new ArrayList<>();
        try{
            JSONObject mainJSONObject = new JSONObject(jsonResult);
            JSONArray articles = mainJSONObject.getJSONArray("articles");

            for(int i = 0; i<articles.length();i++){
                JSONObject item = articles.getJSONObject(i);
                newsList.add(new News(item.getString("author"),item.getString("title"),item.getString("description"),item.getString("url"),item.getString("urlToImage"),item.getString("publishedAt")));


            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        return newsList;
    }
}
