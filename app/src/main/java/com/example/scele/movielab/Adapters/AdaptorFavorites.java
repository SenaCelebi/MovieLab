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

    private Cursor mCursor;

    Context context;
    MovieItemClickListener movieItemClickListener;


    public AdaptorFavorites(Context context, MovieItemClickListener movieItemClickListener) {
        this.context = context;
        this.movieItemClickListener = movieItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorites,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        mCursor.moveToPosition(i);

        Log.i("test", "mcursor count on bind " + i);

        String title = mCursor.getString(1);
        String rate = mCursor.getString(2);
        String path = mCursor.getString(3);

        Log.i("test", ""+myViewHolder.getItemId());
        myViewHolder.movie_Title.setText(title);
        myViewHolder.movie_Rate.setText(rate);
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+path).into(myViewHolder.poster);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null){
            return 0;
        }
        else{
            int count = mCursor.getCount();
            Log.i("test", count + "get item count");
            return count;
        }
    }

    public void swapCursor(Cursor newCursor){
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView movie_Title, movie_Rate;
        private ImageView poster;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            movie_Title = itemView.findViewById(R.id.tv_movie_name_favorites_item_name);
            movie_Rate = itemView.findViewById(R.id.tv_movie_rate_favorites_item);
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

