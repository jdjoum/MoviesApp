package com.jdjoum.movies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

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
    private RatingBar rbRatingBar;


    //String Variable to hold the userInput
    String userInput;
    public static int index;
    public static int numRatings = 0;
    public static boolean reloadRating = false;
    //String Variable to hold the Poster URL
    public static String savedPoster;
    //String Variable to hold the Movie Title
    public static String savedTitle;
    //ArrayList String for the ratings
    public static ArrayList<String> ratings = new ArrayList<>();
    //ArrayList String for the titles
    public static ArrayList<String> titles = new ArrayList<>();

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
        rbRatingBar = findViewById(R.id.rbRating);

        //Set the Poster and Favorite Buttons to unclickable initially
        btnPoster.setEnabled(false);
        //Set the Rating Bar to unclickable initially
        rbRatingBar.setEnabled(false);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Defines what happens when the Rating Bar is pressed
        rbRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                numRatings++;
                String rateValue = String.valueOf(rbRatingBar.getRating());

                //If the rating is not reset to 0.0 for a new movie title, save the rating
                if(!rateValue.equals("0.0") && reloadRating == false) {
                    Log.d("(myTag)", "User Rated " + savedTitle + " " + rateValue + "/5.0");
                    saveRating(ratings, titles, rateValue, savedTitle);
                }
            }
        });

        //Defines what happens when the Search Button is pressed
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    userInput = etMovie.getText().toString();
                    useAPI(userInput);
                }catch (Exception e){
                    btnPoster.setEnabled(false);
                    tvTitle.setText("Movie");
                    tvGenre.setText("Not");
                    tvYear.setText("Found");
                    tvTime.setText("Please try again.");
                    tvActors.setText("- Movie Mania");
                    tvDirector.setText("");

                    e.printStackTrace();
                }
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

    //Reload  a movie that has already been rated
    public boolean reloadRating(ArrayList<String> ratings, ArrayList<String> titles){
        reloadRating = false;
        //Reload the rated movie
        for (int i = titles.size()-1; i >= 0; i--) {
            if(savedTitle.equals(titles.get(i))) {
                Log.d("(myTag)", "The movie rating needs to be reloaded");
                reloadRating = true;
                index = i;
                Log.d("(myTag)", String.valueOf(i));
                //Reload the previous rating to the rating bar, if a movie is researched
                setRating(ratings, index);
                break;
            }
        }
        return reloadRating;
    }

    //Save the user's rating into the lists
    public void saveRating(ArrayList<String> savedRatings, ArrayList<String> savedTitles,String userRating, String movieTitle){
        savedRatings.add(userRating);
        savedTitles.add(movieTitle);
        Log.d("(myTag)", "Movie Rating "+ savedRatings.size() +" Saved to Lists");
    }

    public void setRating(ArrayList<String> ratings, int index){
        String tempRating = ratings.get(index);
        float floatRating = Float.parseFloat(tempRating);
        rbRatingBar.setRating(floatRating);
        //Toast.makeText(SecondActivity.this, "Movie Rating Reloaded" + floatRating, Toast.LENGTH_SHORT).show();
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
                            //Check if the movie rating needs to be reloaded, if yes reload it
                            boolean reload = reloadRating(ratings, titles);
                            //Saves new ratings to lists after the Movie Search page is returned to
                            if(reload == true){
                                rbRatingBar.setEnabled(true);
                                rbRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

                                    @Override
                                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                                        numRatings++;
                                        String rateValue = String.valueOf(rbRatingBar.getRating());
                                        Log.d("(myTag)", "The Current Rate Value is: " + rateValue + "/5.0");
                                        //If the rating is not reset to 0.0 for a new movie title, save the rating
                                        if(!rateValue.equals("0.0")) {
                                            Log.d("(myTag)", "User Rated " + savedTitle + " " + rateValue + "/5.0");
                                            saveRating(ratings, titles, rateValue, savedTitle);
                                        }
                                    }
                                });
                            }
                            //If no, set the rating bar to 0
                            if(reload == false) {
                                Log.d("(myTag)", "No reload about to be rated for the first time");
                                rbRatingBar.setEnabled(true);
                                //Reset the rating bar to 0.0
                                rbRatingBar.setRating(0);
                            }
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
                            btnPoster.setEnabled(true);
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
