package com.example.finalmobile.dataresponse;

import com.example.finalmobile.model.MovieModel;
import com.google.gson.annotations.SerializedName;

public class MovieDetailResponse {
    @SerializedName("results")

    private MovieModel results;

    public MovieModel getResults() {
        return results;
    }
}
