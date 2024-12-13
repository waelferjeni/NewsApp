package com.example.newsapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsJsonParser {
    public List<News> parseNewsResponse(String jsonData) {
        List<News> notesList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray articles = jsonObject.getJSONArray("articles");

            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);
                String title = article.getString("title");
                String description = article.getString("description");
                String imageUrl = article.getString("urlToImage");

                notesList.add(new News(title, description, imageUrl));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return notesList;
    }
}