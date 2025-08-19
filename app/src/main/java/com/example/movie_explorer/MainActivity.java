package com.example.movie_explorer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private List<Movie> movieList = new ArrayList<>();
    private EditText editSearch;
    private ProgressBar progressBar;

    private static final String API_KEY = "8902bcad"; // your OMDb API key
    private boolean sortNewestFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        editSearch = findViewById(R.id.editSearch);
        progressBar = findViewById(R.id.progressBar);

        adapter = new MovieAdapter(this, movieList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Load default movies on app start
        loadDefaultMovies();

        // Search button
        findViewById(R.id.btnSearch).setOnClickListener(v -> {
            String query = editSearch.getText().toString().trim();
            if (!query.isEmpty()) {
                searchMovies(query);
            } else {
                Toast.makeText(MainActivity.this, "Enter a movie name", Toast.LENGTH_SHORT).show();
            }
        });

        // Favorites button
        findViewById(R.id.btnFavorites).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, FavoritesActivity.class));
        });

        // Sort button
        findViewById(R.id.btnSort).setOnClickListener(v -> {
            if (movieList == null || movieList.isEmpty()) return;

            Button btn = (Button) v;
            if (sortNewestFirst) {
                movieList.sort((m1, m2) -> parseYear(m2.getYear()) - parseYear(m1.getYear())); // New → Old
                btn.setText("Sort: Old → New");
            } else {
                movieList.sort((m1, m2) -> parseYear(m1.getYear()) - parseYear(m2.getYear())); // Old → New
                btn.setText("Sort: New → Old");
            }
            sortNewestFirst = !sortNewestFirst;
            adapter.updateList(movieList);
        });
    }

    private int parseYear(String yearStr) {
        try {
            return Integer.parseInt(yearStr.replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            return 0; // If invalid year, treat as 0
        }
    }

    private void searchMovies(String query) {
        progressBar.setVisibility(ProgressBar.VISIBLE);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<MovieResponse> call = apiService.searchMovies(query, API_KEY);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                progressBar.setVisibility(ProgressBar.GONE);

                if (response.isSuccessful() && response.body() != null && "True".equals(response.body().getResponse())) {
                    movieList = response.body().getMovies();
                    adapter.updateList(movieList);
                } else {
                    Toast.makeText(MainActivity.this, "No results found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                progressBar.setVisibility(ProgressBar.GONE);
                Log.e("MainActivity", "Error: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Error loading movies", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Load default/popular movies when app opens
    private void loadDefaultMovies() {
        progressBar.setVisibility(ProgressBar.VISIBLE);

        // You can pick one randomly from popular searches
        String[] defaultQueries = {"harry potter", "Avengers", "Stranger Things", "Batman"};
        String query = defaultQueries[new Random().nextInt(defaultQueries.length)];

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<MovieResponse> call = apiService.searchMovies(query, API_KEY);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                progressBar.setVisibility(ProgressBar.GONE);

                if (response.isSuccessful() && response.body() != null && "True".equals(response.body().getResponse())) {
                    movieList = response.body().getMovies();
                    adapter.updateList(movieList);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                progressBar.setVisibility(ProgressBar.GONE);
                Log.e("MainActivity", "Error loading default movies: " + t.getMessage());
            }
        });
    }
}
