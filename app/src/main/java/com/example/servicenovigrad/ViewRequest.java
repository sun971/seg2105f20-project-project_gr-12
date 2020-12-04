package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewRequest extends AppCompatActivity {
    TextView firstNameText;
    TextView lastNameText;
    TextView dobText;
    TextView addressText;
    TextView licenseTypeText;
    ImageView proofOfResidencePhoto;
    ImageView customerPhoto;
    ImageView proofOfStatusPhoto;
    Button acceptButton;
    Button rejectButton;
    TextView customerPhotoTitle;
    TextView proofOfStatusTitle;
    TextView proofOfResidenceTitle;

    DatabaseReference dbUserRef;
    DatabaseReference dbRootRef;
    FirebaseUser currentUser;

    String requestId;
    ServiceRequest currentRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);

        //Get GUI Elements
        firstNameText = (TextView)findViewById(R.id.firstNameTitle);
        lastNameText = (TextView)findViewById(R.id.lastNameTitle);
        dobText = (TextView)findViewById(R.id.dobTitle);
        addressText = (TextView)findViewById(R.id.addressTitle);
        licenseTypeText = (TextView)findViewById(R.id.licenseTypeTitle);
        proofOfResidencePhoto = (ImageView) findViewById(R.id.proofOfResidenceImage);
        proofOfResidenceTitle = (TextView) findViewById(R.id.proofOfResidenceTitle);
        customerPhoto = (ImageView) findViewById(R.id.customerPhotoImage);
        customerPhotoTitle = (TextView) findViewById(R.id.customerPhotoTitle);
        proofOfStatusPhoto = (ImageView) findViewById(R.id.proofOfStatusImage);
        proofOfStatusTitle = (TextView) findViewById(R.id.proofOfStatusTitle);
        acceptButton = (Button) findViewById(R.id.acceptRequestButton);
        rejectButton = (Button) findViewById(R.id.rejectRequestButton);




    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth fbAuthRef = FirebaseAuth.getInstance();
        currentUser = fbAuthRef.getCurrentUser();
        dbRootRef = FirebaseDatabase.getInstance().getReference();
        dbUserRef = dbRootRef.child("users").child(currentUser.getUid());

        requestId = getIntent().getExtras().getString("Request ID");

        Log.d("SERVICE REQUEST ID", requestId);
        dbUserRef.child("serviceRequests").child(requestId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // Store values
                String firstName = String.valueOf(dataSnapshot.child("firstName").getValue());
                String lastName = String.valueOf(dataSnapshot.child("lastName").getValue());
                String address = String.valueOf(dataSnapshot.child("address").getValue());
                String dob = String.valueOf(dataSnapshot.child("dob").getValue());

                LicenseType licenseType;
                String licenseReader = String.valueOf(dataSnapshot.child("licenseType").getValue());
                if(licenseReader.equals("G1"))   {
                    licenseType = LicenseType.G1;
                }
                else if(licenseReader.equals("G2")) {
                    licenseType = LicenseType.G2;
                }
                else    {
                    licenseType = LicenseType.G;
                }



                String customerPhotoUid = String.valueOf(dataSnapshot.child("customerPhotoUid").getValue());
                String proofOfResidenceUid = String.valueOf(dataSnapshot.child("proofOfResidenceUid").getValue());
                String proofOfStatusUid = String.valueOf(dataSnapshot.child("proofOfStatusUid").getValue());

                ServiceRequest request = new ServiceRequest(firstName, lastName, address, dob, licenseType, customerPhotoUid, proofOfResidenceUid, proofOfStatusUid);
                setUIElements(request);
                setCurrentRequest(request);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error Loading Request", Toast.LENGTH_LONG).show();
                goBack();
            }
        });

    }

    private void setCurrentRequest(ServiceRequest request) {
        currentRequest = request;
    }

    private void setUIElements(ServiceRequest request) {

        String firstName;
        String lastName;
        String address;
        String dob;
        LicenseType licenseType;
        String customerPhotoUid;
        String proofOfResidenceUid;
        String proofOfStatusUid;

        firstName = request.getFirstName();
        lastName = request.getLastName();
        address = request.getAddress();
        dob = request.getDob();
        licenseType = request.getLicenseType();
        customerPhotoUid = request.getCustomerPhotoUid();
        proofOfResidenceUid = request.getProofOfResidenceUid();
        proofOfStatusUid = request.getProofOfStatusUid();

        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
        setDob(dob);
        setLicenseType(licenseType);
        setCustomerPhoto(customerPhotoUid);
        setProofOfResidence(proofOfResidenceUid);
        setProofOfStatus(proofOfStatusUid);

    }

    private void setFirstName(String firstName) {
        if (firstName == null) {
            firstNameText.setVisibility(View.GONE);
        } else {
            firstNameText.setText("First Name: " + firstName);
        }
    }

    private void setLastName(String lastName) {
        if (lastName == null) {
            lastNameText.setVisibility(View.GONE);
        } else {
            lastNameText.setText("Last Name: " + lastName);
        }
    }
    private void setAddress(String address) {
        if (address == null) {
            addressText.setVisibility(View.GONE);
        } else {
            addressText.setText("Address: " + address);
        }
    }
    private void setDob(String dob) {
        if (dob == null) {
            dobText.setVisibility(View.GONE);
        } else {
            dobText.setText("DOB: " + dob);
        }
    }
    private void setLicenseType(LicenseType licenseType) {
        if (licenseType == null) {
            licenseTypeText.setVisibility(View.GONE);
        } else {
            switch (licenseType) {
                case G1:
                    licenseTypeText.setText("License Type: G1");
                case G2:
                    licenseTypeText.setText("License Type: G2");
                case G:
                    licenseTypeText.setText("License Type: G");
            }

        }
    }
    private void setCustomerPhoto(String customerPhotoUid) {
        if (customerPhotoUid == null) {
            customerPhotoTitle.setVisibility(View.GONE);
            customerPhoto.setVisibility(View.GONE);
        } else {
            //set photo
        }
    }
    private void setProofOfResidence(String proofOfResidenceUid) {
        if (proofOfResidenceUid == null) {
            proofOfResidenceTitle.setVisibility(View.GONE);
            proofOfResidencePhoto.setVisibility(View.GONE);
        } else {
            //set photo
        }
    }
    private void setProofOfStatus(String proofOfStatusUid) {
        if (proofOfStatusUid == null) {
            proofOfStatusTitle.setVisibility(View.GONE);
            proofOfStatusPhoto.setVisibility(View.GONE);
        } else {
            //set photo
        }
    }

    public void Accept(View v) {
        DatabaseReference dbRequestsRef = dbUserRef.child("serviceRequests");
        if (dbRequestsRef != null) {
            dbRequestsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<ServiceRequest> requests = new ArrayList<ServiceRequest>();

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        ServiceRequest request = postSnapshot.getValue(ServiceRequest.class);
                        if (!request.equals(currentRequest)) {
                            requests.add(request);
                        }
                    }

                    dbUserRef.child("serviceRequests").setValue(requests);
                    Toast.makeText(getApplicationContext(), "Request Accepted", Toast.LENGTH_LONG).show();
                    goBack();

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public void Reject(View v) {
        DatabaseReference dbRequestsRef = dbUserRef.child("serviceRequests");
        if (dbRequestsRef != null) {
            dbRequestsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<ServiceRequest> requests = new ArrayList<ServiceRequest>();

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        ServiceRequest request = postSnapshot.getValue(ServiceRequest.class);
                        if (!request.equals(currentRequest)) {
                            requests.add(request);
                        }
                    }

                    dbUserRef.child("serviceRequests").setValue(requests);
                    Toast.makeText(getApplicationContext(), "Request Rejected", Toast.LENGTH_LONG).show();
                    goBack();

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    private void goBack() {
        Intent intent = new Intent(ViewRequest.this, EmployeeServiceRequests.class);
        startActivity(intent);
    }
}