package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RateBranch extends AppCompatActivity {

    FirebaseDatabase db;
    FirebaseAuth session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_branch);


        db = FirebaseDatabase.getInstance();
        session = FirebaseAuth.getInstance();
    }

    public void submitRating(View view)  {
        final String branchID = getIntent().getExtras().getString("branchID");

        RatingBar rating = (RatingBar)findViewById(R.id.ratingBar);
        EditText comment = (EditText)findViewById(R.id.ratingComment);

        final String id = session.getUid();

        final String ratingSubmitted = String.valueOf(rating.getRating());

        final String commentSubmitted = comment.getText().toString();

        // Check if ratings already exist
        final DatabaseReference getRating = db.getReference("users/"+branchID);
        getRating.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.hasChild("ratings"))  {

                    HashMap<String, ArrayList<String>> customerRatingEntries = new HashMap<>();

                    for(DataSnapshot ratingEntry: snapshot.child("ratings").getChildren())  {
                        //Log.d("RATING ENTRY", ratingEntry.toString());
                        GenericTypeIndicator<ArrayList<String>> ratingData = new GenericTypeIndicator<ArrayList<String>>() {};

                        ArrayList<String> databaseRatingEntry = ratingEntry.getValue(ratingData);

                        customerRatingEntries.put(ratingEntry.getKey(), databaseRatingEntry);
                        Log.d("RATING ENTRY", ratingEntry.toString());
                    }

                    ArrayList<String> submittedRatingData = new ArrayList<>();
                    submittedRatingData.add(ratingSubmitted);
                    submittedRatingData.add(commentSubmitted);

                    customerRatingEntries.put(id, submittedRatingData);



                    DatabaseReference dbRequestsRef = db.getReference("users/"+branchID+"/ratings");

                    dbRequestsRef.setValue(customerRatingEntries);
                }
                else    {
                    ArrayList<String> submittedRatingData = new ArrayList<>();
                    submittedRatingData.add(ratingSubmitted);
                    submittedRatingData.add(commentSubmitted);
                    HashMap<String, ArrayList<String>> customerRatingEntries = new HashMap<>();
                    customerRatingEntries.put(id, submittedRatingData);

                    DatabaseReference dbRequestsRef = db.getReference("users/"+branchID).child("ratings");

                    dbRequestsRef.setValue(customerRatingEntries);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("test", "CANCELLED");

            }
        });

        Toast.makeText(getApplicationContext(), "Rating submitted for branch", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, WelcomePage.class);
        startActivity(intent);
    }

    public void goToWelcomePage(View view)  {
        Intent intent = new Intent(this, WelcomePage.class);
        startActivity(intent);
    }
}