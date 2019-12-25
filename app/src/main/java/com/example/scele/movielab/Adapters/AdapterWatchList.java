package com.example.scele.movielab.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.scele.movielab.Models.mMovie;
import com.example.scele.movielab.MovieItemClickListener;
import com.example.scele.movielab.R;

import java.util.List;

public class AdapterWatchList extends RecyclerView.Adapter<AdapterWatchList.FavItemViewHolder> {

    private Cursor mCursor;

    Context context;
    List<mMovie> watchlist;
    MovieItemClickListener movieItemClickListener;

    public AdapterWatchList(Context context, MovieItemClickListener movieItemClickListener) {
        this.context = context;
        this.movieItemClickListener = movieItemClickListener;
    }

    @NonNull
    @Override
    public FavItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_watchlist,viewGroup,false);
        return new AdapterWatchList.FavItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavItemViewHolder myViewHolder, int i) {

        mCursor.moveToPosition(i);

        Log.i("test", "mcursor count on bind " + i);

        String title = mCursor.getString(1);
        String path = mCursor.getString(3);

        Log.i("test", ""+myViewHolder.getItemId());
        myViewHolder.movie_Title.setText(title);
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


    public class FavItemViewHolder extends RecyclerView.ViewHolder {

        private TextView movie_Title, movie_rate;
        private ImageView poster;


        public FavItemViewHolder(@NonNull View itemView) {
            super(itemView);
            movie_Title = itemView.findViewById(R.id.tv_movie_name_watchlist_item);
            poster = itemView.findViewById(R.id.iv_movie_poster_watchlist_item);
            movie_rate = itemView.findViewById(R.id.tv_movie_rate_watchlist_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // movieItemClickListener.onMovieClick(favoriteList.get(getAdapterPosition()),poster);
                }
            });
        }
    }
}
