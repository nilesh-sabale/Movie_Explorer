package com.example.movie_explorer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private Context context;
    private List<Movie> movieList;
    private PrefsManager prefs;

    public MovieAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
        prefs = new PrefsManager(context);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.title.setText(movie.getTitle());
        holder.year.setText(movie.getYear());

        Glide.with(context)
                .load(movie.getPoster())
                .into(holder.poster);

        // Set favorite icon
        holder.btnFavorite.setImageResource(
                prefs.isFavorite(movie) ? R.drawable.ic_favorite : R.drawable.ic_favorite_border
        );

        // Favorite button click
        holder.btnFavorite.setOnClickListener(v -> {
            if (prefs.isFavorite(movie)) {
                prefs.removeFavorite(movie);
                holder.btnFavorite.setImageResource(R.drawable.ic_favorite_border);
            } else {
                prefs.addFavorite(movie);
                holder.btnFavorite.setImageResource(R.drawable.ic_favorite);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void updateList(List<Movie> newMovies) {
        movieList = newMovies;
        notifyDataSetChanged();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView title, year;
        ImageView poster, btnFavorite;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textTitle);
            year = itemView.findViewById(R.id.textYear);
            poster = itemView.findViewById(R.id.imagePoster);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
        }
    }
}
