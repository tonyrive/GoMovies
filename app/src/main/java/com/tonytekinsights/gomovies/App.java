package com.tonytekinsights.gomovies;

import android.app.Application;

public class App extends Application {
    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = App.this;
    }

    public static Application getInstance() { return app; }
}
