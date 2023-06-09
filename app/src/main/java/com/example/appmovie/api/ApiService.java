package com.example.appmovie.api;

import com.example.appmovie.dataresponse.MovieDetailResponse;
import com.example.appmovie.dataresponse.MovieResponse;
import com.example.appmovie.dataresponse.TvDetailResponse;
import com.example.appmovie.dataresponse.TvResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MovieDetailResponse> getMovieDetails(@Path("id") int movieId, @Query("api_key") String apiKey);

    @GET("tv/popular")
    Call<TvResponse> getPopularTVShows(@Query("api_key") String apiKey);

    @GET("tv/{id}")
    Call<TvDetailResponse> getTVShowDetails(@Path("id") int tvShowId, @Query("api_key") String apiKey);
}
