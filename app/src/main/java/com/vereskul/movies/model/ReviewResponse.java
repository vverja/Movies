package com.vereskul.movies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResponse {
    private int id;
    private int page;
    @SerializedName("results")
    private List<Review> reviews;

    public ReviewResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
