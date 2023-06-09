package com.example.appmovie.dataresponse;

import com.example.appmovie.model.TvModel;
import com.google.gson.annotations.SerializedName;

public class TvDetailResponse {
    @SerializedName("results")

    private TvModel results;

    public TvModel getResults() {
        return results;
    }
}
