package com.vereskul.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.vereskul.movies.adapters.MoviesAdapter;
import com.vereskul.movies.view.FavoriteMoviesViewModel;

public class FavoriteMoviesActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movies);
        RecyclerView favoriteRecycleView = findViewById(R.id.favoriteRecycleView);
        MoviesAdapter moviesAdapter = new MoviesAdapter();
        favoriteRecycleView.setAdapter(moviesAdapter);
        favoriteRecycleView.setLayoutManager(new GridLayoutManager(this, 2));
        moviesAdapter.setOnMovieClickListener(movie -> {
            Intent intent = MovieDetailActivity.getIntent(FavoriteMoviesActivity.this, movie);
            startActivity(intent);
        });
        moviesAdapter.setOnReachEndOfList(()-> Log.d(TAG, "end of list"));
        FavoriteMoviesViewModel viewModel = new ViewModelProvider(this)
                                                .get(FavoriteMoviesViewModel.class);
        viewModel.getFavoriteMovie().observe(this, moviesAdapter::setMovies);
    }

    public static Intent newIntent(Context context){
        return new Intent(context, FavoriteMoviesActivity.class);
    }
}