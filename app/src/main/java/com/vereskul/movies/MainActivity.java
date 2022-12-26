package com.vereskul.movies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.vereskul.movies.adapters.MoviesAdapter;
import com.vereskul.movies.view.MainViewModule;

public class MainActivity extends AppCompatActivity {
    private MainViewModule viewModule;
    private RecyclerView recyclerView;
    private MoviesAdapter moviesAdapter;
    private ProgressBar pbLoading;
    private final String TAG = getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pbLoading = findViewById(R.id.pbLoading);
        moviesAdapter = new MoviesAdapter();
        recyclerView = findViewById(R.id.recViewMovies);
        recyclerView.setAdapter(moviesAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        viewModule = new ViewModelProvider(this).get(MainViewModule.class);
        viewModule.getMoviesList().observe(this, (movies -> moviesAdapter.setMovies(movies)));
        moviesAdapter.setOnReachEndOfList(()->viewModule.loadRatedMovies());
        moviesAdapter.setOnMovieClickListener((movie)-> {
            Intent intent = MovieDetailActivity.getIntent(MainActivity.this, movie);
            startActivity(intent);
        });
        viewModule.getIsLoading().observe(this, (isLoad)->{
            if (isLoad){
                pbLoading.setVisibility(View.VISIBLE);
            }else{
                pbLoading.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.favoriteMoviesMenuItem){
            Intent intent = FavoriteMoviesActivity.newIntent(this);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}