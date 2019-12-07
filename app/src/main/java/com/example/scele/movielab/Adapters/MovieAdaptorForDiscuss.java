package com.example.scele.movielab.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
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
import com.example.scele.movielab.MovieDetailActivity;
import com.example.scele.movielab.MovieItemClickListener;
import com.example.scele.movielab.R;

import java.util.ArrayList;
import java.util.List;

public class MovieAdaptorForDiscuss extends RecyclerView.Adapter<MovieAdaptorForDiscuss.MyViewHolder2> {

    Context context;
    List<mMovie> Data;
    MovieItemClickListener movieItemClickListener;

    public MovieAdaptorForDiscuss(Context context, List<mMovie> data) {
        this.context = context;
        Data = data;
      //  this.movieItemClickListener = movieItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item_discussion,viewGroup,false);
        return new MovieAdaptorForDiscuss.MyViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 myViewHolder2, final int i) {
        myViewHolder2.movie_Title.setText(Data.get(i).getOriginalTitle());
        String vote = Double.toString(Data.get(i).getVoteAverage());
        myViewHolder2.movie_Year.setText(vote);
        myViewHolder2.movie_desc.setText(Data.get(i).getOverview());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+Data.get(i).getPosterPath()).into(myViewHolder2.poster);

        myViewHolder2.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra("movie_title", Data.get(i).getOriginalTitle());
                intent.putExtra("movie_description", Data.get(i).getOverview());
                intent.putExtra("movie_rate", Data.get(i).getVoteAverage().toString());
                intent.putExtra("movie_poster", Data.get(i).getPosterPath());
                intent.putExtra("id", Data.get(i).getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return Data.size();
    }


    public class MyViewHolder2 extends RecyclerView.ViewHolder {

        private TextView movie_Title, movie_Year, movie_desc;
        private ImageView poster;
        private CardView cardView;


        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            movie_Title = itemView.findViewById(R.id.movie_name_discuss_item);
            movie_Year = itemView.findViewById(R.id.movie_year_discussion_item);
            movie_desc = itemView.findViewById(R.id.movie_description_discussion_item);
            poster = itemView.findViewById(R.id.movie_picture_item_discuss);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}

