package com.tonytekinsights.gomovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tonytekinsights.gomovies.App;
import com.tonytekinsights.gomovies.BuildConfig;
import com.tonytekinsights.gomovies.R;
import com.tonytekinsights.gomovies.models.Movie;
import com.tonytekinsights.gomovies.models.MovieResults;
import com.tonytekinsights.gomovies.services.NetworkService;
import com.tonytekinsights.gomovies.services.NetworkServiceImpl;
import com.tonytekinsights.gomovies.ui.MovieInfoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {
    private MovieResults movieResults = new MovieResults();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return this.movieResults.getSize();
    }

    public void setData(MovieResults movieResults) {
        this.movieResults = movieResults;
    }

    public void addMovies(MovieResults movieResults) {
        this.movieResults.movies.addAll(movieResults.movies);
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        @BindView(R.id.mv_image) ImageView mvImage;
        @BindView(R.id.mv_title) TextView mvTitle;
        private int movieId;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();

            if(!NetworkServiceImpl.isOnline(context)) {
                NetworkServiceImpl.noInternet(v);
            } else {
                Intent intent = new Intent(context, MovieInfoActivity.class);
                intent.putExtra(context.getString(R.string.movie_id), this.movieId);

                context.startActivity(intent);
            }
        }

        void bind(int position) {
            Movie movie = movieResults.movies.get(position);
            this.movieId = movie.id;

            Picasso.get()
                    .load(BuildConfig.TMDB_IMAGE_BASE_URL +
                            App.getInstance().getString(R.string.tmdb_file_size_500) +
                            movie.poster_path)
                    .into(mvImage);

            mvImage.setOnClickListener(this);
            mvTitle.setText(movie.title);
        }
    }
}
