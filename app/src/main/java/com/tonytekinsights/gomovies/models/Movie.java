package com.tonytekinsights.gomovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {
    @SerializedName("id")
    public int id;

    @SerializedName("title")
    public String title;

    @SerializedName("poster_path")
    public String poster_path;

    @SerializedName("backdrop_path")
    public String backdrop_path;

    @SerializedName("overview")
    public String overview;

    @SerializedName("release_date")
    public String release_date;

    @SerializedName("vote_average")
    public String vote_average;

    @SerializedName("runtime")
    public String runtime;

    @SerializedName("videos")
    public Videos videos;

    public Movie() { }

    public Movie(int id, String title, String posterPath, String backdropPath, String overview, String releaseDate, String score) {
        this(id, title, posterPath, backdropPath, overview, releaseDate, score, null, null);
    }

    public Movie(int id, String title, String poster_path, String backdrop_path, String overview, String release_date, String vote_average, String runtime, Videos videos) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.runtime = runtime;
        this.videos = videos;
    }

    protected Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        poster_path = in.readString();
        backdrop_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
        vote_average = in.readString();
        runtime = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(title);
        dest.writeValue(poster_path);
        dest.writeValue(backdrop_path);
        dest.writeValue(overview);
        dest.writeValue(release_date);
        dest.writeValue(vote_average);
        dest.writeValue(runtime);
        dest.writeValue(videos);
    }
}
