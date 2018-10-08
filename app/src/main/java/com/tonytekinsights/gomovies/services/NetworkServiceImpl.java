package com.tonytekinsights.gomovies.services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tonytekinsights.gomovies.App;
import com.tonytekinsights.gomovies.BuildConfig;
import com.tonytekinsights.gomovies.R;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkServiceImpl {

    static Retrofit GetNetworkService() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        int cacheSize = 10 * 1024 * 1024;

        Cache cache = new Cache(App.getInstance().getCacheDir(), cacheSize);

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(chain ->
        {
            Request baseRequest = chain.request();
            Request request = baseRequest
                    .newBuilder()
                    .method(baseRequest.method(), baseRequest.body())
                    .build();

            return chain.proceed(request);
        });

        okHttpClientBuilder.cache(cache);
        OkHttpClient client = okHttpClientBuilder.build();
        return new Retrofit
                .Builder()
                .baseUrl(BuildConfig.TMDB_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static void noInternet(View v) {
        final Snackbar snackbar = Snackbar.make(v,
                R.string.no_network, Snackbar.LENGTH_LONG);

        snackbar.setAction(R.string.dismiss, v1 -> snackbar.dismiss());
        snackbar.show();
    }
}
