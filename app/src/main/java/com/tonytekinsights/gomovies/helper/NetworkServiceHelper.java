package com.tonytekinsights.gomovies.helper;

import android.arch.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkServiceHelper<T> {
    public MutableLiveData<T> data = new MutableLiveData<>();

    public Callback<T> getData() {
        return new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                try {
                    data.setValue(response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                try {
                    throw new InterruptedException("There was an error!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}