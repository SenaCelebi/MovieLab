package com.example.scele.movielab;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.scele.movielab.Adapters.MovieAdaptor;
import com.example.scele.movielab.Adapters.MovieAdaptorForItem;
import com.example.scele.movielab.BackgroundTasks.SessionManager;
import com.example.scele.movielab.Models.Movie;
import com.example.scele.movielab.Models.NetworksUtils;
import com.example.scele.movielab.Models.mMovie;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity{

    BottomNavigationView navigationView;
    Intent intent;
    RecyclerView moviesRecycleView;
    Context context = this;

    EditText ed_search;


    private ResponseMovie responseMovie;
    private mMovie mMovie;
    private MovieAdaptorForItem movieAdaptor;
    private List<mMovie> movieList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //Toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logoaction);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation_bar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
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




        ed_search = findViewById(R.id.search_bar);
        responseMovie = new ResponseMovie();
        mMovie = new mMovie();


        movieList = new ArrayList<>();
        moviesRecycleView = findViewById(R.id.recycle_view_search);
        moviesRecycleView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));


        SearchMovies();

    }



    private void SearchMovies() {
        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                showMovie();
            }
        });

    }

    private void showMovie(){
        RestApi restApi = NetworksUtils.getRetrofit().create(RestApi.class);
        Call<ResponseMovie> call = restApi.getMovie(Constant.API_KEY,Constant.LANGUAGE,ed_search.getText().toString());
        call.enqueue(new Callback<ResponseMovie>() {
            @Override
            public void onResponse(Call<ResponseMovie> call, Response<ResponseMovie> response) {

                if(response.isSuccessful()){
                    responseMovie = response.body();

                    if(response.body().total_results !=0){
                        for (int i = 0; i<responseMovie.results.size(); i++){
                            mMovie movie = responseMovie.results.get(i);
                            movieList.add(movie);
                        }

                        Log.v("search", movieList.toString());
                        moviesRecycleView.setAdapter(new MovieAdaptorForItem(context,movieList));
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseMovie> call, Throwable t) {

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
            Intent intent = new Intent(SearchActivity.this, SettingsActivity.class);
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
