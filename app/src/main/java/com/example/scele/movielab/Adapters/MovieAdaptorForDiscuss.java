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

public class MovieAdaptorForDiscuss extends RecyclerView.Adapter<MovieAdaptorForDiscuss.MyViewHolder2> {

    Context context;
    List<Movie> Data;
    MovieItemClickListener movieItemClickListener;

    public MovieAdaptorForDiscuss(Context context, List<Movie> data, MovieItemClickListener movieItemClickListener) {
        this.context = context;
        Data = data;
        this.movieItemClickListener = movieItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item_discussion,viewGroup,false);
        return new MovieAdaptorForDiscuss.MyViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 myViewHolder2, int i) {

        myViewHolder2.movie_Title.setText(Data.get(i).getTitle());
        myViewHolder2.poster.setImageResource(Data.get(i).getThumbnail());
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }


    public class MyViewHolder2 extends RecyclerView.ViewHolder {

        private TextView movie_Title;
        private ImageView poster;


        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            movie_Title = itemView.findViewById(R.id.movie_name_discuss_item);
            poster = itemView.findViewById(R.id.movie_picture_item_discuss);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movieItemClickListener.onMovieClick(Data.get(getAdapterPosition()),poster);
                }
            });
        }
    }
}

