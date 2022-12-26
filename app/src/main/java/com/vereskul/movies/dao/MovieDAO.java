package com.vereskul.movies.dao;

import com.vereskul.movies.model.Movie;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.rxjava3.core.Completable;

@Dao
public interface MovieDAO {
    @Query("SELECT * from favorite_movies")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT * from favorite_movies WHERE id = :movieId")
    LiveData<Movie>getMovieById(int movieId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMovie(Movie movie);

    @Delete
    Completable deleteMovie(Movie movie);

}
