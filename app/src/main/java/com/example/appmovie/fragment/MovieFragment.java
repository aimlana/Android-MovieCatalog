package com.example.appmovie.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.appmovie.adapter.MovieAdapter;
import com.example.appmovie.dataresponse.MovieDataResponse;
import com.example.appmovie.model.MovieModel;
import com.example.appmovie.R;
import com.example.appmovie.api.ApiConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayout retryicon;

    private ImageView im2;
    private ProgressBar progressBar;

    private Handler handler;

    private ImageView Retryy;
    public static ArrayList<MovieModel> dataPerson = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        retryicon = view.findViewById(R.id.retryicon);
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.pb1);
        Retryy = view.findViewById(R.id.retry);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        handler = new Handler();

        loading();

        getDataApi();
        return view;
    }

    private void getDataApi() {
        if (isNetworkAvailable()) {
            progressBar.setVisibility(View.VISIBLE);
            retryicon.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            //call -> untuk mengambil data di reqres.in disini dia mengambil data perpage
            Call<MovieDataResponse> call = ApiConfig.getApiService().getPopularMovies("35254a98cc59f9518caf1bacbf0f5792");
            call.enqueue(new Callback<MovieDataResponse>() {
                @Override
                public void onResponse(Call<MovieDataResponse> call, Response<MovieDataResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            List<MovieModel> userResponse = response.body().getData(); // Assuming movie list is stored in the "data" field
                            MovieAdapter adapter = new MovieAdapter(getContext(), userResponse);
                            recyclerView.setAdapter(adapter);
                        } else {
                            if (response.errorBody() != null) {
                                Log.e("MainActivity", "onFailure: " + response.errorBody().toString());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<MovieDataResponse> call, Throwable t) {
                    Toast.makeText(getContext(), "Unable to fetch data!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Retry();

        }
    }

    private void loading() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            try { //simulate process in background thread
                for (int i = 0; i <= 10; i++) {
                    Thread.sleep(150);
                    int percentage = i * 10;
                    handler.post(() -> {
                        //update ui in main thread
                        if (percentage == 100) {
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        } else {
                            progressBar.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    });
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private void Retry() {
        retryicon.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        Retryy.setOnClickListener(view -> {
            retryicon.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            handler.postDelayed(() -> {
                progressBar.setVisibility(View.VISIBLE);
//                recyclerView.setVisibility(View.VISIBLE);
                getDataApi();
            }, 500);

        });
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}
