package com.example.scele.movielab;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.scele.movielab.Adapters.AdapterWatchList;
import com.example.scele.movielab.BackgroundTasks.SessionManager;
import com.example.scele.movielab.Database.Contract;
import com.example.scele.movielab.Models.Movie;

public class WatchListActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>,
        MovieItemClickListener {

    public static final int      WATCHLIST_LOADER = 0;
    public static final String[] MAIN_WATCHLIST_PROJECTION = {
            Contract.WatchListEntry.COLUMN_MOVIEID,
            Contract.WatchListEntry.COLUMN_TITLE,
            Contract.WatchListEntry.COLUMN_USERRATING,
            Contract.WatchListEntry.COLUMN_POSTERPATH,
            Contract.WatchListEntry.COLUMN_OVERVIEW,
    };

    private AdapterWatchList mAdapter;
    private RecyclerView watchList;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_list);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logoaction);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation_bar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:

                        intent = new Intent(WatchListActivity.this, HomeActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_search:

                        intent = new Intent(WatchListActivity.this, SearchActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_discuss:

                        intent = new Intent(WatchListActivity.this, DiscussionActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_favorites:

                        intent = new Intent(WatchListActivity.this, FavoritesActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_watchlist:
                        break;
                }

                return false;
            }
        });


        getLoaderManager().initLoader(WATCHLIST_LOADER, null, this);

        watchList = findViewById(R.id.recycle_view_watchlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        watchList.setLayoutManager(layoutManager);
        watchList.setHasFixedSize(true);
        mAdapter                          = new AdapterWatchList(this, this);
        watchList.setAdapter(mAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        watchList.setLayoutManager(staggeredGridLayoutManager);

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
            Intent intent = new Intent(WatchListActivity.this, SettingsActivity.class);
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
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String sortOrder             = Contract.WatchListEntry.COLUMN_MOVIEID + " ASC";
        Uri weatherForLocationUri = Contract.WatchListEntry.W_CONTENT_URI;

        return new CursorLoader(this,
                weatherForLocationUri,
                MAIN_WATCHLIST_PROJECTION,
                null,
                null,
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onMovieClick(Movie movie, ImageView moviePoster) {

    }
}
