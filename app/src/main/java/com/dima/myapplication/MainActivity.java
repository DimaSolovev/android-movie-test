package com.dima.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dima.myapplication.adapter.MovieAdapter;
import com.dima.myapplication.data.Movie;
import com.dima.myapplication.util.JSONUtil;
import com.dima.myapplication.util.NetworkUtil;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPosters;
    private MovieAdapter movieAdapter;
    private Switch switchSort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewPosters = findViewById(R.id.recyclerViewPosters);
        movieAdapter = new MovieAdapter();
        switchSort = findViewById(R.id.switchSort);
        recyclerViewPosters.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewPosters.setAdapter(movieAdapter);
        switchSort.setChecked(true);
        switchSort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    JSONObject jsonObject = NetworkUtil.getJSONFromNetwork(1, NetworkUtil.TOP_RATED);
                    List<Movie> movies = JSONUtil.getMoviesFromJSON(jsonObject);
                    movieAdapter.setMovies(movies);
                }else {
                    JSONObject jsonObject = NetworkUtil.getJSONFromNetwork(1, NetworkUtil.POPULARITY);
                    List<Movie> movies = JSONUtil.getMoviesFromJSON(jsonObject);
                    movieAdapter.setMovies(movies);
                }
            }
        });
        switchSort.setChecked(false);
    }
}