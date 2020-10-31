package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import android.util.Log;

public class CurrentService extends AppCompatActivity {

    ListView listViewServices;
    DatabaseReference databaseServices;

    List<String> services;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_service);
        db = FirebaseDatabase.getInstance();
        databaseServices = db.getReference("services");
        listViewServices = (ListView) findViewById(R.id.listViewServices);

        services = new ArrayList<String>();

    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseServices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                services.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String service = postSnapshot.child("name").getValue().toString();
                    String tmp = service.substring(0, 1).toUpperCase() + service.substring(1);
                    services.add(tmp);
                }
                Collections.sort(services);

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CurrentService.this, R.layout.layout_services_list, R.id.textViewName, services);
                listViewServices.setAdapter(arrayAdapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void CreateNewServices(View view) {
        Intent intent = new Intent(this, CreateNewServices.class);
        //add in the manage services view
        startActivity(intent);
    }
}