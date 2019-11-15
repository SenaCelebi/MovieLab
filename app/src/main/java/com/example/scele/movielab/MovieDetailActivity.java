package com.example.scele.movielab;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView MovieImage, movieBackground;
    private TextView title_movie, desc_movie;
    private FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initializeViews();


    }

    void initializeViews(){
        floatingActionButton = findViewById(R.id.floatingActionButton_detail_activity);
        MovieImage = findViewById(R.id.iv_poster_activity_movie_detail);
        movieBackground = findViewById(R.id.detail_Movie_Background);
        title_movie = findViewById(R.id.title_Movie_Detail);
        desc_movie = findViewById(R.id.description_Movie_Detail);
        String title = getIntent().getExtras().getString("title");
        int imageId = getIntent().getExtras().getInt("imgURL");
        int imageBackId = getIntent().getExtras().getInt("imgBack");
        Glide.with(this).load(imageId).into(MovieImage);
        Glide.with(this).load(imageBackId).into(movieBackground);
        title_movie.setText(title);
        getSupportActionBar().setTitle(title);
        MovieImage.setImageResource(imageId);
       floatingActionButton.setAnimation(AnimationUtils.loadAnimation(this,R.anim.scale_ani));

    }
}
