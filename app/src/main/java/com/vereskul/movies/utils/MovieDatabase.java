package com.vereskul.movies.utils;

import android.app.Application;

import com.vereskul.movies.dao.MovieDAO;
import com.vereskul.movies.model.Movie;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class MovieDatabase extends RoomDatabase {
    private static MovieDatabase database = null;

    public static MovieDatabase getInstance(Application application){
        if (database==null){
            database = Room.databaseBuilder(
                    application,
                    MovieDatabase.class,
                    "movie.db"
            ).build();
        }
        return database;
    }
    public abstract MovieDAO movieDAO();
}
