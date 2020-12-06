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
import java.util.Iterator;
import java.util.List;

public class SearchByAddress extends AppCompatActivity {
    ListView listOfAddresses;
    DatabaseReference databaseAddresses;

    List<String> addresses;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_address);

        db = FirebaseDatabase.getInstance();

        databaseAddresses = db.getReference("users");
        listOfAddresses = (ListView) findViewById(R.id.ListOfAddresses);

        addresses = new ArrayList<String>();

        //huh
        listOfAddresses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String address = addresses.get(i);
                AddressChosen(address);
            }
        });
    }

    private void AddressChosen(String address){
        Intent intent = new Intent(this, BranchesByAddress.class); //get new intent
        intent.putExtra("address", address);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Finds all the current services in the database and displays them in list view
        databaseAddresses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                addresses.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if (postSnapshot.child("accountType").getValue().toString().equals("employee")) {
                        String address = postSnapshot.child("address").getValue().toString();
                        //check for duplicate
                        if (!(addresses.contains(address))) {
                            addresses.add(address);
                        }
                    }
                }
                Collections.sort(addresses);

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SearchByAddress.this, R.layout.layout_services_list, R.id.textViewName, addresses);
                listOfAddresses.setAdapter(arrayAdapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}