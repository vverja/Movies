package com.vereskul.movies.internet;

import com.vereskul.movies.model.Movie;
import com.vereskul.movies.model.ReviewResponse;
import com.vereskul.movies.model.Root;
import com.vereskul.movies.model.Videos;
import com.vereskul.movies.utils.InternetSettings;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("discover/movie?api_key=" + InternetSettings.apiKey + "&language=ru-ru")
    Single<Root> loadMovies(@Query("page") int page);
    @GET("movie/{movie_id}/videos?api_key=" + InternetSettings.apiKey + "&language=ru-ru")
    Single<Videos> getTrailers(@Path("movie_id") int movieId);
    @GET("movie/{movie_id}/reviews?api_key=" + InternetSettings.apiKey)
    Single<ReviewResponse>getReviews(@Path("movie_id") int movieId);
}
