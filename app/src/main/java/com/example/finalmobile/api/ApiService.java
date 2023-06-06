package com.example.finalmobile.api;

import com.example.finalmobile.models.MovieModels;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/movie/popular")
    Call<DataResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("/movie/{movie_id}")
    Call<MovieModels> getMovieDetails(@Path("movie_id") int movieId, @Query("api_key") String apiKey);
}
