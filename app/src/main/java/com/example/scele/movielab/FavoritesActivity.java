package com.example.scele.movielab;

import android.app.ActivityOptions;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Loader;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.app.LoaderManager;

import com.example.scele.movielab.Adapters.AdapterWatchList;
import com.example.scele.movielab.Adapters.AdaptorFavorites;
import com.example.scele.movielab.BackgroundTasks.SessionManager;
import com.example.scele.movielab.Data.FavoriteDbHelper;
import com.example.scele.movielab.Database.Contract;
import com.example.scele.movielab.Models.Movie;
import com.example.scele.movielab.Models.mMovie;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity implements MovieItemClickListener, SharedPreferences.OnSharedPreferenceChangeListener,
        LoaderManager.LoaderCallbacks<Cursor> {

    public static final int      FAVORITES_LOADER = 0;
    public static final String[] MAIN_FAVORITES_PROJECTION = {
            Contract.FavoriteEntry.COLUMN_MOVIEID,
            Contract.FavoriteEntry.COLUMN_TITLE,
            Contract.FavoriteEntry.COLUMN_USERRATING,
            Contract.FavoriteEntry.COLUMN_POSTERPATH,
            Contract.FavoriteEntry.COLUMN_OVERVIEW,
    };


    AdaptorFavorites movieAdaptor;

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

        getLoaderManager().initLoader(FAVORITES_LOADER, null, this);

        moviesRecycleView = findViewById(R.id.recycle_view_favorites);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        moviesRecycleView.setLayoutManager(layoutManager);
        moviesRecycleView.setHasFixedSize(true);
        movieAdaptor = new  AdaptorFavorites(this,this);
        moviesRecycleView.setAdapter(movieAdaptor);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        moviesRecycleView.setLayoutManager(staggeredGridLayoutManager);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        initViews2();
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        String sortOrder             = Contract.FavoriteEntry.COLUMN_MOVIEID + " ASC";
        Uri uriF = Contract.FavoriteEntry.F_CONTENT_URI;

        return new CursorLoader(this,
                uriF,
                MAIN_FAVORITES_PROJECTION,
                null,
                null,
                sortOrder);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        movieAdaptor.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        movieAdaptor.swapCursor(null);
    }
}
