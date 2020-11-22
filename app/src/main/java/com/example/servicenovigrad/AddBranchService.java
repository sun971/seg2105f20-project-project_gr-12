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
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddBranchService extends AppCompatActivity {

    ListView listViewServices;
    DatabaseReference databaseServices;

    List<String> services; //stores all the service names
    List<String> branchList;
    FirebaseDatabase db;

    private FirebaseAuth session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_branch_service);

        db = FirebaseDatabase.getInstance();
        session = FirebaseAuth.getInstance();
        databaseServices = db.getReference("services");
        listViewServices = (ListView) findViewById(R.id.listServices);

        services = new ArrayList<String>();
        branchList = new ArrayList<String>();
        getBranchServices();

        listViewServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                String service = services.get(i);
                showAddDialog(service);
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

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddBranchService.this, R.layout.layout_services_list, R.id.textViewName, services);
                listViewServices.setAdapter(arrayAdapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getBranchServices(){
        FirebaseUser user = session.getCurrentUser();
        if (user != null) {
            String id = user.getUid();
            DatabaseReference userData = db.getReference("users/" + id);
            userData.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};

                    branchList = snapshot.child("branchServices").getValue(t);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        } else {

        }
    }

    private void showAddDialog(final String serviceName) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.layout_add_branch_service, null);
        dialogBuilder.setView(dialogView);

        final Button buttonCancel = (Button) dialogView.findViewById(R.id.buttonCancel2);
        final Button buttonAdd = (Button) dialogView.findViewById(R.id.buttonAdd);

        dialogBuilder.setTitle(serviceName);
        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();


        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(branchList != null) {
                    if (!branchList.contains(serviceName)) {
                        addService(serviceName);
                    } else {
                        Toast.makeText(getApplicationContext(), "ERROR: Service Already In Branch", Toast.LENGTH_LONG).show();
                    }
                }else{
                    addService(serviceName);
                }
                dialog.dismiss();
            }
        });
    }

    private void addService(String name) {

        if(branchList == null) {
            branchList = new ArrayList<String>();
        }
        branchList.add(name);

        FirebaseUser user = session.getCurrentUser();
        String id = user.getUid();
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("users/" + id + "/branchServices");
        dR.setValue(branchList);

        Toast.makeText(getApplicationContext(), "Service Added Successfully", Toast.LENGTH_SHORT).show();
    }

    public void done(View view) {
        Intent intent = new Intent(this, CurrentBranchServices.class);
        startActivity(intent);
    }
}