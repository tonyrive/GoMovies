package com.tonytekinsights.gomovies.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.tonytekinsights.gomovies.models.Movie;
import com.tonytekinsights.gomovies.models.MovieResults;
import com.tonytekinsights.gomovies.services.RepositoryService;


public class MoviesViewModel extends ViewModel {
    private RepositoryService repositoryService;

    public MoviesViewModel() {
        this.repositoryService = new RepositoryService();
    }

    public LiveData<MovieResults> getPopularMovies(int pageId) {
        return this.repositoryService.getPopularMovies(pageId);
    }

    public LiveData<MovieResults> getTopRatedMovies(int pageId) {
        return this.repositoryService.getTopRatedMovies(pageId);
    }

    public LiveData<Movie> getMovie(int movieId) {
        return this.repositoryService.getMovie(movieId);
    }
}
