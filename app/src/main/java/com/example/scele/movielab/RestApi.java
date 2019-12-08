package com.example.scele.movielab;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApi {

    @GET("movie")
    Call<ResponseMovie> getMovie(
        @Query("api_key") String api_key,
        @Query("language") String language,
        @Query("query") String query
    );

}
