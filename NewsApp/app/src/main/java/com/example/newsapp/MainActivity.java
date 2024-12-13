package com.example.newsapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView newsRecycler;
    private List<News> newsList = new ArrayList<>();
    private NewsApiClient newsApiClient;
    private NewsJsonParser newsJsonParser;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        newsRecycler = findViewById(R.id.newsList);
        newsRecycler.setLayoutManager(new LinearLayoutManager(this));
        newsRecycler.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        newsRecycler.addItemDecoration(dividerItemDecoration);

        com.example.newsapp.Adapter adapter = new com.example.newsapp.Adapter(newsList, this);
        newsRecycler.setAdapter(adapter);
        newsApiClient = new NewsApiClient();
        newsJsonParser = new NewsJsonParser();

        fetchNews("");

        adapter.notifyDataSetChanged();


    }
    private void fetchNews(String category) {
        newsApiClient.fetchTopHeadlines("us",category, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Failed to fetch news", Toast.LENGTH_SHORT).show());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    newsList = newsJsonParser.parseNewsResponse(responseData);
                    runOnUiThread(() -> updateRecyclerView(newsList));
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_category_spinner);
        Spinner spinner = (Spinner) item.getActionView();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = parent.getItemAtPosition(position).toString();
                fetchNews(selectedCategory);
                adapter.notifyDataSetChanged();

                //List<News> filteredNews = filterNewsByCategory(selectedCategory);
                //updateRecyclerView(filteredNews);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        return true;
    }
    private void updateRecyclerView(List<News> filteredNews) {
        com.example.newsapp.Adapter adapter = new com.example.newsapp.Adapter(filteredNews, this);
        newsRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }



}
