package com.dima.myapplication.util;

import androidx.dynamicanimation.animation.SpringAnimation;

import com.dima.myapplication.data.Movie;
import com.dima.myapplication.data.Review;
import com.dima.myapplication.data.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONUtil {

    private static final String KEY_RESULTS = "results";
    //отзывы
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_CONTENT = "content";
    //видео
    private static final String KEY_KEY_OF_VIDEO = "key";
    private static final String KEY_NAME = "name";
    private static final String BASE_YOUTUBE_URL = "https://www.youtube.com/watch?v=";

    private static final String BASE_POSTER_URL = "https://image.tmdb.org/t/p/";
    private static final String SMALL_POSTER_SIZE = "w185";
    private static final String BIG_POSTER_SIZE = "w780";


    private static final String KEY_ID = "id";
    private static final String KEY_VOTE_COUNT = "vote_count";
    private static final String KEY_TITLE = "title";
    private static final String KEY_ORIGINAL_TITLE = "original_title";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_POSTER_PATH = "poster_path";
    private static final String KEY_BACKGROUND_PATH = "backdrop_path";
    private static final String KEY_VOTE_AVERAGE = "vote_average";
    private static final String KEY_RELEASE_DATE = "release_date";

    public static List<Trailer> getTrailersFromJSON(JSONObject jsonObject) {
        List<Trailer> result = new ArrayList<>();
        if (jsonObject == null) {
            return result;
        }

        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULTS);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectTrailer = jsonArray.getJSONObject(i);
                String key = BASE_YOUTUBE_URL + jsonObjectTrailer.getString(KEY_KEY_OF_VIDEO);
                String name = jsonObjectTrailer.getString(KEY_NAME);
                Trailer trailer = new Trailer(key, name);
                result.add(trailer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Review> getReviewsFromJSON(JSONObject jsonObject) {
        List<Review> result = new ArrayList<>();
        if (jsonObject == null) {
            return result;
        }

        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULTS);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectReview = jsonArray.getJSONObject(i);
                String author = jsonObjectReview.getString(KEY_AUTHOR);
                String content = jsonObjectReview.getString(KEY_CONTENT);
                Review review = new Review(author, content);
                result.add(review);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Movie> getMoviesFromJSON(JSONObject jsonObject) {
        List<Movie> result = new ArrayList<>();
        if (jsonObject == null) {
            return result;
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULTS);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject film = jsonArray.getJSONObject(i);
                int id = film.getInt(KEY_ID);
                int voteCount = film.getInt(KEY_VOTE_COUNT);
                String title = film.getString(KEY_TITLE);
                String originalTitle = film.getString(KEY_ORIGINAL_TITLE);
                String overview = film.getString(KEY_OVERVIEW);
                String posterPath = BASE_POSTER_URL + SMALL_POSTER_SIZE + film.getString(KEY_POSTER_PATH);
                String bigPosterPath = BASE_POSTER_URL + BIG_POSTER_SIZE + film.getString(KEY_POSTER_PATH);
                String backgroundPath = film.getString(KEY_BACKGROUND_PATH);
                double voteAverage = film.getDouble(KEY_VOTE_AVERAGE);
                String releaseDate = film.getString(KEY_RELEASE_DATE);
                Movie movie = new Movie(id, voteCount, title, originalTitle, overview, posterPath, bigPosterPath, backgroundPath, voteAverage, releaseDate);
                result.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
