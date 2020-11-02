package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class AdminWelcome extends AppCompatActivity {
    private FirebaseAuth session;
    private FirebaseDatabase mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_welcome);

        session = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        getCurrentUser();
    }



    public void onStart()   {
        super.onStart();

        boolean loggedIn = false;

        //Check and redirect to login
        if(loggedIn == false)
        {
            Log.d("Msg","No one signed in");


        }

    }

    public void onClick(View v) {
        if (v.getId() == R.id.btnSignOut) {
            session.signOut();
            //sends back to login
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);

        }
    }



    public void getCurrentUser(){
        FirebaseUser user = session.getCurrentUser();
        if (user != null) {
            String id = user.getUid();
            DatabaseReference userData = mDatabase.getReference("users/" + id);

            userData.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            TextView helloTextView = (TextView) findViewById(R.id.welcomeMessage);
                            helloTextView.setText("Welcome "+snapshot.child("firstName").getValue()+"! you are logged in as "+snapshot.child("accountType").getValue());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    }

            );


        } else {

        }
    }


    public void deleteAccounts(View view) {
        if (view.getId() == R.id.DeleteAccounts) {
            Intent intent = new Intent(this, DeleteAccount.class);
            startActivity(intent);

        }
    }

    public void ManageServices(View view) {
        if (view.getId() == R.id.btnManageServices) {
           // session.signOut();
           Intent intent = new Intent(this, CurrentService.class);
            //add in the manage services view
            startActivity(intent);

        }
    }

    public void SignOut(View view) {
        if (view.getId() == R.id.btnSignOut) {
            session.signOut();
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);

        }
    }
}