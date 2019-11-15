package com.example.scele.movielab;

import android.widget.ImageView;

import com.example.scele.movielab.Models.Movie;

public interface MovieItemClickListener {
    void onMovieClick(Movie movie, ImageView moviePoster);
}
