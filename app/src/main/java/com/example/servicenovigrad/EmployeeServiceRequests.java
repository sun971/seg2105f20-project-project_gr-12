package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmployeeServiceRequests extends AppCompatActivity {

    List<String> requests; //stores all the request names
    ListView listViewRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_service_requests);

        listViewRequests = (ListView) findViewById(R.id.listViewRequests);
        requests = new ArrayList<String>();

        //Pop up dialog appears when a service is long pressed
        listViewRequests.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String request = requests.get(i);
                sendToViewRequest(request);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth fbAuthRef = FirebaseAuth.getInstance();
        FirebaseUser currentUser = fbAuthRef.getCurrentUser();
        DatabaseReference dbRootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference dbRequestsRef = dbRootRef.child("users").child(currentUser.getUid()).child("activeRequests");

        //Finds all the current services in the database and displays them in list view
        if (dbRequestsRef != null) {
            dbRequestsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    requests.clear();

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        String request = postSnapshot.child("firstName").getValue().toString();
                        requests.add(request);
                    }
                    Collections.sort(requests);

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(EmployeeServiceRequests.this, R.layout.layout_services_list, R.id.textViewName, requests);
                    listViewRequests.setAdapter(arrayAdapter);

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    protected void onClickBack(View v) {
        Intent intent = new Intent(this, EmployeeWelcome.class);
        startActivity(intent);
    }

    private void sendToViewRequest(String request) {
        Intent intent = new Intent(this, ViewRequest.class);
        intent.putExtra("Request ID", request);
        startActivity(intent);
    }
}