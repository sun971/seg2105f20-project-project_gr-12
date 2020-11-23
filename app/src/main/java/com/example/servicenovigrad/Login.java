package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private FirebaseAuth session;
    private FirebaseDatabase mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        session = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();//new

    }

    // JUST TESTING IF CAN READ IF USER IS LOGGED IN OR NOT
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = session.getCurrentUser();


        if(currentUser != null)
        {
            if (currentUser.equals("admin")) {
                Intent intent = new Intent(this, AdminWelcome.class);
                startActivity(intent);
            }

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

                if(email.equals("admin") && password.equals("admin")){
                    email = "admin@admin.com";
                    password = "adminpassword";
                }

                session.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        EditText emailField = (EditText)findViewById(R.id.email);
                        EditText passwordField = (EditText)findViewById(R.id.password);

                        String email = emailField.getText().toString();
                        String password = passwordField.getText().toString();

                        if(email.equals("admin") && password.equals("admin")){
                            email = "admin@admin.com";
                            password = "adminpassword";
                        }

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Msg", "signInWithEmail:success");
                            FirebaseUser user = session.getCurrentUser();
                            Log.d("Session", user.toString());

                            Toast.makeText(Login.this, "Authentication success.", Toast.LENGTH_LONG).show();

                            //Redirect if welcome for admin if user is signed in as an admin
                            if (email.equals("admin@admin.com") && password.equals("adminpassword")) {
                                Intent redirectToWelcome = new Intent(Login.this, AdminWelcome.class);
                                startActivity(redirectToWelcome);
                            }
                            else if (!email.equals("admin@admin.com")) {
                                //check if user is an employee account
                                final String id = user.getUid();
                                DatabaseReference userData = mDatabase.getReference("users/" + id);
                                //DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                                //DatabaseReference userNameRef = rootRef.child("Users").child("Nick123");

                                userData.addListenerForSingleValueEvent(
                                        new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                EmployeeAccount emp = new EmployeeAccount(id, snapshot.child("firstName").getValue().toString(), snapshot.child("lastName").getValue().toString(), snapshot.child("eMail").getValue().toString(), snapshot.child("password").getValue().toString());

                                                boolean hasAddress = true;
                                                if ((snapshot.child("address").getValue()) ==null){
                                                    hasAddress=  false;
                                                }


                                                //need to add in checks to make sure the employee number is checked
                                                if((snapshot.child("accountType").getValue()).equals("employee") && hasAddress){
                                                    Intent redirectToWelcome = new Intent(Login.this, EmployeeWelcome.class);
                                                    startActivity(redirectToWelcome);

                                                }else if ((snapshot.child("accountType").getValue()).equals("employee") && !(hasAddress)){
                                                    Intent redirectToSetInfo = new Intent(Login.this, SetEmployeeInfo.class);
                                                    startActivity(redirectToSetInfo);

                                                }else{
                                                    Intent redirectToWelcomePage = new Intent(Login.this, WelcomePage.class);
                                                    startActivity(redirectToWelcomePage);
                                                }
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }

                                        }

                                );
                            }

                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.w("Error", "COULD NOT SIGN IN", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_LONG).show();

                        }


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