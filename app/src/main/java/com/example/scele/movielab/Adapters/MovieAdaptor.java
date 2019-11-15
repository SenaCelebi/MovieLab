package com.example.scele.movielab.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.scele.movielab.Models.Movie;
import com.example.scele.movielab.MovieItemClickListener;
import com.example.scele.movielab.R;

import java.util.ArrayList;
import java.util.List;

public class MovieAdaptor extends RecyclerView.Adapter<MovieAdaptor.MyViewHolder> {

    Context context;
    List<Movie> Data;
    MovieItemClickListener movieItemClickListener;

    public MovieAdaptor(Context context, List<Movie> data, MovieItemClickListener movieItemClickListener) {
        this.context = context;
        Data = data;
        this.movieItemClickListener = movieItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.movie_item,viewGroup,false);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.movie_Title.setText(Data.get(i).getTitle());
        myViewHolder.poster.setImageResource(Data.get(i).getThumbnail());
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView movie_Title;
        private ImageView poster;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            movie_Title = itemView.findViewById(R.id.tv_movie_name_list_item);
            poster = itemView.findViewById(R.id.iv_movie_poster_list_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movieItemClickListener.onMovieClick(Data.get(getAdapterPosition()),poster);
                }
            });
        }
    }
}

