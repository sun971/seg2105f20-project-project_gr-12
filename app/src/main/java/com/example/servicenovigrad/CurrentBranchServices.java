package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CurrentBranchServices extends AppCompatActivity {
    ListView listViewBranchServices;
    DatabaseReference databaseServices;
    List<String> servicesList;


    private FirebaseAuth session;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_branch_services);

        db = FirebaseDatabase.getInstance();


        listViewBranchServices = (ListView) findViewById(R.id.listViewBranchServices);

        session = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        servicesList = new ArrayList<String>();

        //Pop up dialog appears when a service is long pressed
        listViewBranchServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String service = servicesList.get(i);
                showRemoveDialog(service);
                return true;
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = session.getCurrentUser();
        if (user != null) {
            String id = user.getUid();
            DatabaseReference userData = db.getReference("users/" + id);
            userData.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};

                    servicesList = snapshot.child("branchServices").getValue(t);

                    if (servicesList != null) {
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CurrentBranchServices.this, R.layout.layout_services_list, R.id.textViewName, servicesList);
                        listViewBranchServices.setAdapter(arrayAdapter);
                    }else{
                        servicesList = new ArrayList<>();
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CurrentBranchServices.this, R.layout.layout_services_list, R.id.textViewName, servicesList);
                        listViewBranchServices.setAdapter(arrayAdapter);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        } else {
        }
    }

    public void AddService(View view) {
        Intent intent = new Intent(this, AddBranchService.class);
        startActivity(intent);
    }

    public void WelcomePage(View view) {
        Intent intent = new Intent(this, EmployeeWelcome.class);
        startActivity(intent);
    }

    private void showRemoveDialog(final String serviceName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.layout_remove_branch_service, null);
        dialogBuilder.setView(dialogView);

        final Button buttonCancel = (Button) dialogView.findViewById(R.id.buttonCancel);
        final Button buttonRemove = (Button) dialogView.findViewById(R.id.buttonRemove);

        dialogBuilder.setTitle(serviceName);
        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();


        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeService(serviceName);
                dialog.dismiss();
            }
        });
    }

    //deletes the service
    private void removeService(String name) {
        servicesList.remove(name);
        FirebaseUser user = session.getCurrentUser();
        String id = user.getUid();
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("users/" + id).child("branchServices");
        dR.setValue(servicesList);
        Toast.makeText(getApplicationContext(), "Service Removed", Toast.LENGTH_SHORT).show();
    }
}
