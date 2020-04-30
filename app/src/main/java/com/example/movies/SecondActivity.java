package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SecondActivity extends AppCompatActivity {
    //Creating private variables for each of the activity elements based on type
    private EditText etMovie;
    private Button btnSearch;
    private Button btnPoster;
    private Button btnFavorite;
    private TextView tvTitle;
    private TextView tvGenre;
    private TextView tvYear;
    private TextView tvTime;
    private TextView tvActors;
    private TextView tvDirector;


    //String Variable to hold the userInput
    String userInput;
    //String Variable to hold the Poster URL
    public static String savedPoster;
    //String Variable to hold the Movie Title
    public static String savedTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //Obtaining references for each of the activity page elements
        etMovie = (EditText)findViewById(R.id.etMovie);
        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvGenre = (TextView)findViewById(R.id.tvGenre);
        tvYear = (TextView)findViewById(R.id.tvYear);
        tvTime = (TextView)findViewById(R.id.tvTime);
        tvActors = (TextView)findViewById(R.id.tvActors);
        tvDirector = (TextView)findViewById(R.id.tvDirector);
        btnSearch = (Button)findViewById(R.id.btnSearch);
        btnPoster = (Button)findViewById(R.id.btnPoster);
        btnFavorite = (Button)findViewById(R.id.btnFavorite);

        //Set the Poster and Favorite Buttons to unclickable initially
        btnPoster.setEnabled(false);
        //btnFavorite.setEnabled(false);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Defines what happens when the Search Button is pressed
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = etMovie.getText().toString();
                useAPI(userInput);
                btnPoster.setEnabled(true);
                //btnFavorite.setEnabled(true);
            }
        });

        //Defines what happens when the Poster Button is pressed
        btnPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPoster();
            }
        });

        //Defines what happens when the Favorite Button is pressed
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFavoriteList();
            }
        });
    }

    public void useAPI(String userInput){
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String URL = "https://www.omdbapi.com/?i=tt3896198&apikey=1f7c368&t=" + userInput;

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response){
                        try{
                            //Obtaining Data from the API
                            savedTitle = response.getString("Title");
                            String Genre = response.getString("Genre");
                            String Year = response.getString("Year");
                            String Time = response.getString("Runtime");
                            String Actors = response.getString("Actors");
                            String Director = response.getString("Director");
                            savedPoster = response.getString("Poster");
                            tvTitle.setText("Title: " + savedTitle);
                            tvGenre.setText("Genre: " + Genre);
                            tvYear.setText("Year of Release: " + Year);
                            tvTime.setText("Duration: " + Time);
                            tvActors.setText("Actors: " + Actors);
                            tvDirector.setText("Director: " + Director);
                        }catch(JSONException e){
                            tvTitle.setText("Movie");
                            tvGenre.setText("Not");
                            tvYear.setText("Found");
                            tvTime.setText("Please try again.");
                            tvActors.setText("- Movie Mania");
                            tvDirector.setText("");

                            e.printStackTrace();
                        }
                        Log.e("Rest JSON Response: ", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest JSON Response: ", error.toString());
                    }
                }
        );
        requestQueue.add(objectRequest);
    }

    public void goToPoster(){
        //Go from one activity page to a new one
        //1st Parameter = src.this // 2nd Parameter = dst.class
        Intent intent  = new Intent(SecondActivity.this, MoviePoster.class);
        //Call of startActivity passing it the intent (Loading the new activity page)
        startActivity(intent);
    }

    public void goToFavoriteList(){
        //Go from one activity page to a new one
        //1st Parameter = src.this // 2nd Parameter = dst.class
        Intent intent  = new Intent(SecondActivity.this, FavoriteList.class);
        //Call of startActivity passing it the intent (Loading the new activity page)
        startActivity(intent);
    }
}
