package com.example.movies;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Movies {


    final String movieName;
    final String movieGenre;

    public Movies(final String movieName, final String movieGenre) {
        this.movieName = movieName;
        this.movieGenre = movieGenre;
    }





}
