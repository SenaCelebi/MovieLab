package com.example.scele.movielab.Adapters;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.scele.movielab.Constant;
import com.example.scele.movielab.Data.FavoriteContract;
import com.example.scele.movielab.Data.FavoriteDbHelper;
import com.example.scele.movielab.Database.Contract;
import com.example.scele.movielab.Database.DBProvider;
import com.example.scele.movielab.Models.Movie;
import com.example.scele.movielab.Models.mMovie;
import com.example.scele.movielab.MovieDetailActivity;
import com.example.scele.movielab.MovieItemClickListener;
import com.example.scele.movielab.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.scele.movielab.Database.DBProvider.*;
import static com.example.scele.movielab.R.*;

public class MovieAdaptorForItem extends RecyclerView.Adapter<MovieAdaptorForItem.MyViewHolder2> {

    Context context;
    List<mMovie> Data;
    FavoriteDbHelper favoriteDbHelper;
    mMovie movie;
    private ContentResolver mResolver;
    private SQLiteDatabase sqLiteDatabase;

    public MovieAdaptorForItem(Context context, List<mMovie> data) {
        this.context = context;
        this.Data = data;
        mResolver = context.getContentResolver();
    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout.movie_item_comments,viewGroup,false);
        return new MovieAdaptorForItem.MyViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder2 myViewHolder2, final int i) {

        myViewHolder2.movie_Title.setText(Data.get(i).getOriginalTitle());
        String vote = Double.toString(Data.get(i).getVoteAverage());
        myViewHolder2.releaseDate.setText(vote);

        Log.v("control", "https://image.tmdb.org/t/p/w500/"+Data.get(i).getPosterPath());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+Data.get(i).getPosterPath()).into(myViewHolder2.poster);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        favoriteDbHelper = new FavoriteDbHelper(context);
        movie = new mMovie();

        if (Exists(Data.get(i).getTitle())){
            myViewHolder2.favorite.setChecked(true);
            myViewHolder2.favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){

                    }
                    else {
                        SharedPreferences.Editor editor = (SharedPreferences.Editor) context.getSharedPreferences("com.example.scele.movielab.Adapters.MovieAdaptorForItem", Context.MODE_PRIVATE).edit();
                        editor.putBoolean("Favorite Added", false);
                        editor.commit();

                        Snackbar.make(buttonView, "Removed to Favorite",
                                Snackbar.LENGTH_LONG).show();

                        Log.v("unchecked", "cccccccc");
                        favoriteDbHelper.deleteFavorite(i);
                    }
                }
            });
        }
        else {

            myViewHolder2.favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        SharedPreferences.Editor editor = (SharedPreferences.Editor) context.getSharedPreferences("com.example.scele.movielab.Adapters.MovieAdaptorForItem", Context.MODE_PRIVATE).edit();
                        editor.putBoolean("Favorite Added", true);
                        editor.commit();

                        Snackbar.make(buttonView, "Added to Favorite",
                                Snackbar.LENGTH_LONG).show();

                        Log.v("checked", "asdfg");
                        int movie_id = i;
                        String rate = Data.get(i).getVoteAverage().toString();
                        String pster = Data.get(i).getPosterPath();

                        movie.setId(movie_id);
                        movie.setOriginalTitle(Data.get(i).getOriginalTitle());
                        movie.setPosterPath(pster);
                        movie.setVoteAverage(Double.parseDouble(rate));
                        movie.setOverview(Data.get(i).getOverview());

                        favoriteDbHelper.addMovie(movie);
                    }
                    else {

                    }
                }
            });

        }

        if (isWLExist(Data.get(i).getTitle())){
            myViewHolder2.watchList.setChecked(true);
            myViewHolder2.watchList.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        //Do nothing
                    }
                    else {
                        //Delete

                        String selection = Contract.WatchListEntry.COLUMN_TITLE + " =?";
                        Uri uri = Contract.WatchListEntry.W_CONTENT_URI;
                        String searchItem = Data.get(i).getTitle();
                        String[] selectionArgs = { searchItem } ;
                        mResolver.delete(uri, selection, selectionArgs);

                    }
                }
            });
        }
        else {

            myViewHolder2.watchList.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        int movie_id = i;
                        String rate = Data.get(i).getVoteAverage().toString();
                        String pster = Data.get(i).getPosterPath();

                        movie.setId(movie_id);
                        movie.setOriginalTitle(Data.get(i).getOriginalTitle());
                        movie.setPosterPath(pster);
                        movie.setVoteAverage(Double.parseDouble(rate));
                        movie.setOverview(Data.get(i).getOverview());

                        ContentValues values = new ContentValues();

                        values.put(Contract.WatchListEntry.COLUMN_MOVIEID, movie_id);
                        values.put(Contract.WatchListEntry.COLUMN_POSTERPATH, pster);
                        values.put(Contract.WatchListEntry.COLUMN_TITLE, movie.getOriginalTitle());
                        values.put(Contract.WatchListEntry.COLUMN_OVERVIEW, movie.getOverview());
                        values.put(Contract.WatchListEntry.COLUMN_USERRATING, rate);

                        mResolver.insert(Contract.WatchListEntry.W_CONTENT_URI, values);

                        Log.v("addedto",  movie.getOriginalTitle());
                    }
                    else {
                        //Do nothing
                    }
                }
            });

        }


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

    public boolean Exists(String searchItem){
        String projection [] = {
                FavoriteContract.FavoriteEntry._ID,
                FavoriteContract.FavoriteEntry.COLUMN_MOVIEID,
                FavoriteContract.FavoriteEntry.COLUMN_TITLE,
                FavoriteContract.FavoriteEntry.COLUMN_USERRATING,
                FavoriteContract.FavoriteEntry.COLUMN_POSTERPATH,
                FavoriteContract.FavoriteEntry.COLUMN_OVERVIEW

        };

        sqLiteDatabase = favoriteDbHelper.getWritableDatabase();
        String selection = FavoriteContract.FavoriteEntry.COLUMN_TITLE + " =?";
        String [] selectionArgs = { searchItem };
        Log.v("senan",searchItem.toString());
        String Limit = "1";

        Cursor cursor = sqLiteDatabase.query(FavoriteContract.FavoriteEntry.TABLE_NAME, projection,selection,selectionArgs,null,null,null,Limit);
        boolean exists = (cursor.getCount()>0);
        cursor.close();
        return exists;
    }

    public boolean isWLExist(String searchItem){
        String projection [] = {
                Contract.WatchListEntry._ID,
                Contract.WatchListEntry.COLUMN_MOVIEID,
                Contract.WatchListEntry.COLUMN_TITLE,
                Contract.WatchListEntry.COLUMN_USERRATING,
                Contract.WatchListEntry.COLUMN_POSTERPATH,
                Contract.WatchListEntry.COLUMN_OVERVIEW

        };

        Uri uri = Contract.WatchListEntry.W_CONTENT_URI;
        String selection = Contract.WatchListEntry.COLUMN_TITLE + " =?";
        String [] selectionArgs = { searchItem };
        Log.v("senan",searchItem.toString());
        String Limit = "1";

        Cursor cursor = mResolver.query(uri, projection,selection,selectionArgs,Limit);
        boolean exists = (cursor.getCount()>0);
        cursor.close();
        return exists;
    }


    public class MyViewHolder2 extends RecyclerView.ViewHolder {

        private TextView movie_Title, overview, releaseDate;
        private ImageView poster;
        private CardView cardView;
        private CheckBox favorite, watchList;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            movie_Title = itemView.findViewById(id.tv_movie_name_comments_item);
            poster = itemView.findViewById(id.poster_movie_item_comments);
            releaseDate = itemView.findViewById(id.tv_release_date_comments_item);
            cardView = itemView.findViewById(id.cardView);
            favorite = itemView.findViewById(id.favorite_comments_item);
            watchList = itemView.findViewById(R.id.add_watchlist_comments_item);

        }
    }
}

