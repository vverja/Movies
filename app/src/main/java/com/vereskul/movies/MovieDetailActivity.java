package com.vereskul.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.rxjava3.schedulers.Schedulers;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vereskul.movies.adapters.ReviewsAdapter;
import com.vereskul.movies.adapters.TrailersAdapter;
import com.vereskul.movies.dao.MovieDAO;
import com.vereskul.movies.model.Movie;
import com.vereskul.movies.model.Video;
import com.vereskul.movies.model.Videos;
import com.vereskul.movies.utils.MovieDatabase;
import com.vereskul.movies.view.MovieDetailViewModule;

import java.time.LocalDate;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private static final String EXTRA = "movie";
    private ImageView posterImageDetails;
    private TextView titleTextView;
    private TextView yearTextView;
    private TextView descriptionTextView;
    private Movie movie;
    private MovieDetailViewModule viewModule;
    private RecyclerView trailersList;
    private RecyclerView reviewsList;
    private TrailersAdapter trailersAdapter;
    private ReviewsAdapter reviewsAdapter;
    private ImageView favoriteImage;
    private Drawable starOn;
    private Drawable starOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initViews();
        loadMovie();
    }

    private void initViews(){
        posterImageDetails = findViewById(R.id.posterImageDetails);
        titleTextView = findViewById(R.id.titleTextView);
        yearTextView = findViewById(R.id.yearTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        favoriteImage = findViewById(R.id.favoriteImage);

        favoriteImage.setOnClickListener((view)->{

        });
        starOn = ContextCompat
                .getDrawable(MovieDetailActivity.this, android.R.drawable.btn_star_big_on);
        starOff = ContextCompat
                .getDrawable(MovieDetailActivity.this, android.R.drawable.btn_star_big_off);

        trailersAdapter = new TrailersAdapter();
        trailersAdapter.setOnPlayButtonClick(video -> {
            //Log.d(TAG,"YouTube".equals(video.getSite())?"https://www.youtube.com/watch?v=" + video.getKey():"not youtube");
            if ("YouTube".equals(video.getSite())){
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.youtube.com/watch?v=" + video.getKey()));
                startActivity(intent);
            }
        });

        reviewsAdapter = new ReviewsAdapter();

        viewModule = new ViewModelProvider(this).get(MovieDetailViewModule.class);
        viewModule.getVideos().observe(this, (videos -> trailersAdapter.setTrailersList(videos)));
        viewModule.getReviews().observe(this, reviews -> reviewsAdapter.setReviews(reviews));

        trailersList = findViewById(R.id.trailer_list);
        trailersList.setAdapter(trailersAdapter);

        reviewsList = findViewById(R.id.reviews_list);
        reviewsList.setAdapter(reviewsAdapter);
//        trailersList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    public static Intent getIntent(Context context, Movie movie){
        Intent intent = new Intent(context, MovieDetailActivity.class);

        intent.putExtra(EXTRA, movie);
        return intent;
    }

    private void loadMovie(){
        movie = (Movie) getIntent().getSerializableExtra(EXTRA);
        Glide.with(this)
                .load(movie.getPoster_path())
                .into(posterImageDetails);
        titleTextView.setText(movie.getTitle());
        yearTextView.setText(movie.getRelease_date().substring(0,4));
        descriptionTextView.setText(movie.getOverview());
        viewModule.loadTrailers(movie);
        viewModule.loadReviews(movie);

        viewModule.getFavoriteMovie(movie).observe(this, movieFromDb -> {
            if (movieFromDb!=null){
                favoriteImage.setImageDrawable(starOn);
                favoriteImage.setOnClickListener(v -> viewModule.makeMovieRegular(movie));
            }else{
                favoriteImage.setImageDrawable(starOff);
                favoriteImage.setOnClickListener(v -> viewModule.makeMovieFavorite(movie));
            }
        });
    }
}