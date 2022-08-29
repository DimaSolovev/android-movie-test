package com.dima.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.dima.myapplication.adapter.MovieAdapter;
import com.dima.myapplication.data.FavouriteMovie;
import com.dima.myapplication.data.MainViewModel;
import com.dima.myapplication.data.Movie;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFavourite;
    private MovieAdapter movieAdapter;
    private MainViewModel viewModel;

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.itemMain:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.itemFavourite:
                Intent favouriteIntent = new Intent(this, FavouriteActivity.class);
                startActivity(favouriteIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        recyclerViewFavourite = findViewById(R.id.recyclerViewFavourite);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        movieAdapter = new MovieAdapter();
        recyclerViewFavourite.setAdapter(movieAdapter);
        recyclerViewFavourite.setLayoutManager(new GridLayoutManager(this, 2));
        LiveData<List<FavouriteMovie>> favouriteMovies = viewModel.getFavouriteMovie();
        favouriteMovies.observe(this, new Observer<List<FavouriteMovie>>() {
            @Override
            public void onChanged(List<FavouriteMovie> favouriteMovies) {
                List<Movie> movies = new ArrayList<>();
                if (favouriteMovies != null) {
                    movies.addAll(favouriteMovies);
                    movieAdapter.setMovies(movies);
                }
            }
        });

        movieAdapter.setOnPosterClickListener(new MovieAdapter.OnPosterClickListener() {
            @Override
            public void onPosterClick(int id) {
                Movie movie = movieAdapter.getMovies().get(id);
                Intent intent = new Intent(FavouriteActivity.this, DetailActivity.class);
                intent.putExtra("id", movie.getId());
                startActivity(intent);
            }
        });

        movieAdapter.setOnReachEndListener(new MovieAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
                Toast.makeText(FavouriteActivity.this, "End", Toast.LENGTH_SHORT).show();
            }
        });
    }
}