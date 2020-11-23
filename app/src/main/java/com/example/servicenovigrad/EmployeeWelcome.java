package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployeeWelcome extends AppCompatActivity {

    private FirebaseAuth session;
    private FirebaseDatabase mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_welcome);

        session = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        getCurrentUser();


    }

    private void getCurrentUser() {
            FirebaseUser user = session.getCurrentUser();
            if (user != null) {
                String id = user.getUid();
                DatabaseReference userData = mDatabase.getReference("users/" + id);

                userData.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                TextView helloTextView = (TextView) findViewById(R.id.welcomeMessage);
                                helloTextView.setText("Welcome "+snapshot.child("firstName").getValue()+"! you are logged in as "+snapshot.child("accountType").getValue());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        }

                );


            } else {

            }
    }

    public void SignOut(View view) {
        if (view.getId() == R.id.btnSignOut) {
            session.signOut();
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);

        }
    }

    public void EditAddress(View view) {
        Intent intent = new Intent(this, EditEmployeeInfo.class);
        startActivity(intent);
    }

    public void EditHours(View view) {
    }

    public void deleteAccounts(View view) {
    }

    public void ChangeServices(View view) {
        Intent intent = new Intent(this, CurrentBranchServices.class);
        startActivity(intent);
    }

    public void onClickManageServiceRequest(View view) {
        Intent intent = new Intent(this, EmployeeServiceRequests.class);
        startActivity(intent);
    }
}