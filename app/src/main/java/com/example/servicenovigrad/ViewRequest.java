package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewRequest extends AppCompatActivity {
    TextView titleText;
    TextView firstNameText;
    TextView lastNameText;
    TextView dobText;
    TextView addressText;
    TextView licenseTypeText;
    Button proofOfResidencePhoto;
    Button customerPhoto;
    Button proofOfStatusPhoto;
    Button acceptButton;
    Button rejectButton;
    TextView customerPhotoTitle;
    TextView proofOfStatusTitle;
    TextView proofOfResidenceTitle;

    Uri residenceUri;
    Uri statusUri;
    Uri photoUri;

    DatabaseReference dbUserRef;
    DatabaseReference dbRootRef;
    FirebaseUser currentUser;
    StorageReference storageRef;

    String requestId;
    ServiceRequest currentRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);

        int REQUEST_CODE=1;

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, REQUEST_CODE);

        //Get GUI Elements
        titleText = (TextView)findViewById(R.id.title);
        firstNameText = (TextView)findViewById(R.id.firstNameTitle);
        lastNameText = (TextView)findViewById(R.id.lastNameTitle);
        dobText = (TextView)findViewById(R.id.dobTitle);
        addressText = (TextView)findViewById(R.id.addressTitle);
        licenseTypeText = (TextView)findViewById(R.id.licenseTypeTitle);
        proofOfResidencePhoto = (Button) findViewById(R.id.proofOfResidenceButton);
        proofOfResidenceTitle = (TextView) findViewById(R.id.proofOfResidenceTitle);
        customerPhoto = (Button) findViewById(R.id.customerPhotoButton);
        customerPhotoTitle = (TextView) findViewById(R.id.customerPhotoTitle);
        proofOfStatusPhoto = (Button) findViewById(R.id.proofOfStatusButton);
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
        storageRef = FirebaseStorage.getInstance().getReference();

        requestId = getIntent().getExtras().getString("Request ID");

        Log.d("SERVICE REQUEST ID", requestId);
        dbUserRef.child("serviceRequests").child(requestId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // Store values
                String serviceName = String.valueOf(dataSnapshot.child("serviceName").getValue());
                String customerUid = String.valueOf(dataSnapshot.child("customerUid").getValue());
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
                else  if(licenseReader.equals("G")){
                    licenseType = LicenseType.G;
                } else {
                    licenseType = null;
                }



                String customerPhotoUid = String.valueOf(dataSnapshot.child("customerPhotoUid").getValue());
                String proofOfResidenceUid = String.valueOf(dataSnapshot.child("proofOfResidenceUid").getValue());
                String proofOfStatusUid = String.valueOf(dataSnapshot.child("proofOfStatusUid").getValue());

                ServiceRequest request = new ServiceRequest(serviceName, customerUid, firstName, lastName, address, dob, licenseType, customerPhotoUid, proofOfResidenceUid, proofOfStatusUid);
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

        String serviceName;
        String customerUid;
        String firstName;
        String lastName;
        String address;
        String dob;
        LicenseType licenseType;
        String customerPhotoUid;
        String proofOfResidenceUid;
        String proofOfStatusUid;

        serviceName = request.getServiceName();
        titleText.setText(serviceName);

        customerUid = request.getCustomerUid();
        firstName = request.getFirstName();
        lastName = request.getLastName();
        address = request.getAddress();
        dob = request.getDob();
        licenseType = request.getLicenseType();
        customerPhotoUid = request.getCustomerPhotoUid();
        proofOfResidenceUid = request.getProofOfResidenceUid();
        proofOfStatusUid = request.getProofOfStatusUid();

        Log.d("test", "PhotoUid: " + customerPhotoUid);
        Log.d("test", "proofOfResidenceUid: " + proofOfResidenceUid);
        Log.d("test", "proofOfStatusUid: " + proofOfStatusUid);

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
        } else if (firstName.equals("")) {
            firstNameText.setVisibility(View.GONE);
        }  else if (firstName.equals("null")) {
            firstNameText.setVisibility(View.GONE);
        } else {
            firstNameText.setVisibility(View.VISIBLE);
            firstNameText.setText("First Name: " + firstName);
        }
    }

    private void setLastName(String lastName) {
        if (lastName == null) {
            lastNameText.setVisibility(View.GONE);
        } else if (lastName.equals(""))
        {
            lastNameText.setVisibility(View.GONE);
        } else if (lastName.equals("null"))
        {
            lastNameText.setVisibility(View.GONE);
        } else {
            lastNameText.setVisibility(View.VISIBLE);
            lastNameText.setText("Last Name: " + lastName);
        }
    }
    private void setAddress(String address) {
        if (address == null) {
            addressText.setVisibility(View.GONE);
        } else if (address.equals(""))
        {
            addressText.setVisibility(View.GONE);
        } else if (address.equals("null"))
        {
            addressText.setVisibility(View.GONE);
        } else {
            addressText.setVisibility(View.VISIBLE);
            addressText.setText("Address: " + address);
        }
    }
    private void setDob(String dob) {
        if (dob == null) {
            dobText.setVisibility(View.GONE);
        } else if (dob.equals("")){
            dobText.setVisibility(View.GONE);
        } else if (dob.equals("null")){
            dobText.setVisibility(View.GONE);
        } else {
            dobText.setVisibility(View.VISIBLE);
            dobText.setText("DOB: " + dob);
        }
    }
    private void setLicenseType(LicenseType licenseType) {
        if (licenseType == null) {
            licenseTypeText.setVisibility(View.GONE);
        } else {
            licenseTypeText.setVisibility(View.VISIBLE);

            if(licenseType == licenseType.G1){
                licenseTypeText.setText("License Type: G1");
            }else if (licenseType == licenseType.G2){
                licenseTypeText.setText("License Type: G2");
            }
            else{
                licenseTypeText.setText("License Type: G");
            }

        }
    }
    private void setCustomerPhoto(String customerPhotoUid) {
        if (customerPhotoUid == null) {
            customerPhotoTitle.setVisibility(View.GONE);
            customerPhoto.setVisibility(View.GONE);
        } else if (customerPhotoUid.equals("")){
            customerPhotoTitle.setVisibility(View.GONE);
            customerPhoto.setVisibility(View.GONE);
        } else if (customerPhotoUid.equals("null")){
            customerPhotoTitle.setVisibility(View.GONE);
            customerPhoto.setVisibility(View.GONE);
        } else {
            customerPhotoTitle.setVisibility(View.VISIBLE);
            customerPhoto.setVisibility(View.VISIBLE);

            StorageReference photoRef = storageRef.child(customerPhotoUid);
            Task<Uri> getDownloadUrl = photoRef.getDownloadUrl();
            getDownloadUrl.addOnCompleteListener(ViewRequest.this, new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    photoUri = task.getResult();
                }
            });
        }
    }
    private void setProofOfResidence(String proofOfResidenceUid) {
        if (proofOfResidenceUid == null) {
            proofOfResidenceTitle.setVisibility(View.GONE);
            proofOfResidencePhoto.setVisibility(View.GONE);
        } else if (proofOfResidenceUid.equals("")){
            proofOfResidenceTitle.setVisibility(View.GONE);
            proofOfResidencePhoto.setVisibility(View.GONE);
        } else if (proofOfResidenceUid.equals("null")){
            proofOfResidenceTitle.setVisibility(View.GONE);
            proofOfResidencePhoto.setVisibility(View.GONE);
        } else {
            proofOfResidenceTitle.setVisibility(View.VISIBLE);
            proofOfResidencePhoto.setVisibility(View.VISIBLE);

            StorageReference residenceRef = storageRef.child(proofOfResidenceUid);
            Task<Uri> getDownloadUrl = residenceRef.getDownloadUrl();
            getDownloadUrl.addOnCompleteListener(ViewRequest.this, new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    residenceUri = task.getResult();
                }
            });

        }
    }
    private void setProofOfStatus(String proofOfStatusUid) {
        if (proofOfStatusUid == null) {
            proofOfStatusTitle.setVisibility(View.GONE);
            proofOfStatusPhoto.setVisibility(View.GONE);
        } else if (proofOfStatusUid.equals("")) {
            proofOfStatusTitle.setVisibility(View.GONE);
            proofOfStatusPhoto.setVisibility(View.GONE);
        } else if (proofOfStatusUid.equals("null")) {
            proofOfStatusTitle.setVisibility(View.GONE);
            proofOfStatusPhoto.setVisibility(View.GONE);
        } else {
            proofOfStatusTitle.setVisibility(View.VISIBLE);
            proofOfStatusPhoto.setVisibility(View.VISIBLE);

            StorageReference statusRef = storageRef.child(proofOfStatusUid);
            Task<Uri> getDownloadUrl = statusRef.getDownloadUrl();
            getDownloadUrl.addOnCompleteListener(ViewRequest.this, new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    statusUri = task.getResult();
                }
            });
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

    public void DownloadResidence(View v) {
        if (residenceUri != null) {
            DownloadManager.Request request = new DownloadManager.Request(residenceUri);
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "proofOfResidence");
            DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);
        } else {
            Toast.makeText(getApplicationContext(), "Error Retrieving URL", Toast.LENGTH_LONG).show();
        }
    }

    public void DownloadPhoto(View v) {
        if (photoUri != null) {
            DownloadManager.Request request = new DownloadManager.Request(photoUri);
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "customerPhoto");
            DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);
        } else {
            Toast.makeText(getApplicationContext(), "Error Retrieving URL", Toast.LENGTH_LONG).show();
        }
    }
    public void DownloadStatus(View v) {
        if (statusUri != null) {
            DownloadManager.Request request = new DownloadManager.Request(statusUri);
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "proofOfStatus");
            DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);
        } else {
            Toast.makeText(getApplicationContext(), "Error Retrieving URL", Toast.LENGTH_LONG).show();
        }
    }


    private void goBack() {
        Intent intent = new Intent(ViewRequest.this, EmployeeServiceRequests.class);
        startActivity(intent);
    }
}