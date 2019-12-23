package com.example.scele.movielab.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
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
import com.example.scele.movielab.Data.FavoriteDbHelper;
import com.example.scele.movielab.Models.Movie;
import com.example.scele.movielab.Models.mMovie;
import com.example.scele.movielab.MovieDetailActivity;
import com.example.scele.movielab.MovieItemClickListener;
import com.example.scele.movielab.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdaptorForItem extends RecyclerView.Adapter<MovieAdaptorForItem.MyViewHolder2> {


    Context context;
    List<mMovie> Data;
    FavoriteDbHelper favoriteDbHelper;
    mMovie movie;

    public MovieAdaptorForItem(Context context, List<mMovie> data) {
        this.context = context;
        this.Data = data;
    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item_comments,viewGroup,false);
        return new MovieAdaptorForItem.MyViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder2 myViewHolder2, final int i) {

        myViewHolder2.movie_Title.setText(Data.get(i).getOriginalTitle());
        String vote = Double.toString(Data.get(i).getVoteAverage());
        myViewHolder2.releaseDate.setText(vote);

        Log.v("control", "https://image.tmdb.org/t/p/w500/"+Data.get(i).getPosterPath());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+Data.get(i).getPosterPath()).into(myViewHolder2.poster);



        favoriteDbHelper = new FavoriteDbHelper(context);
        movie = new mMovie();
        myViewHolder2.favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
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

                }else{
                    Log.v("unchecked", "cccccccc");
                    favoriteDbHelper.deleteFavorite(i);
                }
            }
        });


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

        private TextView movie_Title, overview, releaseDate;
        private ImageView poster;
        private CardView cardView;
        private CheckBox favorite;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            movie_Title = itemView.findViewById(R.id.tv_movie_name_comments_item);
            poster = itemView.findViewById(R.id.poster_movie_item_comments);
            releaseDate = itemView.findViewById(R.id.tv_release_date_comments_item);
            cardView = itemView.findViewById(R.id.cardView);
            favorite = itemView.findViewById(R.id.favorite_comments_item);

        }
    }
}

