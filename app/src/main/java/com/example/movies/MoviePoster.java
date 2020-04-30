package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MoviePoster extends AppCompatActivity {
    //Creating private variables for each of the activity elements based on type
    private WebView wvPoster;
    private Button btnBack;

    //Obtain the savedPoster URL from the SecondActivity
    String userPoster = SecondActivity.savedPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_poster);

        //Obtaining references for each of the activity page elements
        wvPoster = (WebView)findViewById(R.id.wvPoster);
        btnBack = (Button)findViewById(R.id.btnBack);

        //Load the Poster based on the userInput
        loadImageFromURL(userPoster);

        //Defines what happens when the back button is pressed
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go back to the previous activity page
                //Go from one activity page to a new one
                //1st Parameter = src.this // 2nd Parameter = dst.class
                Intent intent  = new Intent(MoviePoster.this, SecondActivity.class);
                //Call of startActivity passing it the intent (Loading the new activity page)
                startActivity(intent);
            }
        });
    }

    public void loadImageFromURL(String URL){
        wvPoster.loadUrl(URL);
    }
}
