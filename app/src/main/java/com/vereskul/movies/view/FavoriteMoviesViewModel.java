package com.vereskul.movies.view;

import android.app.Application;

import com.vereskul.movies.dao.MovieDAO;
import com.vereskul.movies.model.Movie;
import com.vereskul.movies.utils.MovieDatabase;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class FavoriteMoviesViewModel extends AndroidViewModel {
    private final MovieDAO movieDAO;
    public FavoriteMoviesViewModel(@NonNull Application application) {
        super(application);
        movieDAO = MovieDatabase.getInstance(application).movieDAO();
    }

    public LiveData<List<Movie>> getFavoriteMovie(){
        return movieDAO.getAllMovies();
    }
}
