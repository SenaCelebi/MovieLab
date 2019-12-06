package com.example.scele.movielab;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApi {

    @GET("movie")
    Call<ResponseMovie> getMovie(
        @Query("05991608ea1ac9bb221aa48fd7c1dd8c") String api_key,
        @Query("language") String language,
        @Query("query") String query
    );

}
