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

    private List<ServiceRequest> activeRequests;
    private List<String> branchServices;

    EmployeeAccount(String uid, String firstName, String lastName, String eMail, String password) {
        super(uid, firstName, lastName, eMail, password);
        double servicePriceField = 12.65;

        HashMap<String, Boolean> fieldsEnableTemp = new HashMap<String, Boolean>();
        HashMap<String, Boolean> formsEnableTemp = new HashMap<String, Boolean>();

        fieldsEnableTemp.put("firstNameFieldEnable", true);
        fieldsEnableTemp.put("lastNameFieldEnable", true);
        fieldsEnableTemp.put("addressFieldEnable", true);
        fieldsEnableTemp.put("dobFieldEnable", false);
        fieldsEnableTemp.put("licenseFormEnable", false);

        formsEnableTemp.put("statusFormEnable", false);
        formsEnableTemp.put("photoIDFormEnable", true);
        formsEnableTemp.put("residentFormEnable", true);

        HashMap<String, Boolean> fieldsEnable = fieldsEnableTemp;
        HashMap<String, Boolean> formsEnable = formsEnableTemp;

        activeRequests = new ArrayList<ServiceRequest>();
        branchServices = new ArrayList<String>();

        branchServices.add("asdfg");
        branchServices.add("asdfg");


        activeRequests.add(new ServiceRequest("Marshall", "Steele", "myAddress", "12/22/1997", LicenseType.G2, "asd", "asd", "asd"));
        activeRequests.add(new ServiceRequest("Marshall2", "Steele2", "myAddress2", "12/22/1997", LicenseType.G2, "asd", "asd", "asd"));
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

    public Boolean hasAddress() {
        FirebaseAuth session = FirebaseAuth.getInstance();
        FirebaseUser user = session.getCurrentUser();
        String id = user.getUid();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference addRef = rootRef.child("users").child(id).child("address");
        final Boolean[] exists = {false};
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()) {
                    exists[0] = true;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Log.d(TAG, databaseError.getMessage()); //Don't ignore errors!
            }
        };
        addRef.addListenerForSingleValueEvent(eventListener);

        //if (address !=null){
        //    return true;
        //}
        return exists[0];
    }

    public Boolean hasPhone() {
        if (phone !=null){
            return true;
        }
        return false;
    }

    public List<ServiceRequest> getServiceRequests(){
        return activeRequests;
    }

    public List<String> getBranchServices(){
        return branchServices;
    }

}