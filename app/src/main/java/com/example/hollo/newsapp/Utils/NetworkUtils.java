package com.example.hollo.newsapp.Utils;


import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    final static String BASE_URL
            = "https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=0424595f37c14145b0f567fdfc4c8793";
    final static String PARAM_QUERY
            = "q";
    final static String PARAM_SORT
            ="sort";
   // final static String sortBy = "author";

    public static URL buildUrl(String SearchQuery) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
              //  .appendQueryParameter(PARAM_QUERY,SearchQuery)
              //  .appendQueryParameter(PARAM_SORT, sortBy)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();


            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


}
