package com.example.scele.movielab.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.scele.movielab.API.Client;
import com.example.scele.movielab.API.Service;
import com.example.scele.movielab.Constant;
import com.example.scele.movielab.Models.Trailer;
import com.example.scele.movielab.MovieDetailActivity;
import com.example.scele.movielab.R;
import com.example.scele.movielab.Models.Slidep;
import com.example.scele.movielab.TrailerResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SliderPageAdapter extends PagerAdapter {

    private Context context;
    private List<Slidep> listSlides;
    private List<String> videoKeys;
    private HashMap<String, String> hashMap;


    public SliderPageAdapter(Context context, List<Slidep> listSlides, List<String> videoKeys, HashMap<String, String> hashMap) {
        this.context = context;
        this.listSlides = listSlides;
        this.videoKeys = videoKeys;
        this.hashMap = hashMap;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View slideLayout;
        slideLayout = inflater.inflate(R.layout.slide_item,container, false);

        ImageView slideImage = slideLayout.findViewById(R.id.image_slide);
        TextView slideTitle = slideLayout.findViewById(R.id.title_slide);
        FloatingActionButton fab = slideLayout.findViewById(R.id.floatingActionButton2);
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+listSlides.get(position).getImage()).into(slideImage);
        slideTitle.setText(listSlides.get(position).getTitle());

       // To go trailer from slider
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link;
                link = hashMap.get(listSlides.get(position).getTitle());
                Log.v("hey", Integer.toString(position));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+ link));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        container.addView(slideLayout);
        return slideLayout;
    }

    @Override
    public int getCount() {
        return listSlides.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }



}

