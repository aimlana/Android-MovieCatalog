package com.example.finalmobile.model;

import com.google.gson.annotations.SerializedName;

public class TvModel {
    @SerializedName("id")
    private String id;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("name")
    private String name;

    @SerializedName("first_air_date")
    private String firstAirDate;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("vote_average")
    private String rating;

    @SerializedName("overview")
    private String overview;

    public TvModel(String id, String posterPath, String name, String firstAirDate, String backdropPath, String rating, String overview) {
        this.id = id;
        this.posterPath = posterPath;
        this.name = name;
        this.firstAirDate = firstAirDate;
        this.backdropPath = backdropPath;
        this.rating = rating;
        this.overview = overview;
    }

    public String getId() {
        return id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getName() {
        return name;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getRating() {
        return rating;
    }

    public String getOverview() {
        return overview;
    }
}
