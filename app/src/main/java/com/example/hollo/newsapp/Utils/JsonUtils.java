package com.example.hollo.newsapp.Utils;
import com.example.hollo.newsapp.models.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

//    public  static ArrayList<NewsItem> parseNews(String jsonResult){
public  static ArrayList<NewsItem> parseNews(String jObject){
        ArrayList<NewsItem> newsList = new ArrayList<>();
        try{
            JSONObject mainJSONObject = new JSONObject(String.valueOf(jObject));
            JSONArray articles = mainJSONObject.getJSONArray("articles");

            for(int i = 0; i<articles.length();i++){
                JSONObject item = articles.getJSONObject(i);
                newsList.add(new NewsItem(item.getString("author"),item.getString("title"),item.getString("description"),item.getString("url"),item.getString("urlToImage"),item.getString("publishedAt")));


            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        return newsList;
    }
}
