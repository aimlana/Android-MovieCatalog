package com.example.appmovie.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appmovie.model.MovieModel;
import com.example.appmovie.R;
import com.example.appmovie.api.ApiConfig;
import com.example.appmovie.dataresponse.MovieDetailDataResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView img2, img3, back, love;
    private TextView tv4, tv5, tv6;
    boolean isFavorite = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        tv6 = findViewById(R.id.tv6);
        love = findViewById(R.id.favbutton);
        back  = findViewById(R.id.backbutton);

        back.setOnClickListener(view -> {
            Intent intent = new Intent(MovieDetailActivity.this, MainActivity.class);
            startActivity(intent);
        });

        love.setOnClickListener(view -> {
            if (!isFavorite) {
                love.setImageResource(R.drawable.red_favorite);
                isFavorite = true;
            } else {
                love.setImageResource(R.drawable.baseline_favorite_border_24);
                isFavorite = false;
            }
        });

        getDataApi();
    }

    private void getDataApi() {
        if (isNetworkAvailable()) {
            Intent intent = getIntent();
            String movieId = intent.getStringExtra("movie_id");
            Toast.makeText(this, movieId, Toast.LENGTH_SHORT).show();
            Call<MovieDetailDataResponse> call = ApiConfig.getApiService().getMovieDetails(Integer.valueOf(movieId), "35254a98cc59f9518caf1bacbf0f5792");
            call.enqueue(new Callback<MovieDetailDataResponse>() {
                @Override
                public void onResponse(Call<MovieDetailDataResponse> call, Response<MovieDetailDataResponse> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(MovieDetailActivity.this, "test", Toast.LENGTH_SHORT).show();
                        if (response.body() != null) {
                            MovieModel movieModel = response.body().getData2();
                            String judul = getIntent().getStringExtra("judul");
                            String rating = getIntent().getStringExtra("rating");
                            String synopsis = getIntent().getStringExtra("synopsis");
                            String backdropPath = getIntent().getStringExtra("backdrop");
                            String poster = getIntent().getStringExtra("poster");
                            tv4.setText(judul);
                            tv5.setText(rating);
                            tv6.setText(synopsis);
                            Glide.with(MovieDetailActivity.this)
                                    .load("https://image.tmdb.org/t/p/w500" + backdropPath)
                                    .into(img2);
                            Glide.with(MovieDetailActivity.this)
                                    .load("https://image.tmdb.org/t/p/w500" + poster)
                                    .into(img3);
                        }
                    } else {
                        Toast.makeText(MovieDetailActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<MovieDetailDataResponse> call, Throwable t) {
                    Toast.makeText(MovieDetailActivity.this, "Unable to fetch data!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static String formatDate(String inputDate) {
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        DateFormat outputFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        String formattedDate = "";

        try {
            Date date = inputFormat.parse(inputDate);
            formattedDate = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedDate;
    }
}
