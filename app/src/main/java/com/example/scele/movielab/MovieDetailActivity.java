package com.example.scele.movielab;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.scele.movielab.API.Client;
import com.example.scele.movielab.API.Service;
import com.example.scele.movielab.Adapters.TrailerAdapter;
import com.example.scele.movielab.Models.Trailer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView MovieImage, movieBackground;
    private TextView title_movie, desc_movie;
    private FloatingActionButton floatingActionButton;
    private TextView userRating, releaseDate;

    private static String videoID;

    private RecyclerView recyclerView;
    private TrailerAdapter adapter;
    private List<Trailer> trailerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initializeViews();

        //Logo in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logoaction);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        initViews();
    }

    void initializeViews(){

        releaseDate = findViewById(R.id.release_date_Movie_Detail);
        floatingActionButton = findViewById(R.id.floatingActionButton_detail_activity);
        MovieImage = findViewById(R.id.iv_poster_activity_movie_detail);
        movieBackground = findViewById(R.id.detail_Movie_Background);
        title_movie = findViewById(R.id.title_Movie_Detail);
        desc_movie = findViewById(R.id.description_Movie_Detail);
        floatingActionButton = findViewById(R.id.floatingActionButton_detail_activity);

        if (getIntent().hasExtra("movie_title")){
            String name = getIntent().getStringExtra("movie_title");
            title_movie.setText(name);
            String rate = getIntent().getStringExtra("movie_rate");
            releaseDate.setText(rate);
            desc_movie.setText(getIntent().getStringExtra("movie_description"));
            String path = getIntent().getStringExtra("movie_poster");
            Glide.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500/"+path).into(MovieImage);
           // Glide.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500/"+path).into(movieBackground);
        }

        //Trailer
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // int movie_id = getIntent().getExtras().getInt("video_id");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+ videoID));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }

    private void initViews(){
        trailerList = new ArrayList<>();
        adapter = new TrailerAdapter(this, trailerList);
        recyclerView = findViewById(R.id.recyclerViewDetail);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        loadJSONTrailer();
    }

    private void loadJSONTrailer(){
        int movie_id = getIntent().getExtras().getInt("id");
        try{
            Client Client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<TrailerResponse> call = apiService.getMovieTrailer(movie_id, Constant.API_KEY);
            call.enqueue(new Callback<TrailerResponse>() {
                @Override
                public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                    List<Trailer> trailer = response.body().getResults();
                    videoID = trailer.get(0).getKey();
                    recyclerView.setAdapter(new TrailerAdapter(getApplicationContext(), trailer));
                    recyclerView.smoothScrollToPosition(0);
                }

                @Override
                public void onFailure(Call<TrailerResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(MovieDetailActivity.this, "Error fetching trailer data", Toast.LENGTH_SHORT).show();

                }
            });

        }catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
