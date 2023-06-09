package com.example.appmovie.dataresponse;

import com.example.appmovie.model.TvModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvResponse {
    @SerializedName("result")

    private List<TvModel> result;

    public List<TvModel> getResult() {
        return result;
    }
}
