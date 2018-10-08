package com.tonytekinsights.gomovies.services;

import android.arch.lifecycle.LiveData;

import com.tonytekinsights.gomovies.helper.NetworkServiceHelper;
import com.tonytekinsights.gomovies.models.Movie;
import com.tonytekinsights.gomovies.models.MovieResults;
import com.tonytekinsights.gomovies.models.Videos;

public class RepositoryService {
    private NetworkService networkService;

    public RepositoryService() {
        this.networkService = NetworkServiceImpl
                .GetNetworkService()
                .create(NetworkService.class);
    }

    public LiveData<MovieResults> getPopularMovies(int pageId) {
        NetworkServiceHelper<MovieResults> callback = new NetworkServiceHelper<>();
        this.networkService.getPopularMovies(pageId).enqueue(callback.getData());
        return callback.data;
    }

    public LiveData<MovieResults> getTopRatedMovies(int pageId) {
        NetworkServiceHelper<MovieResults> callback = new NetworkServiceHelper<>();
        this.networkService.getTopRatedMovies(pageId).enqueue(callback.getData());
        return callback.data;
    }

    public LiveData<Movie> getMovie(int movieId) {
        NetworkServiceHelper<Movie> callback = new NetworkServiceHelper<>();
        this.networkService.getMovie(movieId).enqueue(callback.getData());
        return callback.data;
    }

    public LiveData<Videos> getVideos(int movieId) {
        NetworkServiceHelper<Videos> callback = new NetworkServiceHelper<>();
        this.networkService.getVideos(movieId).enqueue(callback.getData());
        return callback.data;
    }
}
