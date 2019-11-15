package com.example.scele.movielab;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class WatchListActivity extends AppCompatActivity {

    BottomNavigationView navigationView;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_list);

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


    }
}
