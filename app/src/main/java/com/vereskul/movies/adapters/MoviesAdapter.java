package com.vereskul.movies.adapters;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.vereskul.movies.R;
import com.vereskul.movies.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>{
    private List<Movie> movies = new ArrayList<>();
    private OnReachEndOfList onReachEndOfList;
    private OnMovieClickListener onMovieClickListener;

    public void setOnMovieClickListener(OnMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }

    public void setOnReachEndOfList(OnReachEndOfList onReachEndOfList) {
        this.onReachEndOfList = onReachEndOfList;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item,parent,false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);

        Glide.with(holder.itemView)
                .load(movie.getPoster_path())
                .into(holder.posterImage);
        double rating = movie.getVote_average();
        holder.textViewRating.setText(String.valueOf(rating));
        int backgroundId;
        if (rating >7) {
            backgroundId = R.drawable.circle_green;
        }else if (rating>5){
            backgroundId = R.drawable.circle_yellow;
        }else{
            backgroundId = R.drawable.circle_red;
        }
        Drawable background = ContextCompat
                    .getDrawable(holder.itemView.getContext(),backgroundId);
        holder.textViewRating.setBackground(background);
        if (position>=movies.size()-10){
            onReachEndOfList.onReachEnd();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onMovieClickListener!=null){
                    onMovieClickListener.onClick(movie);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder{
        private final ImageView posterImage;
        private final TextView textViewRating;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImage = itemView.findViewById(R.id.posterImage);
            textViewRating = itemView.findViewById(R.id.textViewRating);

        }
    }

    public interface OnReachEndOfList{
        void onReachEnd();
    }

    public interface OnMovieClickListener{
        void onClick(Movie movie);
    }



}
