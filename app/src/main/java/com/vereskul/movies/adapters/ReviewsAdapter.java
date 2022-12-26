package com.vereskul.movies.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vereskul.movies.R;
import com.vereskul.movies.model.Review;
import com.vereskul.movies.model.UserRating;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsHolder>{
    private List<Review> reviews = new ArrayList<>();

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false);
        return new ReviewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsHolder holder, int position) {
        Review review = reviews.get(position);
        String avatarPath = review.getAuthorDetails().getAvatarPath();
        if (avatarPath!=null) {
            String realPath;
            if (avatarPath.contains("https")) {
                realPath = "https://" + avatarPath.substring(avatarPath.indexOf("www"));
            } else {
                realPath = "https://www.themoviedb.org/t/p/w45_and_h45_face" + avatarPath;
            }

            Glide.with(holder.itemView)
                    .load(realPath)
                    .into(holder.author_avatar_image);
        }
        holder.author_textview.setText(review.getAuthor());
        holder.review_text.setText(review.getContent());
        if(review.getAuthorDetails()!=null){
            Double dblRating = review.getAuthorDetails().getRating();
            int ratingColor;
            if(Objects.isNull(dblRating) || dblRating<=3.0){
                ratingColor = R.color.red;
            }else if(dblRating<=7.0){
                ratingColor = R.color.yellow;
            }else{
                ratingColor = R.color.holo_green_light;
            }
            Drawable background = ContextCompat.getDrawable(holder.itemView.getContext(),
                    ratingColor);
            holder.review_linear_item.setBackground(background);
        }
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    static class ReviewsHolder extends RecyclerView.ViewHolder{
        private final LinearLayout review_linear_item;
        private final TextView author_textview;
        private final TextView review_text;
        private final ImageView author_avatar_image;
        public ReviewsHolder(@NonNull View itemView) {
            super(itemView);
            review_linear_item = itemView.findViewById(R.id.review_linear_item);
            author_textview = itemView.findViewById(R.id.author_textview);
            author_avatar_image = itemView.findViewById(R.id.author_avatar_image);
            review_text = itemView.findViewById(R.id.review_text);
        }
    }
}
