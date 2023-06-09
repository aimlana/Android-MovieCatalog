package com.example.finalmobile.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class MovieDataResponse {
    @SerializedName("result")
    private ArrayList<MovieResponse> result;
    public ArrayList<MovieResponse> getData() {
        return result;
    }
}
