package com.example.scele.movielab;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Menu;

import android.view.MenuInflater;

import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.scele.movielab.BackgroundTasks.SessionManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class HomeActivity extends AppCompatActivity {

    //Home

    private List<Slidep> slideList;
    private ViewPager viewPager;
    private TabLayout indicator;

    Context context = this;

    BottomNavigationView navigationView;
    Intent intent = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logoaction);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logoaction);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        viewPager = findViewById(R.id.slider_pager);
        indicator = findViewById(R.id.indicator);

        slideList = new ArrayList<>();
        slideList.add(new Slidep(R.drawable.image1,"Movie Title \n details"));
        slideList.add(new Slidep(R.drawable.image2,"Movie Title \n details"));
        slideList.add(new Slidep(R.drawable.image1,"Movie Title \n details"));
        slideList.add(new Slidep(R.drawable.image2,"Movie Title \n details"));

        SliderPageAdapter sliderPageAdapter = new SliderPageAdapter(context,slideList);
        viewPager.setAdapter(sliderPageAdapter);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new HomeActivity.SliderTimer(),4000,6000);
        indicator.setupWithViewPager(viewPager,true);


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation_bar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.nav_home:

                        break;

                    case R.id.nav_search:

                        intent = new Intent(HomeActivity.this, SearchActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_discuss:

                        intent = new Intent(HomeActivity.this, DiscussionActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_favorites:

                        intent = new Intent(HomeActivity.this, FavoritesActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_watchlist:

                        intent = new Intent(HomeActivity.this, WatchListActivity.class);
                        startActivity(intent);
                        break;
                }


                return false;
            }
        });
    }

    class SliderTimer extends TimerTask{

        @Override
        public void run() {
            HomeActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewPager.getCurrentItem()<slideList.size()-1){

                        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                    }else{
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
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


}
