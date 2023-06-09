package com.example.finalmobile.api;

import com.google.gson.annotations.SerializedName;

public class MovieResponse {
    @SerializedName("id")
    private String id;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("title")
    private String title;
    @SerializedName("release_date")
    private String releaseDate;

    public MovieResponse(String id, String posterPath, String title, String releaseDate) {
        this.id = id;
        this.posterPath = posterPath;
        this.title = title;
        this.releaseDate = releaseDate;
    }

    public String getId() {
        return id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
