package com.example.scele.movielab;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.example.scele.movielab.Adapters.SliderPageAdapter;
import com.example.scele.movielab.Adapters.mMovieAdaptor;
import com.example.scele.movielab.BackgroundTasks.SessionManager;
import com.example.scele.movielab.Models.Movie;
import com.example.scele.movielab.Models.Slidep;
import com.example.scele.movielab.Models.Trailer;
import com.example.scele.movielab.Models.mMovie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeActivity extends AppCompatActivity implements MovieItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private List<Slidep> slideList;
    private ViewPager viewPager;
    private TabLayout indicator;

    final List<String> videoKeys = new ArrayList<>();
    HashMap<String, String> hashMap = new HashMap<>();

    RecyclerView moviesRecycleView, upcomingMoviesRecycleView;

    Context context = this;
    BottomNavigationView navigationView;
    Intent intent = null;

    private static String image1, image2, image3, image4;
    private static String name1, name2, name3, name4;
    private static int id1, id2, id3, id4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Logo in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logoaction);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        //Bottom Navigation Bar Control
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


        //For Recycle View
        moviesRecycleView = findViewById(R.id.recyclerView2);
        moviesRecycleView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        upcomingMoviesRecycleView = findViewById(R.id.upcoming_movies_recycle_view);
        upcomingMoviesRecycleView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        loadJSON();
        loadJSONforUpcoming();

    }

   private void loadJSON(){

            Client client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<MovieResponse> call = apiService.getNowPlayingMovies(Constant.API_KEY);
            call.enqueue(new Callback<MovieResponse>() {

                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                    List<mMovie> movies = response.body().getResults();

                    moviesRecycleView.setAdapter(new mMovieAdaptor(context,movies));

                }
                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {

                    if(t instanceof IOException){
                        Log.i("asd", t.getMessage());
                    }
                    else{
                        Log.i("asd", "NOT IO");
                    }
                    Toast.makeText(HomeActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            });

    }

    private void loadJSONforUpcoming(){
        Client client = new Client();
        Service apiService = Client.getClient().create(Service.class);
        Call<MovieResponse> call = apiService.getUpcomingMovies(Constant.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<mMovie> movies = response.body().getResults();
                upcomingMoviesRecycleView.setAdapter(new mMovieAdaptor(context,movies));
                //Slider in top of the page
                image1 = movies.get(0).getPosterPath();
                image2 = movies.get(1).getPosterPath();
                image3 = movies.get(2).getPosterPath();
                image4 = movies.get(3).getPosterPath();
                name1 = movies.get(0).getOriginalTitle();
                name2 = movies.get(1).getOriginalTitle();
                name3 = movies.get(2).getOriginalTitle();
                name4 = movies.get(3).getOriginalTitle();
                id1 = movies.get(0).getId();
                id2 = movies.get(1).getId();
                id3 = movies.get(2).getId();
                id4 = movies.get(3).getId();

                loadJSONTrailer(name1, id1);
                loadJSONTrailer(name2,id2);
                loadJSONTrailer(name3, id3);
                loadJSONTrailer(name4, id4);

                viewPager = findViewById(R.id.slider_pager);
                indicator = findViewById(R.id.indicator);
                slideList = new ArrayList<>();
                slideList.add(new Slidep(image1,name1, id1));
                slideList.add(new Slidep(image2,name2, id2));
                slideList.add(new Slidep(image3,name3, id3));
                slideList.add(new Slidep(image4,name4, id4));

                SliderPageAdapter sliderPageAdapter = new SliderPageAdapter(context,slideList, videoKeys, hashMap);
                viewPager.setAdapter(sliderPageAdapter);
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new HomeActivity.SliderTimer(),4000,6000);
                indicator.setupWithViewPager(viewPager,true);

            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

                if(t instanceof IOException){
                    Log.i("asd", t.getMessage());
                }
                else{
                    Log.i("asd", "NOT IO");
                }
                Toast.makeText(HomeActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // To show trailer in slider
    private void loadJSONTrailer(final String name, int movie_id){
        try{
            Client Client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<TrailerResponse> call = apiService.getMovieTrailer(movie_id, Constant.API_KEY);
            call.enqueue(new Callback<TrailerResponse>() {
                @Override
                public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                    List<Trailer> trailer = response.body().getResults();
                    String videoID;
                    videoID = trailer.get(0).getKey();
                    videoKeys.add(videoID);
                    hashMap.put(name, videoID);
                }

                @Override
                public void onFailure(Call<TrailerResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                }
            });

        }catch (Exception e){
            Log.d("Error", e.getMessage());

        }
    }


    @Override
    public void onMovieClick(Movie movie, ImageView moviePoster) {

        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("title",movie.getTitle());
        intent.putExtra("imgURL",movie.getThumbnail());
        intent.putExtra("imgBack",movie.getPhotoBack());

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(HomeActivity.this,
                moviePoster,"sharedName");
        startActivity(intent,options.toBundle());

    }

    @Override
    public void onRefresh() {

    }

    //Timer For Slider
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

    //Logout
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
            Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
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
