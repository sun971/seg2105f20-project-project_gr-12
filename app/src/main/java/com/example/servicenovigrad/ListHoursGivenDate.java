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
import android.util.Log;

public class ListHoursGivenDate extends AppCompatActivity {

// first attempt by search with just Monday
    ListView ListOfHours;
    //ListOfHours
    DatabaseReference databaseHours;

    List<String> hours;
    FirebaseDatabase db;
    String serviceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_hours_given_date);

        Bundle bundle = getIntent().getExtras();
        serviceName = bundle.getString("Day");

        db = FirebaseDatabase.getInstance();
        //get the user
        databaseHours = db.getReference("users");
        ListOfHours = (ListView) findViewById(R.id.ListOfHours);


        hours = new ArrayList<String>();
        ListOfHours.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String hoursavailable = hours.get(i);
                HoursChosen(hoursavailable);
            }
        });

    }
    // search by
    private void HoursChosen(String hourChosen){
        //instead of branchByServiceType have another class called BranchByHours
        Intent intent = new Intent(this, BranchesByHoursType.class);
        intent.putExtra("hourChosen", hourChosen); //make it data screen to get every single branch
        intent.putExtra("Day",serviceName);//send the date
        startActivity(intent);
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

                 //if ((postSnapshot.child("accountType").getValue()).equals("employee")) {
                    if(postSnapshot.child("accountType").getValue().toString().equals("employee")){
                        String Monday = postSnapshot.child(serviceName).getValue().toString();
                        //here you wanna check that its not duplicated
                        if(!(hours.contains(Monday))) {
                            hours.add(Monday);
                        }
                    }
                }
                Collections.sort(hours);
                //change array adapter to the layout services list stuff
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ListHoursGivenDate.this, R.layout.layout_services_list, R.id.textViewName, hours);
                ListOfHours.setAdapter(arrayAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}


