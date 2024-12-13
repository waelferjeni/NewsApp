package com.example.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class NewsDetails extends AppCompatActivity {
    private String title;
    private String description;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_details);

        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        imageUrl = getIntent().getStringExtra("imageUrl");

        TextView newsTitle = findViewById(R.id.newsTitle);
        TextView newsDescription = findViewById(R.id.newsDescription);
        ImageView newsImage = findViewById(R.id.newsImage);

        newsTitle.setText(title);
        newsDescription.setText(description);
        Glide.with(this)
                .load(imageUrl)
                .into(newsImage);
    }
    
    private void shareNews() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, title);
        shareIntent.putExtra(Intent.EXTRA_TEXT, title + "\n\n" + description);
        startActivity(Intent.createChooser(shareIntent, "Share news via"));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.share) {
            shareNews();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}