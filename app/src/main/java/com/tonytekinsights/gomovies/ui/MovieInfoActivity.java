package com.tonytekinsights.gomovies.ui;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tonytekinsights.gomovies.BuildConfig;
import com.tonytekinsights.gomovies.R;
import com.tonytekinsights.gomovies.models.Movie;
import com.tonytekinsights.gomovies.services.NetworkServiceImpl;
import com.tonytekinsights.gomovies.viewmodel.MoviesViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieInfoActivity extends AppCompatActivity {

    @BindView(R.id.mv_backdrop) ImageView mvBackdrop;
    @BindView(R.id.mv_poster) ImageView mvPoster;
    @BindView(R.id.mv_title) TextView mvTitle;
    @BindView(R.id.mv_year) TextView mvYear;
    @BindView(R.id.mv_mins) TextView mvMins;
    @BindView(R.id.mv_rating) TextView mvRating;
    @BindView(R.id.mv_fav) Button mvFav;
    @BindView(R.id.mv_overview) TextView mvOverview;
    @BindView(R.id.pg_bar) ProgressBar pgBar;
    @BindView(R.id.detailLayout) RelativeLayout detailLayout;
    private MoviesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        ButterKnife.bind(this);

        if(!NetworkServiceImpl.isOnline(getBaseContext())) {
            NetworkServiceImpl.noInternet(detailLayout);
            return;
        }

        pgBar.setVisibility(View.VISIBLE);
        viewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);

        Intent intent = getIntent();
        String movieId = getString(R.string.movie_id);
        if (intent.hasExtra(movieId)) {
            int Id = intent.getIntExtra(movieId, 0);
            viewModel.getMovie(Id).observe(this, this::showMovie);
        }
    }

    private void showMovie(Movie movie) {
        if(movie != null) {
            Picasso.get()
                    .load(BuildConfig.TMDB_IMAGE_BASE_URL +
                            getString(R.string.tmdb_file_size_500) +
                            movie.poster_path)
                    .placeholder(R.drawable.test_poster)
                    .into(mvPoster);

            Picasso.get()
                    .load(BuildConfig.TMDB_IMAGE_BASE_URL +
                            getString(R.string.tmdb_file_size_500) +
                            movie.backdrop_path)
                    .placeholder(R.drawable.test_backdrop)
                    .into(mvBackdrop);

            mvTitle.setText(movie.title);
            mvOverview.setText(movie.overview);

            String[] dt = movie.release_date.split("-");
            mvYear.setText(dt[0]);

            mvMins.setText(String.format("%smins", movie.runtime));
            mvRating.setText(String.format("%s/10", movie.vote_average));
        }
        pgBar.setVisibility(View.INVISIBLE);
    }
}
