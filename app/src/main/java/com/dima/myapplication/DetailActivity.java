package com.dima.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dima.myapplication.data.FavouriteMovie;
import com.dima.myapplication.data.MainViewModel;
import com.dima.myapplication.data.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageViewBigPoster;
    private TextView textViewTitle;
    private TextView textViewOriginalTitle;
    private TextView textViewRating;
    private TextView textViewDate;
    private TextView textViewOverview;
    private ImageView imageViewAddToFavourite;

    private int id;
    private Movie movie;
    private MainViewModel viewModel;
    private FavouriteMovie favouriteMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imageViewBigPoster = findViewById(R.id.imageViewBigPoster);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewOriginalTitle = findViewById(R.id.textViewOriginalTitle);
        textViewRating = findViewById(R.id.textViewRating);
        textViewDate = findViewById(R.id.textViewDate);
        textViewOverview = findViewById(R.id.textViewOverview);
        imageViewAddToFavourite = findViewById(R.id.imageViewAddToFavourite);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            id = intent.getIntExtra("id", -1);
        } else {
            finish();
        }
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        movie = viewModel.getMovieById(id);
        Picasso.get().load(movie.getBigPosterPath()).into(imageViewBigPoster);
        textViewTitle.setText(movie.getTitle());
        textViewOriginalTitle.setText(movie.getOriginalTitle());
        textViewRating.setText(Double.toString(movie.getVoteAverage()));
        textViewDate.setText(movie.getReleaseDate());
        textViewOverview.setText(movie.getOverview());
        setFavourite();
    }

    public void onClickChangeFavourite(View view) {
        if (favouriteMovie == null) {
            viewModel.insertFavouriteMovie(new FavouriteMovie(movie));
            Toast.makeText(this, "Added to favourite", Toast.LENGTH_SHORT).show();
        } else {
            viewModel.deleteFavouriteMovie(favouriteMovie);
            Toast.makeText(this, "Deleted from favourite", Toast.LENGTH_SHORT).show();
        }
        setFavourite();
    }

    public void setFavourite() {
        favouriteMovie = viewModel.getFavouriteMovieById(id);

        if(favouriteMovie == null){
            imageViewAddToFavourite.setImageResource(R.drawable.favourite_add_to);
        }else {
            imageViewAddToFavourite.setImageResource(R.drawable.favourite_remove);
        }
    }
}