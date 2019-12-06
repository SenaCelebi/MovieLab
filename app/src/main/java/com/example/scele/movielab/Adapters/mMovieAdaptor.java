package com.example.scele.movielab.Adapters;

import android.content.Context;
import android.content.Intent;
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
import com.example.scele.movielab.MovieDetailActivity;
import com.example.scele.movielab.MovieItemClickListener;
import com.example.scele.movielab.R;

import java.util.ArrayList;
import java.util.List;

public class mMovieAdaptor extends RecyclerView.Adapter<mMovieAdaptor.MyViewHolder> {

    Context context;
    List<mMovie> Data;

    public mMovieAdaptor(Context context, List<mMovie> data) {
        this.context = context;
        this.Data = data;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.movie_item,viewGroup,false);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.movie_Title.setText(Data.get(i).getOriginalTitle());
        String vote = Double.toString(Data.get(i).getVoteAverage());
        myViewHolder.movie_Rating.setText(vote);
        Log.v("control", "https://image.tmdb.org/t/p/w500/"+Data.get(i).getPosterPath());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+Data.get(i).getPosterPath()).into(myViewHolder.poster);
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView movie_Title, movie_Rating;
        private ImageView poster;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            movie_Title = itemView.findViewById(R.id.tv_movie_name_list_item);
            movie_Rating = itemView.findViewById(R.id.tv_movie_rate_list_item);
            poster = itemView.findViewById(R.id.iv_movie_poster_list_item);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        mMovie clickedDataItems = Data.get(pos);
                        Intent intent = new Intent(context, MovieDetailActivity.class);
                        intent.putExtra("original_title",Data.get(pos).getOriginalTitle());
                        intent.putExtra("poster_path",Data.get(pos).getPosterPath());
                        intent.putExtra("overview",Data.get(pos).getOverview());
                        intent.putExtra("vote_average",Double.toString(Data.get(pos).getVoteAverage()));
                        intent.putExtra("release_date",Data.get(pos).getReleaseDate());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);


                    }
                }
            });

        }
    }

}

