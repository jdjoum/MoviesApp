package com.example.movies;

import android.app.Activity;

import com.android.volley.RequestQueue;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MoviesService {

    private static final String URL = "http://www.omdbapi.com/?apikey=";
    private static final String API_KEY = "1f7c368";

    private RequestQueue queue;
    private static final String MOVIE_TAG = "CURRENT_MOVIE";

    public MoviesService(final Activity activity) {
        queue = Volley.newRequestQueue(activity.getApplicationContext());
    }

    public interface MoviesCallback {

        void onMovies(final Movies movies);

        void onError(Exception exception);
    }

    public void getMovies(final String movieTitle, final MoviesCallback callback) {
        final String url = String.format("%s?q=%s&appId=%s", URL, movieTitle, API_KEY);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Code for the whole response
                            final JSONObject moviesJSONObject = new JSONObject(response);
                            //Access the "Title" element in the response
                            //final JSONObject title = moviesJSONObject.getJSONObject("Title");
                            final String movieName = moviesJSONObject.getString("Title");
                            final String movieGenre = moviesJSONObject.getString("Genre");
                            final Movies movies = new Movies(movieName, movieGenre);
                            callback.onMovies(movies);
                        } catch (JSONException e) {
                            callback.onError(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        });
        stringRequest.setTag(MOVIE_TAG);
        queue.add(stringRequest);
    }

    public void cancel() {
        queue.cancelAll(MOVIE_TAG);
    }
}
