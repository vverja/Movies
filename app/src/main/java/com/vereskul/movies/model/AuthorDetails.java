package com.vereskul.movies.model;

import com.google.gson.annotations.SerializedName;

public class AuthorDetails {
    private String name;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("avatar_path")
    private String avatarPath;
    private Double rating;

    public AuthorDetails() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}


