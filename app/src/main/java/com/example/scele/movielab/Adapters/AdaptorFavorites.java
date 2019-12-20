package com.example.scele.movielab.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.scele.movielab.Models.Movie;
import com.example.scele.movielab.Models.mMovie;
import com.example.scele.movielab.MovieItemClickListener;
import com.example.scele.movielab.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptorFavorites extends RecyclerView.Adapter<AdaptorFavorites.MyViewHolder> {

    Context context;
    List<mMovie> favoriteList;
    MovieItemClickListener movieItemClickListener;


    public AdaptorFavorites(Context context, MovieItemClickListener movieItemClickListener, List<mMovie>favoriteList) {
        this.context = context;
        this.movieItemClickListener = movieItemClickListener;
        this.favoriteList = favoriteList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_favorites,viewGroup,false);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.movie_Title.setText(favoriteList.get(i).getOriginalTitle());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+favoriteList.get(i).getPosterPath()).into(myViewHolder.poster);



    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView movie_Title;
        private ImageView poster;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            movie_Title = itemView.findViewById(R.id.tv_movie_name_favorites_item_name);
            poster = itemView.findViewById(R.id.iv_movie_poster_favorites_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // movieItemClickListener.onMovieClick(favoriteList.get(getAdapterPosition()),poster);
                }
            });
        }
    }

}

