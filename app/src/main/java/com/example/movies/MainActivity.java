package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Creating private variables for each of the activity elements based on type
    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private CheckBox cbShowPassword;

    //Creating a private counter variable for attempts left
    private int counter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Setting each of the private variables to their corresponding activity elements id
        Name = (EditText)findViewById(R.id.etName);
        Password = (EditText)findViewById(R.id.etPassword);
        Info = (TextView)findViewById(R.id.tvInfo);
        Login = (Button)findViewById(R.id.btnLogin);
        cbShowPassword = (CheckBox)findViewById(R.id.cbShowPasword);

        //Showing that the number of attempts left is 5 initially
        Info.setText("Number of Attempts Left: " + String.valueOf(counter));

        //Defines what happens when the login button is pressed
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });

        //Defines what happens when the show password checkbox is checked
        cbShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }

    private void validate(String userName, String userPassword){
        if(userName.equals("Admin") && userPassword.equals("1234")){
            //Go from one activity page to a new one
            //1st Parameter = src.this // 2nd Parameter = dst.class
            Intent intent  = new Intent(MainActivity.this, SecondActivity.class);
            //Call of startActivity passing it the intent (Loading the new activity page)
            startActivity(intent);
        }else{
            counter--;
            Info.setText("Number of Attempts Left: " + String.valueOf(counter));
            if(counter == 0){
                //Disables the button when the  failed attempts counter reaches 0
                Login.setEnabled(false);
                Toast.makeText(this, "Out of Attempts", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
