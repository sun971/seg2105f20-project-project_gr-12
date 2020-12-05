package com.example.servicenovigrad;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HelperClass {
    private EmployeeAccount employeeAccount;
    private AdminAccount adminAccount;
    private CustomerAccount customerAccount;
    private FirebaseDatabase mDatabase;

    public HelperClass() {
        employeeAccount = null;
        adminAccount = null;
        customerAccount = null;
        mDatabase = FirebaseDatabase.getInstance();

    }

    public EmployeeAccount getEmployeeAccount(String uid) {

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                employeeAccount = snapshot.getValue(EmployeeAccount.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return employeeAccount;

    }

    public AdminAccount getAdminAccount(String uid) {

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adminAccount = snapshot.getValue(AdminAccount.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return adminAccount;

    }

    public CustomerAccount getCustomerAccount(String uid) {

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                customerAccount = snapshot.getValue(CustomerAccount.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return customerAccount;

    }
/*
    public void addServiceRequestsToAccount(String uid) {



        DatabaseReference userData = mDatabase.getReference("users/"+uid);

        userData.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<ServiceRequest> serviceRequests = new ArrayList<ServiceRequest>();
                        serviceRequests.add(new ServiceRequest("Marshall", "Steele", "123 Guac Lane", "12/22/1997", LicenseType.G2, "hello.jpg", "res.png", "stat.img"));
                        EmployeeAccount thisAccount = snapshot.getValue(EmployeeAccount.class);
                        //thisAccount.addServiceRequest(new ServiceRequest("Marshall", "Steele", "123 Guac Lane", "12/22/1997", LicenseType.G2, "hello.jpg", "res.png", "stat.img"));
                        //OVERWRITE THIS ENTRIES SERVICE LIST
                        mDatabase.getReference("users/"+thisAccount.getUid()).setValue(thisAccount);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }

        );

    }


 */

}
