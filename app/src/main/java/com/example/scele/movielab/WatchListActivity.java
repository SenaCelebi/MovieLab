package com.example.scele.movielab;

import android.content.Intent;
import android.preference.PreferenceFragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.scele.movielab.BackgroundTasks.SessionManager;
import com.example.scele.movielab.Fragments.TopRatedFragment;
import com.example.scele.movielab.Fragments.WatchListFragment;

public class WatchListActivity extends AppCompatActivity {

    BottomNavigationView navigationView;
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


        //Ikıncı navigation bar icin fragmentlarla olusturulan
        BottomNavigationView bottomNavigationViewUp = findViewById(R.id.up_navigation_bar);
        bottomNavigationViewUp.setOnNavigationItemSelectedListener(navListener);


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            PreferenceFragment fragment = null;
            switch (menuItem.getItemId()){
                case R.id.up_nav_most_rated:
                    fragment = new TopRatedFragment();
                    break;
                case R.id.up_nav_watchlist:
                    fragment = new WatchListFragment();
                    break;
            }
            //
            // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_up,fragment).commit();
            return true;
        }
    };


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
}
