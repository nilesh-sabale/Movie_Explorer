package com.example.movie_explorer;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PrefsManager {
    private static final String PREFS_NAME = "movie_prefs";
    private static final String FAVORITES_KEY = "favorites";

    private SharedPreferences prefs;
    private Gson gson;

    public PrefsManager(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveFavorites(List<Movie> favorites) {
        String json = gson.toJson(favorites);
        prefs.edit().putString(FAVORITES_KEY, json).apply();
    }

    public List<Movie> getFavorites() {
        String json = prefs.getString(FAVORITES_KEY, null);
        if (json == null) return new ArrayList<>();
        Type type = new TypeToken<List<Movie>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void addFavorite(Movie movie) {
        List<Movie> favorites = getFavorites();
        if (!favorites.contains(movie)) {
            favorites.add(movie);
            saveFavorites(favorites);
        }
    }

    public void removeFavorite(Movie movie) {
        List<Movie> favorites = getFavorites();
        favorites.remove(movie);
        saveFavorites(favorites);
    }

    public boolean isFavorite(Movie movie) {
        List<Movie> favorites = getFavorites();
        return favorites.contains(movie);
    }
}
