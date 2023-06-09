package com.example.finalmobile.dataresponse;

import com.example.finalmobile.model.TvModel;
import com.google.gson.annotations.SerializedName;

public class TvDetailResponse {
    @SerializedName("results")

    private TvModel results;

    public TvModel getData4() {
        return results;
    }
}
