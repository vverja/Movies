package com.vereskul.movies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Root{
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<Movie> movies;
    @SerializedName("total_pages")
    private int total_pages;
    @SerializedName("total_results")
    private int total_results;

    public Root(int page, List<Movie> results, int total_pages, int total_results) {
        this.page = page;
        this.movies = results;
        this.total_pages = total_pages;
        this.total_results = total_results;
    }
    public Root() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    @Override
    public String toString() {
        return "Root{" +
                "page=" + page +
                ", movies=" + movies +
                ", total_pages=" + total_pages +
                ", total_results=" + total_results +
                '}';
    }
}