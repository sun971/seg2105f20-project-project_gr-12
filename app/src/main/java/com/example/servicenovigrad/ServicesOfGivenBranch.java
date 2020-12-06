package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServicesOfGivenBranch extends AppCompatActivity {

    ListView listOfServices;
    DatabaseReference databaseUsers;

    FirebaseDatabase db;
    List<String> servicesList;
    String branchName;
    String branchFirst;
    String branchLast;
    String branchID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_of_given_branch);

        //gets the service name passed from previous activity
        Bundle bundle = getIntent().getExtras();
        branchName = bundle.getString("branchName");

        TextView branchNameView = findViewById(R.id.branchNameText);
        branchNameView.setText(branchName);


        db = FirebaseDatabase.getInstance();
        servicesList = new ArrayList<String>();
        databaseUsers = db.getReference("users");
        listOfServices = (ListView) findViewById(R.id.serviceChoicesListView);

        String[] seperatedBranchName = branchName.split(" ");
        branchFirst = seperatedBranchName[0];
        branchLast = seperatedBranchName[1];


        Log.d("test","Branch first: " + branchFirst);
        Log.d("test","Branch last: " + branchLast);
        Log.d("test","Branch ID: " + branchID);
        Log.d("test","Updating?");

        listOfServices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String service = servicesList.get(i);
                ServiceChosen(service);
            }
        });
    }

    private void ServiceChosen(String service){
        Intent intent = new Intent(this, SubmitRequest.class);
        intent.putExtra("serviceName", service);
        intent.putExtra("branchID", branchID);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();



        // Retrieve average rating
        final RatingBar branchAverageRating = (RatingBar)findViewById(R.id.ratingBar4);
        DatabaseReference getRating = db.getReference("users/"+branchID);
        getRating.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.hasChild("ratings"))  {
                    branchAverageRating.setRating((float) 5.00);
                }
                else    {
                    branchAverageRating.setRating((float) 0);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("test", "CANCELLED");

            }
        });

        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("test","Accessed users");
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Log.d("test","New User");
                    if (postSnapshot.child("accountType").getValue().toString().equals("employee")) {
                        String childFirst = postSnapshot.child("firstName").getValue().toString();
                        String childLast = postSnapshot.child("lastName").getValue().toString();

                        Log.d("test","child first: " + childFirst);
                        Log.d("test","child last: " + childLast);
                        Log.d("test","Branch ID: " + branchID);

                        if (branchFirst.equals(childFirst) && branchLast.equals(childLast)) {
                            branchID = postSnapshot.child("uid").getValue().toString();
                            GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};

                            servicesList = postSnapshot.child("branchServices").getValue(t);

                            if (servicesList != null) {
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ServicesOfGivenBranch.this, R.layout.layout_services_list, R.id.textViewName, servicesList);
                                listOfServices.setAdapter(arrayAdapter);
                            }else{
                                servicesList = new ArrayList<>();
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ServicesOfGivenBranch.this, R.layout.layout_services_list, R.id.textViewName, servicesList);
                                listOfServices.setAdapter(arrayAdapter);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("test", "CANCELLED");

            }
        });

    }

    public void WelcomePage(View view) {
        Intent intent = new Intent(this, WelcomePage.class);
        startActivity(intent);
    }

    public void goToRateBranchPage(View view)    {
        Intent intent = new Intent(this, RateBranch.class);
        intent.putExtra("branchID", branchID);
        startActivity(intent);
    }
}