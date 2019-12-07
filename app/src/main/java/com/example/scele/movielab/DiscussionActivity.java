package com.example.scele.movielab;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.scele.movielab.API.Client;
import com.example.scele.movielab.API.Service;
import com.example.scele.movielab.Adapters.MovieAdaptorForDiscuss;
import com.example.scele.movielab.Adapters.MovieAdaptorForItem;
import com.example.scele.movielab.Adapters.mMovieAdaptor;
import com.example.scele.movielab.BackgroundTasks.SessionManager;
import com.example.scele.movielab.Models.Movie;
import com.example.scele.movielab.Models.mMovie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscussionActivity extends AppCompatActivity implements MovieItemClickListener {

    BottomNavigationView navigationView;
    Intent intent;
    RecyclerView moviesRecycleView;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logoaction);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation_bar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        //For Recycle View
       /* List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie("Logan",R.drawable.poster1,R.drawable.bk1));
        movieList.add(new Movie("Life of Brian",R.drawable.poster2,R.drawable.bk2));
        movieList.add(new Movie("Life of Brian",R.drawable.poster2, R.drawable.bk2));
        movieList.add(new Movie("Life of Brian",R.drawable.poster2, R.drawable.bk2));
        movieList.add(new Movie("Life of Brian",R.drawable.poster2, R.drawable.bk2));*/

        moviesRecycleView = findViewById(R.id.recycle_view_discussion);
        moviesRecycleView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        loadJSONDiscussion();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:

                        intent = new Intent(DiscussionActivity.this, HomeActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_search:

                        intent = new Intent(DiscussionActivity.this, SearchActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_discuss:
                        break;

                    case R.id.nav_favorites:

                        intent = new Intent(DiscussionActivity.this, FavoritesActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_watchlist:

                        intent = new Intent(DiscussionActivity.this, WatchListActivity.class);
                        startActivity(intent);
                        break;
                }

                return false;
            }
        });


    }
    private void loadJSONDiscussion(){

        Client client = new Client();
        Service apiService = Client.getClient().create(Service.class);
        Call<MovieResponse> call = apiService.getPopularMovies(Constant.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                List<mMovie> movies = response.body().getResults();

                moviesRecycleView.setAdapter(new MovieAdaptorForDiscuss(context,movies));

            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

                if(t instanceof IOException){
                    Log.i("asd", t.getMessage());
                }
                else{
                    Log.i("asd", "NOT IO");
                }
                Toast.makeText(DiscussionActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.over_flow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(DiscussionActivity.this, SettingsActivity.class);
            startActivity(intent);
            return super.onOptionsItemSelected(item);
        }

        else {
            SessionManager sm = new SessionManager(this);
            sm.logout();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onMovieClick(Movie movie, ImageView moviePoster) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("title",movie.getTitle());
        intent.putExtra("imgURL",movie.getThumbnail());
        intent.putExtra("imgBack",movie.getPhotoBack());

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(DiscussionActivity.this,
                moviePoster,"sharedName");
        startActivity(intent,options.toBundle());
    }
}
