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

public class SearchByService extends AppCompatActivity {

    ListView listOfServices;
    DatabaseReference databaseServices;

    List<String> services;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_service);

        db = FirebaseDatabase.getInstance();

        databaseServices = db.getReference("services");
        listOfServices = (ListView) findViewById(R.id.ListOfServices);

        services = new ArrayList<String>();

        listOfServices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String service = services.get(i);
                ServiceChosen(service);
            }
        });
    }

    private void ServiceChosen(String serviceName){
        Intent intent = new Intent(this, BranchesByServiceType.class);
        intent.putExtra("serviceName", serviceName);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Finds all the current services in the database and displays them in list view
        databaseServices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                services.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String service = postSnapshot.child("name").getValue().toString();
                    services.add(service);
                }
                Collections.sort(services);

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SearchByService.this, R.layout.layout_services_list, R.id.textViewName, services);
                listOfServices.setAdapter(arrayAdapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}