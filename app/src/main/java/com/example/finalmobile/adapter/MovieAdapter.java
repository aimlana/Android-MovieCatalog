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
import com.example.finalmobile.MovieDetailActivity;
import com.example.finalmobile.R;
import com.example.finalmobile.models.MovieModels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    Context context;
    private List<MovieModels> movieData;

    public MovieAdapter(Context context, List<MovieModels> movieData) {
        this.context = context;
        this.movieData = movieData;
    }
    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {
        MovieModels movieModels = movieData.get(position);
        holder.movieTitle.setText(movieModels.getTitle());
        holder.yearsRelease.setText(movieModels.getReleaseDate());
        Glide.with(holder.itemView.getContext())
                .load(movieModels.getPosterPath())
                .into(holder.moviePoster);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), MovieDetailActivity.class);
            intent.putExtra("id", movieModels.getId());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView movieTitle, yearsRelease;
        ImageView moviePoster;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.movie_title);
            yearsRelease = itemView.findViewById(R.id.years_release);
            moviePoster = itemView.findViewById(R.id.movie_poster);
        }
    }
}
