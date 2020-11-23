package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditEmployeeInfo extends AppCompatActivity {

    private FirebaseAuth session;
    private FirebaseDatabase mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee_info);

        session = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        getCurrentUser();
    }

    public void onClick(View v) {

        EditText addressField = (EditText)findViewById(R.id.postalAddressId2);
        EditText phoneField = (EditText)findViewById(R.id.phoneId2);

        final String address = addressField.getText().toString();
        final String phone = phoneField.getText().toString();


        if (address.equals("") || phone.equals("")) {
            setContentView(R.layout.activity_edit_employee_info);
            Toast.makeText(getApplicationContext(),"All fields require a value",Toast.LENGTH_LONG).show();

        } else if (!address.matches("[a-zA-Z0-9 ]+") || !phone.matches("[0-9]+")) {
            Toast.makeText(getApplicationContext(), "Phone number must only contain numbers and Address can't contain any symbols.", Toast.LENGTH_LONG).show();

        }else {

            FirebaseUser user = session.getCurrentUser();
            if (user != null) {
                final String id = user.getUid();
                DatabaseReference userData = mDatabase.getReference("users/"+id);

                userData.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                DatabaseReference addPhone = mDatabase.getReference("users/"+id);
                                EmployeeAccount emp = new EmployeeAccount(id, snapshot.child("firstName").getValue().toString(), snapshot.child("lastName").getValue().toString(), snapshot.child("eMail").getValue().toString(), snapshot.child("password").getValue().toString(), address, phone);
                                addPhone.setValue(emp);
                                emp.setAddress(address);
                                emp.setPhone(phone);
                                //dbUserReference.child("address").setValue(address);
                                Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(EditEmployeeInfo.this, EmployeeWelcome.class);
                                startActivity(intent);


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        }

                );


            }


            Intent nextIntent = new Intent(EditEmployeeInfo.this, Login.class);

            startActivity(nextIntent);


        }
    }

    public void getCurrentUser(){
        FirebaseUser user = session.getCurrentUser();
        if (user != null) {
            String id = user.getUid();
            DatabaseReference userData = mDatabase.getReference("users/"+id);

            userData.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {


                            TextView address = (TextView) findViewById(R.id.postalAddressId2);
                            address.setText(snapshot.child("address").getValue().toString());

                            TextView phone = (TextView) findViewById(R.id.phoneId2);
                            phone.setText(snapshot.child("phone").getValue().toString());

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    }

            );


        } else {

        }
    }
}