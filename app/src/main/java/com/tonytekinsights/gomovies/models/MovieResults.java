package com.tonytekinsights.gomovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieResults implements Parcelable {
    @SerializedName("page")
    public Integer page;

    @SerializedName("totalMovies")
    public Integer totalMovies;

    @SerializedName("totalPages")
    public Integer totalPages;

    @SerializedName("results")
    public List<Movie> movies;

    public MovieResults() {
        this.movies = new ArrayList<>();
    }

    public MovieResults(Integer page, Integer totalMovies, Integer totalPages, List<Movie> movies) {
        super();
        this.page = page;
        this.totalMovies = totalMovies;
        this.totalPages = totalPages;
        this.movies = movies;
    }

    public Integer getSize() { return this.movies.size() -1;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.page);
        dest.writeValue(this.totalMovies);
        dest.writeValue(this.totalPages);
        dest.writeTypedList(this.movies);
    }

    protected MovieResults(Parcel in) {
        this.page = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalMovies = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalPages = (Integer) in.readValue(Integer.class.getClassLoader());
        this.movies = in.createTypedArrayList(Movie.CREATOR);
    }

    public static final Parcelable.Creator<MovieResults> CREATOR = new Parcelable.Creator<MovieResults>() {
        @Override
        public MovieResults createFromParcel(Parcel source) {
            return new MovieResults(source);
        }

        @Override
        public MovieResults[] newArray(int size) {
            return new MovieResults[size];
        }
    };
}
