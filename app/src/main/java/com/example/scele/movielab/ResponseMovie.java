package com.example.scele.movielab;

import com.example.scele.movielab.Models.mMovie;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseMovie {

    @SerializedName("page")
    @Expose
    public int page;

    @SerializedName("total_results")
    @Expose
    public int total_results;

    @SerializedName("total_pages")
    @Expose
    public int total_pages;

    @SerializedName("results")
    @Expose
    public List<mMovie> results;
}
