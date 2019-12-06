package com.example.scele.movielab.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.scele.movielab.Constant;
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
    public void onBindViewHolder(@NonNull MyViewHolder2 myViewHolder2, int i) {

       mMovie setMovie = Data.get(i);
       myViewHolder2.overview.setText(setMovie.overview);
       myViewHolder2.releaseDate.setText(setMovie.release_date);
        Glide.with(myViewHolder2.itemView.getContext()).load(Constant.IMAGE+"/"+setMovie.poster_path).into(myViewHolder2.poster);

    }




    @Override
    public int getItemCount() {
        return Data.size();
    }


    public class MyViewHolder2 extends RecyclerView.ViewHolder {

        private TextView movie_Title, overview, releaseDate;
        private ImageView poster;



        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            movie_Title = itemView.findViewById(R.id.tv_movie_name_comments_item);
            poster = itemView.findViewById(R.id.poster_movie_item_comments);
            overview = itemView.findViewById(R.id.tv_description_comments_item);
            releaseDate = itemView.findViewById(R.id.tv_release_date_comments_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        mMovie clickedDataItem = Data.get(pos);
                        Intent intent = new Intent(context, MovieDetailActivity.class);
                        intent.putExtra("vote_count",clickedDataItem.vote_count);
                        intent.putExtra("vote_average",clickedDataItem.vote_average);
                        intent.putExtra("title",clickedDataItem.title);
                        intent.putExtra("popularity",clickedDataItem.popularity);
                        intent.putExtra("poster_path",clickedDataItem.poster_path);
                        intent.putExtra("original_language",clickedDataItem.original_language);
                        intent.putExtra("original_title",clickedDataItem.original_title);
                        intent.putExtra("overview",clickedDataItem.overview);
                        intent.putExtra("release_date",clickedDataItem.release_date);

                        context.startActivity(intent);

                    }
                }
            });



        }
    }
}

