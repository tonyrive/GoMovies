package com.tonytekinsights.gomovies.services;

import com.tonytekinsights.gomovies.BuildConfig;
import com.tonytekinsights.gomovies.models.Movie;
import com.tonytekinsights.gomovies.models.MovieResults;
import com.tonytekinsights.gomovies.models.Videos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkService {
    @GET("movie/popular?api_key=" + BuildConfig.API_KEY)
    Call<MovieResults> getPopularMovies(@Query("page") int pageId);

    @GET("movie/top_rated?api_key=" + BuildConfig.API_KEY)
    Call<MovieResults> getTopRatedMovies(@Query("page") int pageId);

    @GET("movie/{movieId}?append_to_response=videos,reviews&api_key=" + BuildConfig.API_KEY)
    Call<Movie> getMovie(@Path("movieId") int id);

    @GET("movie/{movieId}/videos?api_key=" + BuildConfig.API_KEY)
    Call<Videos> getVideos(@Path("movieId") int id);

    //@GET("movie/{id}/reviews")
    //Call<Reviews>
}
