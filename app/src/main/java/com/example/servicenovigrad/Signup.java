package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void onStart()   {
        super.onStart();


    }

    public void goToLoginPage(View view) {
        Intent intent = new Intent(this, Login.class);

        startActivity(intent);
    }

    public void signup(View view) {
        EditText firstNameField = (EditText)findViewById(R.id.signupFirstName);
        EditText lastNameField = (EditText)findViewById(R.id.signupLastName);
        EditText usernameField = (EditText)findViewById(R.id.signupUsername);
        EditText emailField = (EditText)findViewById(R.id.signupEmail);

        String firstName = firstNameField.getText().toString();
        String lastName = lastNameField.getText().toString();
        String userName = usernameField.getText().toString();
        String email = emailField.getText().toString();

        Log.d("First Name", firstName);
        Log.d("Last Name", lastName);
        Log.d("Username",userName);
        Log.d("Email",email);
    }
}