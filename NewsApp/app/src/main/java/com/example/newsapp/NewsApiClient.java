package com.example.newsapp;



import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class NewsApiClient {
    private static final String BASE_URL = "https://newsapi.org/v2/top-headlines";
    private static final String API_KEY = "13ef2c0a84a84977adce34d346557beb";
    private OkHttpClient client;

    public NewsApiClient() {
        client = new OkHttpClient();
    }

    public void fetchTopHeadlines(String country,String category, Callback callback) {
        String url = BASE_URL + "?country=" + country + "&apiKey=" + API_KEY+"&category="+category;
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(callback);
    }
}