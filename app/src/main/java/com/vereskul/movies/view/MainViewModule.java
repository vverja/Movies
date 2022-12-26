package com.vereskul.movies.view;

import android.app.Application;
import android.util.Log;

import com.vereskul.movies.internet.ApiFactory;
import com.vereskul.movies.internet.ApiService;
import com.vereskul.movies.model.Movie;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModule extends AndroidViewModel {
    private final String TAG = getClass().getSimpleName();
    private final ApiService apiService = ApiFactory.getApiService();
    private int page = 1;
    private final MutableLiveData<List<Movie>> moviesList = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MainViewModule(@NonNull Application application) {
        super(application);
        loadRatedMovies();
    }

    public LiveData<List<Movie>> getMoviesList() {
        return moviesList;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void loadRatedMovies(){
        if (Boolean.TRUE.equals(isLoading.getValue())){
            return;
        }

        Disposable subscribe = apiService.loadMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((disposable -> isLoading.setValue(true)))
                .doAfterTerminate(()-> isLoading.setValue(false))
                .subscribe((root -> {
                    List<Movie> loadedMovies = moviesList.getValue();
                    if (loadedMovies!=null){
                        loadedMovies.addAll(root.getMovies());
                        moviesList.setValue(loadedMovies);
                    }else{
                        moviesList.setValue(root.getMovies());
                    }
                    page++;
                }),
                           (throwable -> Log.d(TAG, throwable.getMessage())));
        compositeDisposable.add(subscribe);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
