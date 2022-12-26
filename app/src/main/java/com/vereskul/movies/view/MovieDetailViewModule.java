package com.vereskul.movies.view;

import android.app.Application;
import android.util.Log;

import com.vereskul.movies.dao.MovieDAO;
import com.vereskul.movies.internet.ApiFactory;
import com.vereskul.movies.internet.ApiService;
import com.vereskul.movies.model.Movie;
import com.vereskul.movies.model.Review;
import com.vereskul.movies.model.ReviewResponse;
import com.vereskul.movies.model.Video;
import com.vereskul.movies.model.Videos;
import com.vereskul.movies.utils.MovieDatabase;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailViewModule extends AndroidViewModel {
    private final String TAG = getClass().getSimpleName();
    private final ApiService apiService = ApiFactory.getApiService();

    private final MovieDAO movieDAO;

    private final MutableLiveData<List<Video>> videos = new MutableLiveData<>();
    private final MutableLiveData<List<Review>> reviews = new MutableLiveData<>();

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();


    public MovieDetailViewModule(@NonNull Application application) {
        super(application);
        movieDAO = MovieDatabase.getInstance(application).movieDAO();
    }

    public LiveData<Movie> getFavoriteMovie(Movie movie){
        return movieDAO.getMovieById(movie.getId());
    }

    public void makeMovieFavorite(Movie movie){
        Disposable room_insert_movie = movieDAO.insertMovie(movie)
                .subscribeOn(Schedulers.io())
                .subscribe(() -> Log.d(TAG, "room insert movie"),
                        throwable -> Log.e(TAG, throwable.getMessage()));
        compositeDisposable.add(room_insert_movie);
    }

    public void makeMovieRegular(Movie movie){
        Disposable room_delete_movie = movieDAO.deleteMovie(movie)
                .subscribeOn(Schedulers.io())
                .subscribe(() -> Log.d(TAG, "room delete movie"),
                        throwable -> Log.e(TAG, throwable.getMessage()));
        compositeDisposable.add(room_delete_movie);
    }

    public LiveData<List<Video>> getVideos() {
        return videos;
    }

    public LiveData<List<Review>> getReviews() {
        return reviews;
    }

    public void loadTrailers(Movie movie){
        Disposable disposable = apiService.getTrailers(movie.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(Videos::getResults)
                .subscribe(videos::setValue,
                        throwable -> Log.d(TAG, throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    public void loadReviews(Movie movie){
        Disposable disposable = apiService.getReviews(movie.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ReviewResponse::getReviews)
                .subscribe(reviews::setValue,
                //.subscribe(reviews1 -> Log.d(TAG, reviews1.toString()),
                        throwable -> Log.d(TAG, throwable.getMessage()));
        compositeDisposable.add(disposable);


    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
