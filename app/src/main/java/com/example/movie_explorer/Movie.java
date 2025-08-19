package com.example.movie_explorer;


import com.google.gson.annotations.SerializedName;

import com.google.gson.annotations.SerializedName;

public class Movie {
    @SerializedName("Title")
    private String title;

    @SerializedName("Year")
    private String year;

    @SerializedName("Poster")
    private String poster;

    public String getTitle() { return title; }
    public String getYear() { return year; }
    public String getPoster() { return poster; }
}