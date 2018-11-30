package com.example.hollo.newsapp.models;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface NewsItemDAO {
    @Insert
    void insert(List<NewsItem> items);

    @Query("DELETE FROM news_item")
    void clearAll();

    @Query("SELECT * FROM news_item ORDER BY id ASC")
    LiveData<List<NewsItem>> loadAllNewsItems();
}
