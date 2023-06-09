package com.example.finalmobile.fragment;

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

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalmobile.R;
import com.example.finalmobile.adapter.MovieAdapter;
import com.example.finalmobile.api.ApiConfig;
import com.example.finalmobile.dataresponse.MovieResponse;
import com.example.finalmobile.model.MovieModel;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MovieFragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayout refreshLayout;
    private ProgressBar progressBar;
    String API_KEY = "f9e39e8702a4db5c2e50a6b357d95734";
//    MovieAdapter movieAdapter;
    private Handler handler;
    private ImageView refreshBtn;
    public static ArrayList<MovieModel> movieModels = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        refreshLayout = view.findViewById(R.id.ll_fail_connection);
        recyclerView = view.findViewById(R.id.rv_movie);
        progressBar = view.findViewById(R.id.progress_bar);
        refreshBtn = view.findViewById(R.id.refresh_btn);
        handler = new Handler();

        loadData();
        getApiData();


        return view;
    }


    private void getApiData() {
       if (isNetworkAvailable()) {
            progressBar.setVisibility(View.VISIBLE);
            refreshLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

           Call<MovieResponse> call = ApiConfig.getApiService().getPopularMovies(API_KEY);
           call.enqueue(new Callback<MovieResponse>() {
               @Override
               public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                   if (response.isSuccessful()) {
                       if (response.body() != null)      {
                           ArrayList<MovieModel> userResponse = response.body().getData();
                           MovieAdapter movieAdapter = new MovieAdapter(getContext(), userResponse);
                           recyclerView.setHasFixedSize(true);
                           recyclerView.setAdapter(movieAdapter);
                           GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
                           recyclerView.setLayoutManager(layoutManager);
                           recyclerView.setAdapter(movieAdapter);
                       } else {
                           if (response.errorBody() != null) {
                               Log.e("API failure", "onFailure" + response.errorBody().toString());
                           }
                       }
                   }
               }

               @Override
               public void onFailure(Call<MovieResponse> call, Throwable t) {
                   Toast.makeText(getContext(), "Unable to fetch data!", Toast.LENGTH_SHORT).show();
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
        recyclerView.setVisibility(View.GONE);
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
}