package com.example.finalmobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalmobile.activity.MovieDetailActivity;
import com.example.finalmobile.R;
import com.example.finalmobile.api.MovieResponse;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    Context context;
    private ArrayList<MovieResponse> movieResponses;

    public MovieAdapter(Context context, ArrayList<MovieResponse> movieResponses) {
        this.context = context;
        this.movieResponses = movieResponses;
    }
    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {
        MovieResponse movie = movieResponses.get(position);
        holder.movieTitle.setText(movie.getTitle());
        holder.movieRelease.setText(movie.getReleaseDate());
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                .into(holder.moviePoster);

        holder.itemView.setOnClickListener(view -> {
            String movieId = movie.getId();
            Intent intent = new Intent(holder.itemView.getContext(), MovieDetailActivity.class);
            intent.putExtra("movie_id", movieId);
            holder.itemView.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return movieResponses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView movieTitle, movieRelease;
        ImageView moviePoster;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.movie_title);
            movieRelease = itemView.findViewById(R.id.movie_release);
            moviePoster = itemView.findViewById(R.id.movie_poster);
        }
    }
}
