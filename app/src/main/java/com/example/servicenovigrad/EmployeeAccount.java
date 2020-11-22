package com.example.servicenovigrad;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EmployeeAccount extends Account {
    private String address;
    private String phone;

    private List<ServiceRequest> serviceRequests;
    private List<String> branchServices;

    EmployeeAccount(String uid, String firstName, String lastName, String eMail, String password) {
        super(uid, firstName, lastName, eMail, password);
        address = null;
        phone = null;

        serviceRequests = new ArrayList<ServiceRequest>();
        branchServices = new ArrayList<String>();


        serviceRequests.add(new ServiceRequest("Marshall", "Steele", "myAddress", "12/22/1997", LicenseType.G2, "asd", "asd", "asd"));
        serviceRequests.add(new ServiceRequest("Bon", "Jovi", "myAddress2", "3/2/1962", LicenseType.G2, "asd", "asd", "asd"));
    }
    EmployeeAccount(String uid, String firstName, String lastName, String eMail, String password, String address, String phone) {
        super(uid, firstName, lastName, eMail, password);
        this.address= address;
        this.phone = phone;
    }

    public String getAccountType() {
        return "employee";
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public List<ServiceRequest> getServiceRequests(){
        return serviceRequests;
    }

    public void removeServiceRequest(ServiceRequest request) {serviceRequests.remove(request); }

    public List<String> getBranchServices(){
        return branchServices;
    }

}