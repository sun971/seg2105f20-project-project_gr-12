package com.example.servicenovigrad;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ServiceRequest {

    private String firstName;
    private String lastName;
    private String address;
    private String dob;
    private LicenseType licenseType;
    private String customerPhotoUid;
    private String proofOfResidenceUid;
    private String proofOfStatusUid;

    ServiceRequest(String firstName, String lastName, String address, String dob, LicenseType licenseType, String customerPhotoUid, String proofOfResidenceUid, String proofOfStatusUid) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.dob = dob;
        this.licenseType = licenseType;
        this.customerPhotoUid = customerPhotoUid;
        this.proofOfResidenceUid = proofOfResidenceUid;
        this.proofOfStatusUid = proofOfStatusUid;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCustomerPhotoUid(String customerPhotoUid) {
        this.customerPhotoUid = customerPhotoUid;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setLicenseType(LicenseType licenseType) {
        this.licenseType = licenseType;
    }

    public void setProofOfResidenceUid(String proofOfResidenceUid) {
        this.proofOfResidenceUid = proofOfResidenceUid;
    }

    public void setProofOfStatusUid(String proofOfStatusUid) {
        this.proofOfStatusUid = proofOfStatusUid;
    }

    public LicenseType getLicenseType() {
        return licenseType;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getAddress() {
        return address;
    }

    public String getCustomerPhotoUid() {
        return customerPhotoUid;
    }

    public String getDob() {
        return dob;
    }

    public String getProofOfResidenceUid() {
        return proofOfResidenceUid;
    }

    public String getProofOfStatusUid() {
        return proofOfStatusUid;
    }

    public static void main(String[] args) {
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword("awda@dawda.com", "awdwdadw");

        FirebaseUser user = mAuth.getCurrentUser();
        String id = user.getUid();
        DatabaseReference userData = mDatabase.getReference("users/"+id);

        userData.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<ServiceRequest> serviceRequests = new ArrayList<ServiceRequest>();
                        serviceRequests.add(new ServiceRequest("Marshall", "Steele", "123 Guac Lane", "12/22/1997", LicenseType.G2, "hello.jpg", "res.png", "stat.img"));
                        EmployeeAccount thisAccount = snapshot.getValue(EmployeeAccount.class);
                        thisAccount.addServiceRequest(new ServiceRequest("Marshall", "Steele", "123 Guac Lane", "12/22/1997", LicenseType.G2, "hello.jpg", "res.png", "stat.img"));
                        //OVERWRITE THIS ENTRIES SERVICE LIST
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }

        );
    }
}
