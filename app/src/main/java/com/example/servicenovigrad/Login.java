package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private FirebaseAuth session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        session = FirebaseAuth.getInstance();
    }

    // JUST TESTING IF CAN READ IF USER IS LOGGED IN OR NOT
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = session.getCurrentUser();

        if(currentUser != null)
        {
            Intent intent = new Intent(this, WelcomePage.class);
            startActivity(intent);
            //Toast.makeText(Login.this, "USER LOGGED IN", Toast.LENGTH_LONG).show();
        }
        else
        {
            Log.d("Session","User NOT logged in");
            //Toast.makeText(Login.this, "USER NOT LOGGED IN", Toast.LENGTH_LONG).show();
        }


    }

    public void login(View view) {

            EditText emailField = (EditText)findViewById(R.id.email);
            EditText passwordField = (EditText)findViewById(R.id.password);

            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();

            if(email.equals("") || password.equals(""))
            {
                Toast.makeText(Login.this, "One or more fields has no value.", Toast.LENGTH_LONG).show();
            }
            else
            {
                Log.d("Email", email);
                Log.d("Password", password);

                session.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Msg", "signInWithEmail:success");
                            FirebaseUser user = session.getCurrentUser();
                            Log.d("Session",user.toString());

                            Toast.makeText(Login.this, "Authentication success.", Toast.LENGTH_LONG).show();

                            Intent redirectToMain = new Intent(Login.this, MainActivity.class);
                            startActivity(redirectToMain);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Error", "COULD NOT SIGN IN", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_LONG).show();
                            // ...
                        }

                        // ...
                    }
                });
            }



    }

    public void goToSignupPage(View view)
    {
        Intent intent = new Intent(this, Signup.class);

        startActivity(intent);
    }
}