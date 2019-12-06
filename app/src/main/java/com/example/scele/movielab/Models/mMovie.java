package com.example.scele.movielab.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class mMovie {

    @SerializedName("vote_count")
    @Expose
    public int vote_count;

    @SerializedName("id")
    @Expose
    public int id;


    @SerializedName("video")
    @Expose
    public boolean video;


    @SerializedName("vote_average")
    @Expose
    public double vote_average;


    @SerializedName("title")
    @Expose
    public String title;


    @SerializedName("popularity")
    @Expose
    public double popularity;


    @SerializedName("poster_path")
    @Expose
    public String poster_path;


    @SerializedName("original_language")
    @Expose
    public String original_language;

    @SerializedName("original_title")
    @Expose
    public String original_title;

    @SerializedName("genre_ids")
    @Expose
    public int [] genre_ids;

    @SerializedName("backdrop_path")
    @Expose
    public String backdrop_path;

    @SerializedName("adult")
    @Expose
    public boolean adult;

    @SerializedName("overview")
    @Expose
    public String overview;

    @SerializedName("release_date")
    @Expose
    public String release_date;
}
