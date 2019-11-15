package com.example.scele.movielab;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class HomeActivity extends AppCompatActivity {

    private List<Slidep> slideList;
    private ViewPager viewPager;
    private TabLayout indicator;

    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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



}
