package com.dima.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

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
    private TextView textViewPopularity;
    private TextView textViewRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewPosters = findViewById(R.id.recyclerViewPosters);
        movieAdapter = new MovieAdapter();
        switchSort = findViewById(R.id.switchSort);
        textViewPopularity = findViewById(R.id.textViewPopularity);
        textViewRating = findViewById(R.id.textViewRating);
        recyclerViewPosters.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewPosters.setAdapter(movieAdapter);
        switchSort.setChecked(true);
        switchSort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setMethodOfSort(isChecked);
            }
        });
        switchSort.setChecked(false);
    }

    private void setMethodOfSort(boolean isChecked){
        int methodOfSort;
        if(isChecked){
            methodOfSort = NetworkUtil.TOP_RATED;
            textViewRating.setTextColor(getResources().getColor(R.color.pink));
            textViewPopularity.setTextColor(getResources().getColor(R.color.white));

        }else {
            methodOfSort = NetworkUtil.POPULARITY;
            textViewPopularity.setTextColor(getResources().getColor(R.color.pink));
            textViewRating.setTextColor(getResources().getColor(R.color.white));
        }
        JSONObject jsonObject = NetworkUtil.getJSONFromNetwork(1, methodOfSort);
        List<Movie> movies = JSONUtil.getMoviesFromJSON(jsonObject);
        movieAdapter.setMovies(movies);
    }


    public void onClickPopularity(View view) {
        setMethodOfSort(false);
        switchSort.setChecked(false);
    }

    public void onClickRating(View view) {
        setMethodOfSort(true);
        switchSort.setChecked(true);
    }
}