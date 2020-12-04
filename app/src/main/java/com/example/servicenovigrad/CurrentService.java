package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import android.util.Log;
import android.widget.Toast;

public class CurrentService extends AppCompatActivity {

    ListView listViewServices;
    DatabaseReference databaseServices;

    List<String> services; //stores all the service names
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_service);

        db = FirebaseDatabase.getInstance();

        databaseServices = db.getReference("services");
        listViewServices = (ListView) findViewById(R.id.listViewServices);

        services = new ArrayList<String>();

        //Pop up dialog appears when a service is long pressed
        listViewServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String service = services.get(i);
                showEditDeleteDialog(service);
                return true;
            }
        });
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

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CurrentService.this, R.layout.layout_services_list, R.id.textViewName, services);
                listViewServices.setAdapter(arrayAdapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //pop up for deleting and editing service
    private void showEditDeleteDialog(final String serviceName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.layout_edit_delete, null);
        dialogBuilder.setView(dialogView);

        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonEdit);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDelete);

        dialogBuilder.setTitle(serviceName);
        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        //deletes the specified service when delete button is pressed
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteService(serviceName);
                dialog.dismiss();
            }
        });
        //edit button
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurrentService.this, EditService.class);
                intent.putExtra("serviceName", serviceName);
                startActivity(intent);
            }
        });
    }

    //deletes the service from database
    private void deleteService(String name) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("services").child(name);
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Service Deleted", Toast.LENGTH_SHORT).show();
    }

    //sends user to the create services page
    public void CreateNewServices(View view) {
        Intent intent = new Intent(this, CreateNewServices.class);
        startActivity(intent);
    }

    public void backToAdmin(View view) {
        Intent intent = new Intent(this, AdminWelcome.class);
        startActivity(intent);
    }
}