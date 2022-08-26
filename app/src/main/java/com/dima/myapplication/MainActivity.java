package com.dima.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dima.myapplication.adapter.MovieAdapter;
import com.dima.myapplication.data.MainViewModel;
import com.dima.myapplication.data.Movie;
import com.dima.myapplication.util.JSONUtil;
import com.dima.myapplication.util.NetworkUtil;

import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPosters;
    private MovieAdapter movieAdapter;
    private Switch switchSort;
    private TextView textViewPopularity;
    private TextView textViewRating;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewPosters = findViewById(R.id.recyclerViewPosters);
        movieAdapter = new MovieAdapter();
        switchSort = findViewById(R.id.switchSort);
        textViewPopularity = findViewById(R.id.textViewPopularity);
        textViewRating = findViewById(R.id.textViewRating);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
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
        movieAdapter.setOnPosterClickListener(new MovieAdapter.OnPosterClickListener() {
            @Override
            public void onPosterClick(int id) {
                Toast.makeText(MainActivity.this, "Click " + id, Toast.LENGTH_SHORT).show();
            }
        });
        movieAdapter.setOnReachEndListener(new MovieAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
                Toast.makeText(MainActivity.this, "End", Toast.LENGTH_SHORT).show();
            }
        });
        LiveData<List<Movie>> moviesFromLiveData = viewModel.getMovies();
        moviesFromLiveData.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieAdapter.setMovies(movies);
            }
        });
    }

    private void setMethodOfSort(boolean isChecked) {
        int methodOfSort;
        if (isChecked) {
            methodOfSort = NetworkUtil.TOP_RATED;
            textViewRating.setTextColor(getResources().getColor(R.color.pink));
            textViewPopularity.setTextColor(getResources().getColor(R.color.white));

        } else {
            methodOfSort = NetworkUtil.POPULARITY;
            textViewPopularity.setTextColor(getResources().getColor(R.color.pink));
            textViewRating.setTextColor(getResources().getColor(R.color.white));
        }
        downloadData(methodOfSort,1);
    }

    private void downloadData(int methodOfSort, int page){
        JSONObject jsonObject = NetworkUtil.getJSONFromNetwork(1, methodOfSort);
        List<Movie> movies = JSONUtil.getMoviesFromJSON(jsonObject);
        if(movies != null && !movies.isEmpty()){
            viewModel.deleteAllMovies();
            for (Movie movie: movies){
                viewModel.insertMovie(movie);
            }
        }
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