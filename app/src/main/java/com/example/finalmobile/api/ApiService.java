package com.example.finalmobile.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("movie/popular")
    Call<MovieDataResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}")
    Call<MovieResponse> getMovieDetails(@Path("movie_id") int movieId, @Query("api_key") String apiKey);
}
