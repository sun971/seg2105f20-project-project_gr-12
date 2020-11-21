package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SetEmployeeInfo extends AppCompatActivity {
    private FirebaseAuth session;
    private FirebaseDatabase mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_employee_info);

        session = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

    }

    public void onClick(View v) {
        EditText addressField = (EditText)findViewById(R.id.postalAddressId);
        EditText phoneField = (EditText)findViewById(R.id.phoneId);

        final String address = addressField.getText().toString();
        final String phone = phoneField.getText().toString();


        if (address.equals("") || phone.equals("")) {
            setContentView(R.layout.activity_set_employee_info);
            Toast.makeText(getApplicationContext(),"All fields require a value",Toast.LENGTH_LONG).show();

            //validate the first and last name contain only letters
        } else if (!address.matches("[a-zA-Z0-9]+") || !phone.matches("[0-9]+")) {
            Toast.makeText(getApplicationContext(), "First name and Last name fields must only contain letters", Toast.LENGTH_LONG).show();

        }else {
            //String user = mAuth.getCurrentUser().getEmail();
            //String id = mAuth.getCurrentUser().getUid();

            //If account successfully created
            //DatabaseReference userReference = mDatabase.getReference("users/"+id);

            //userReference.setValue(new EmployeeAccount(id, firstName, lastName, email, password));

            FirebaseUser user = session.getCurrentUser();
            if (user != null) {
                final String id = user.getUid();
                DatabaseReference userData = mDatabase.getReference("users/"+id);

                userData.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                EmployeeAccount emp = new EmployeeAccount(id, snapshot.child("firstName").getValue().toString(), snapshot.child("lastName").getValue().toString(), snapshot.child("eMail").getValue().toString(), snapshot.child("password").getValue().toString(), address, phone);
                                emp.setAddress(address);
                                emp.setPhone(phone);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        }

                );


            }


            Intent nextIntent = new Intent(SetEmployeeInfo.this, SetEmployeeInfo.class); //whatever the set working class is called

            startActivity(nextIntent);


        }
    }

    public void inputFormat(View v) {
        Toast.makeText(getApplicationContext(), "address: 11 Somewhere; phone: 5555555555", Toast.LENGTH_LONG).show();
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

                            TextView empWelcome = (TextView) findViewById(R.id.welcomeEmployee);
                            empWelcome.setText("Welcome "+snapshot.child("firstName").getValue()+" "+snapshot.child("lastName").getValue());
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