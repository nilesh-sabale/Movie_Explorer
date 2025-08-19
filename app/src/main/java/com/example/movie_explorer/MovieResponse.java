package com.example.movie_explorer;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import java.util.List;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MovieResponse {
    @SerializedName("Search")
    private List<Movie> movies;

    @SerializedName("Response")
    private String response;

    public List<Movie> getMovies() { return movies; }
    public String getResponse() { return response; }
}