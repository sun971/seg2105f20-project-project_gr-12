package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import static com.example.servicenovigrad.R.layout.activity_login;
import static com.example.servicenovigrad.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);


    }

    public void onStart()   {
        super.onStart();

        boolean loggedIn = false;

        //Check and redirect to login
        if(loggedIn == false)
        {
            Log.d("Msg","No one signed in");

            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }

    }
}