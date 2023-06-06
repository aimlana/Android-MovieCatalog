package com.example.finalmobile.api;

import com.example.finalmobile.models.MovieModels;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataResponse {
    @SerializedName("data")
    private List<MovieModels> data;
    public List<MovieModels> getData() {
        return data;
    }
}
