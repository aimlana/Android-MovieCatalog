package com.example.finalmobile;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalmobile.adapter.MovieAdapter;
import com.example.finalmobile.api.ApiConfig;
import com.example.finalmobile.api.DataResponse;
import com.example.finalmobile.models.MovieModels;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MovieFragment extends Fragment {
    private RecyclerView movieRv;
    private LinearLayout refreshLayout;
    private ProgressBar progressBar;
    private Handler handler;
    private ImageView refreshBtn;
    public static ArrayList<MovieModels> movieModels =new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setView();
        getApiData();
        loadData();

        handler = new Handler();

        movieRv.setHasFixedSize(true);
        movieRv.setLayoutManager(new LinearLayoutManager(getContext()));

        MovieAdapter movieAdapter = new MovieAdapter(getContext(), movieModels);
        movieRv.setAdapter(movieAdapter);

    }

    public void setView() {
        refreshLayout = getView().findViewById(R.id.ll_fail_connection);
        movieRv = getView().findViewById(R.id.rv_movie);
        progressBar = getView().findViewById(R.id.progress_bar);
        refreshBtn = getView().findViewById(R.id.refresh_btn);
    }

    private void getApiData() {
       if (isNetworkAvailable()) {
            progressBar.setVisibility(View.VISIBLE);
            refreshLayout.setVisibility(View.GONE);
            movieRv.setVisibility(View.VISIBLE);

           Call<DataResponse> call = ApiConfig.getApiService().getPopularMovies("f9e39e8702a4db5c2e50a6b357d95734");
           call.enqueue(new Callback<DataResponse>() {
               @Override
               public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                   if (response.isSuccessful()) {
                       if (response.body() != null) {
                           List<MovieModels> userResponse = response.body().getData();
                           MovieAdapter movieAdapter = new MovieAdapter(requireContext(), userResponse);
                           movieRv.setAdapter(movieAdapter);
                       } else {
                           if (response.errorBody() != null) {
                               Log.e("API Failure", "onFailure" + response.errorBody().toString());
                           }
                       }
                   }
               }

               @Override
               public void onFailure(Call<DataResponse> call, Throwable t) {
                   Toast.makeText(getContext(), "Unable to fetch data!", Toast.LENGTH_LONG).show();
               }
           });

       } else {
           refresh();
       }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private void refresh() {
        refreshLayout.setVisibility(View.VISIBLE);
        movieRv.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        refreshBtn.setOnClickListener(view -> {
            refreshLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);

            handler.postDelayed(()-> {
                progressBar.setVisibility(View.VISIBLE);
                getApiData();
            }, 500);
        });
    }

    private void loadData() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            try { //simulate process in background thread
                for (int i = 0; i <= 10; i++) {
                    Thread.sleep(100);
                    int percentage = i * 10;
                    handler.post(() -> {
                        //update ui in main thread
                        if (percentage == 100) {
                            progressBar.setVisibility(View.GONE);
                            movieRv.setVisibility(View.VISIBLE);
                        } else {
                            progressBar.setVisibility(View.VISIBLE);
                            movieRv.setVisibility(View.GONE);
                        }
                    });
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}