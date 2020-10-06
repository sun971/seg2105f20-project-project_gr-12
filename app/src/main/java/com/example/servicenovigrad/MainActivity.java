package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import static com.example.servicenovigrad.R.layout.activity_login;
import static com.example.servicenovigrad.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean loggedIn = false;

        if(loggedIn == true) {
            setContentView(activity_main);
        }
        else {
            Intent intent = new Intent(this, Login.class);

            startActivity(intent);
        }

    }
}