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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.scele.movielab.Adapters.MovieAdaptor;
import com.example.scele.movielab.Adapters.MovieAdaptorForItem;
import com.example.scele.movielab.BackgroundTasks.SessionManager;
import com.example.scele.movielab.Models.Movie;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements MovieItemClickListener{

    BottomNavigationView navigationView;
    Intent intent;
    RecyclerView moviesRecycleView;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logoaction);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation_bar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);


        //For Recycle View
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie("Logan",R.drawable.poster1,R.drawable.bk1));
        movieList.add(new Movie("Life of Brian",R.drawable.poster2,R.drawable.bk2));
        movieList.add(new Movie("Life of Brian",R.drawable.poster2, R.drawable.bk2));
        movieList.add(new Movie("Life of Brian",R.drawable.poster2, R.drawable.bk2));
        movieList.add(new Movie("Life of Brian",R.drawable.poster2, R.drawable.bk2));

        moviesRecycleView = findViewById(R.id.recycle_view_search);
        MovieAdaptorForItem movieAdaptor = new MovieAdaptorForItem(context, movieList,this);
        moviesRecycleView.setAdapter(movieAdaptor);
        moviesRecycleView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:

                        intent = new Intent(SearchActivity.this, HomeActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_search:
                        break;

                    case R.id.nav_discuss:

                        intent = new Intent(SearchActivity.this, DiscussionActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_favorites:

                        intent = new Intent(SearchActivity.this, FavoritesActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_watchlist:

                        intent = new Intent(SearchActivity.this, WatchListActivity.class);
                        startActivity(intent);
                        break;
                }

                return false;
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
        SessionManager sm = new SessionManager(this);
        sm.logout();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
        return super.onOptionsItemSelected(item);

    }
    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    public void onMovieClick(Movie movie, ImageView moviePoster) {

        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("title",movie.getTitle());
        intent.putExtra("imgURL",movie.getThumbnail());
        intent.putExtra("imgBack",movie.getPhotoBack());

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SearchActivity.this,
                moviePoster,"sharedName");
        startActivity(intent,options.toBundle());
    }
}
