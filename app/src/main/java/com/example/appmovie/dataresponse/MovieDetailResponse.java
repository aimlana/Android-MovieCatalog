package com.example.appmovie.dataresponse;

import com.example.appmovie.model.MovieModel;
import com.google.gson.annotations.SerializedName;

public class MovieDetailResponse {
    @SerializedName("results")

    private MovieModel results;

    public MovieModel getResults() {
        return results;
    }
}
