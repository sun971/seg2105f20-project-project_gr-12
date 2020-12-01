package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    if (postSnapshot.child("accountType").getValue().toString().equals("employee")) {
                        String childFirst = postSnapshot.child("firstName").getValue().toString();
                        String childLast = postSnapshot.child("lastName").getValue().toString();
                        if (branchFirst.equals(childFirst) && branchLast.equals(childLast)) {
                            branchID = postSnapshot.child("uid").getValue().toString();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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

        //Finds all the current services in the database and displays them in list view
        databaseUsers.child(branchID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};

                servicesList = snapshot.child("branchServices").getValue(t);

                if (servicesList != null) {
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ServicesOfGivenBranch.this, R.layout.layout_services_list, R.id.textViewName, servicesList);
                    listOfServices.setAdapter(arrayAdapter);
                }else{
                    servicesList = new ArrayList<>();
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ServicesOfGivenBranch.this, R.layout.layout_services_list, R.id.textViewName, servicesList);
                    listOfServices.setAdapter(arrayAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void WelcomePage(View view) {
        Intent intent = new Intent(this, CustomerWelcomePage.class);
        startActivity(intent);
    }
}