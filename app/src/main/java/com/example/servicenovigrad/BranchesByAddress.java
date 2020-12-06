package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BranchesByAddress extends AppCompatActivity {
    ListView listOfBranches;
    DatabaseReference databaseServices;

    List<String> branches;
    FirebaseDatabase db;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branches_by_service_type);

        Bundle bundle = getIntent().getExtras();
        address = bundle.getString("address");



        db = FirebaseDatabase.getInstance();
        databaseServices = db.getReference("users");
        listOfBranches = (ListView) findViewById(R.id.BranchesListView);
        branches = new ArrayList<String>();

        listOfBranches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String branch = branches.get(i);
                BranchChosen(branch);
            }
        });
    }


    private void BranchChosen(String branch){
        Intent intent = new Intent(this, ServicesOfGivenBranch.class);
        intent.putExtra("branchName", branch);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Finds all the current services in the database and displays them in list view
        databaseServices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                branches.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if (postSnapshot.child("accountType").getValue().toString().equals("employee")) {
                        //address.getKey();
                        //EmployeeAccount emp = new EmployeeAccount(id, snapshot.child("firstName").getValue().toString(), snapshot.child("lastName").getValue().toString(), snapshot.child("eMail").getValue().toString(), snapshot.child("password").getValue().toString());
                        if (postSnapshot.child("address").getValue().toString().equals(address)){
                            String branchName = postSnapshot.child("firstName").getValue().toString() + " " + postSnapshot.child("lastName").getValue().toString();
                            branches.add(branchName);
                        }
                    }
                }
                //Collections.sort(branches);

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(BranchesByAddress.this, R.layout.layout_services_list, R.id.textViewName, branches);
                listOfBranches.setAdapter(arrayAdapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}