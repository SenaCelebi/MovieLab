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

        Intent thatStartedInthisActivity = getIntent();
        if (thatStartedInthisActivity.hasExtra("original_title")){

            String thubnail = getIntent().getExtras().getString("poster_path");
            String movieName = getIntent().getExtras().getString("original_title");
            String snopsis = getIntent().getExtras().getString("overview");
            String rating = getIntent().getExtras().getString("vote_average");
            String dateOfRelease = getIntent().getExtras().getString("release_date");

            floatingActionButton.setAnimation(AnimationUtils.loadAnimation(this,R.anim.scale_ani));
            Glide.with(this).load(thubnail).into(MovieImage);
            Glide.with(this).load(thubnail).into(movieBackground);
            title_movie.setText(movieName);
            getSupportActionBar().setTitle(movieName);
            userRating.setText(rating);
            desc_movie.setText(snopsis);
            releaseDate.setText(dateOfRelease);

        }else{
            Toast.makeText(this, "Movie detail api error", Toast.LENGTH_SHORT).show();
        }








    }
}
