package com.example.scele.movielab;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.scele.movielab.Adapters.AdaptorFavorites;
import com.example.scele.movielab.BackgroundTasks.SessionManager;
import com.example.scele.movielab.Data.FavoriteDbHelper;
import com.example.scele.movielab.Models.Movie;
import com.example.scele.movielab.Models.mMovie;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity implements MovieItemClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    SQLiteDatabase sqLiteDatabase;
    mMovie movie;
    List<mMovie> movieList;
    AdaptorFavorites movieAdaptor;

    FavoriteDbHelper favoriteDbHelper;
    BottomNavigationView navigationView;
    Intent intent;
    RecyclerView moviesRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logoaction);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation_bar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        //For Recycle View

        initViews2();




        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:

                        intent = new Intent(FavoritesActivity.this, HomeActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_search:

                        intent = new Intent(FavoritesActivity.this, SearchActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_discuss:

                        intent = new Intent(FavoritesActivity.this, DiscussionActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_favorites:
                        break;

                    case R.id.nav_watchlist:
                        intent = new Intent(FavoritesActivity.this, WatchListActivity.class);
                        startActivity(intent);
                        break;
                }

                return false;
            }
        });


        FavoriteDbHelper favoriteDbHelper = new FavoriteDbHelper(this);
        sqLiteDatabase = favoriteDbHelper.getWritableDatabase();



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
            Intent intent = new Intent(FavoritesActivity.this, SettingsActivity.class);
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


        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(FavoritesActivity.this,
                moviePoster,"sharedName");
        startActivity(intent,options.toBundle());
    }


    public void initViews2(){
        moviesRecycleView = findViewById(R.id.recycle_view_favorites);
        movieList = new ArrayList<>();

        favoriteDbHelper = new FavoriteDbHelper(this);

        favoriteDbHelper.getAllFavorites();
        movieList =  favoriteDbHelper.getAllFavorites();
        movieAdaptor = new  AdaptorFavorites(this,this,movieList);

        moviesRecycleView.setAdapter(movieAdaptor);
        movieAdaptor.notifyDataSetChanged();

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        moviesRecycleView.setLayoutManager(staggeredGridLayoutManager);

        getAllFavorite();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        initViews2();

    }

    private void getAllFavorite(){
        new AsyncTask<Void,Void, Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                movieList.clear();
                movieList.addAll(favoriteDbHelper.getAllFavorites());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                movieAdaptor.notifyDataSetChanged();
            }
        }.execute();
    }
}
