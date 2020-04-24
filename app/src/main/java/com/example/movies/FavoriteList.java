package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movies.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FavoriteList extends AppCompatActivity implements AdapterView.OnItemClickListener {
    //Creating private variables for each of the activity elements based on type
    private Button btnBack2;
    private ListView lvFavorite;

    //Obtain the savedTitle from the SecondActivity
    String userTitle = SecondActivity.savedTitle;

    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);

        //Obtaining references for each of the activity page elements
        btnBack2 = (Button)findViewById(R.id.btnBack2);
        lvFavorite = (ListView)findViewById(R.id.lvFavorite);

        items = FileHelper.readData(this);

        adapter = new ArrayAdapter<String>(this, R.layout.row, items);
        lvFavorite.setAdapter(adapter);

        //Add the Favorite Movie to the List
        addFavorite(userTitle);

        //Defines what happens when the back button is pressed
        btnBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go back to the previous activity page
                //Go from one activity page to a new one
                //1st Parameter = src.this // 2nd Parameter = dst.class
                Intent intent  = new Intent(FavoriteList.this, SecondActivity.class);
                //Call of startActivity passing it the intent (Loading the new activity page)
                startActivity(intent);
            }
        });
        //Defines what happens when an item in the list is clicked
        lvFavorite.setOnItemClickListener(this);
    }

    public void addFavorite(String movieTitle){
        adapter.add(movieTitle);
        FileHelper.writeData(items, this);
        Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        items.remove(position);
        adapter.notifyDataSetChanged();
        FileHelper.writeData(items, this);
        Toast.makeText(this, "Item Deleted", Toast.LENGTH_SHORT).show();
    }
}
