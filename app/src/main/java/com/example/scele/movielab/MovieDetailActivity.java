package com.example.scele.movielab;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView MovieImage, movieBackground;
    private TextView title_movie, desc_movie;
    private FloatingActionButton floatingActionButton;
    private TextView userRating, releaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initializeViews();
    }

    void initializeViews(){

        userRating = findViewById(R.id.rating_Movie_Detail);
        releaseDate = findViewById(R.id.release_date_Movie_Detail);
        floatingActionButton = findViewById(R.id.floatingActionButton_detail_activity);
        MovieImage = findViewById(R.id.iv_poster_activity_movie_detail);
        movieBackground = findViewById(R.id.detail_Movie_Background);
        title_movie = findViewById(R.id.title_Movie_Detail);
        desc_movie = findViewById(R.id.description_Movie_Detail);

        if (getIntent().hasExtra("movie_title")){
            String imageName = getIntent().getStringExtra("movie_title");
            title_movie.setText(imageName);
        }

    }
}
