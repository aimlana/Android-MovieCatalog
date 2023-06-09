package com.example.finalmobile.api;

import com.example.finalmobile.dataresponse.MovieResponse;
import com.example.finalmobile.model.MovieModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}")
    Call<MovieModel> getMovieDetails(@Path("movie_id") int movieId, @Query("api_key") String apiKey);
}
