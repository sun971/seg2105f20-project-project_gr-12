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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListHoursGivenDate extends AppCompatActivity {
/*
// first attempt by search with just Monday
    ListView ListOfHours;
    //ListOfHours
    DatabaseReference databaseHours;

    List<String> hours;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_hours_given_date);

        db = FirebaseDatabase.getInstance();
        //get the user
        databaseHours = db.getReference("users");
        ListOfHours = (ListView) findViewById(R.id.ListOfHours);

        //
        hours = new ArrayList<String>();

        ListOfHours.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String service = hours.get(i);
                HoursChosen(service);
            }
        });

    }
    // search by
    private void HoursChosen(String serviceName){
        //instead of branchByServiceType have another class called BranchByHours
       // Intent intent = new Intent(this, BranchesByServiceType.class);
        //intent.putExtra("serviceName", serviceName);
        //startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Find Monday first
        databaseHours.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hours.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    if((postSnapshot.child("accountType").getValue()).equals("employee")) {

                        //   String Monday = postSnapshot.child("Monday").getValue().toString();
                        //  hours.add(Monday);
                    }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    */

}

