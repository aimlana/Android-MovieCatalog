package com.example.finalmobile.dataresponse;

import com.example.finalmobile.model.MovieModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class MovieResponse {
    @SerializedName("result")
    private ArrayList<MovieModel> result;

    public ArrayList<MovieModel> getResult() {
        return result;
    }
}
