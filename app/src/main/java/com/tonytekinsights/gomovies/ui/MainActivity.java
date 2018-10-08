package com.tonytekinsights.gomovies.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.tonytekinsights.gomovies.R;
import com.tonytekinsights.gomovies.adapter.MovieListAdapter;
import com.tonytekinsights.gomovies.models.MovieResults;
import com.tonytekinsights.gomovies.viewmodel.MoviesViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private String recyclerState = "recyclerState";
    @BindView(R.id.rv_movies) RecyclerView recycler;
    @BindView(R.id.pg_bar) ProgressBar pgBar;
    @BindView(R.id.bottom_nav_bar) BottomNavigationView bottomNavBar;
    /*@BindView(R.id.main_frame) FrameLayout mainFrame;*/

    private MovieListAdapter adapter;
    private GridLayoutManager layoutManager;
    private MoviesViewModel viewModel;
    private int pageId = 1;
    private RequestType requestType = RequestType.POPULAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        int numOfCol = 3;
        layoutManager = new GridLayoutManager(this, numOfCol);

        bottomNavBar.setOnNavigationItemSelectedListener(getItemSelectedListener());

        recycler.setHasFixedSize(true);
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setLayoutManager(layoutManager);
        recycler.addOnScrollListener(getScrollListener(layoutManager));

        adapter = new MovieListAdapter();
        recycler.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        getMovies();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        bottomNavBar.setSelectedItemId(itemId);
        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener getItemSelectedListener() {
        return item -> {
            doItemSelected(item.getItemId());
            return true;
        };
    }

    private void doItemSelected(int itemId) {
        pageId = 1;
        switch (itemId) {
            case R.id.navigation_popular:
                requestType = RequestType.POPULAR;
                getMovies();
                break;
            case R.id.navigation_top_rated:
                requestType = RequestType.TOP_RATED;
                getMovies();
                break;
        }
    }

    private OnScrollListener getScrollListener(final GridLayoutManager layoutManager) {
        return new OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                int visibleThreshold = 1;

                if (totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    pageId++;
                    getMovies();
                }
            }
        };
    }

    private void getMovies() {
        pgBar.setVisibility(View.VISIBLE);
        switch (requestType) {
            case POPULAR:
                viewModel.getPopularMovies(pageId).observe(this, this::onChange);
                break;
            case TOP_RATED:
                viewModel.getTopRatedMovies(pageId).observe(this, this::onChange);
                break;
        }
    }

    private void onChange(MovieResults movieResults) {
        if (pageId == 1) adapter.setData(movieResults);
        else adapter.addMovies(movieResults);
        adapter.notifyDataSetChanged();
        pgBar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(
                recyclerState,
                layoutManager.onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null){
            recycler.getLayoutManager().onRestoreInstanceState(
                    savedInstanceState.getParcelable(recyclerState)
            );
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public enum RequestType {
        POPULAR,
        TOP_RATED
    }
}
