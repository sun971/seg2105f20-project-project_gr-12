package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubmitRequest extends AppCompatActivity {

    int resultUploadPhoto = 1;
    Uri photoUri = null;
    int resultUploadResidence = 2;
    Uri residenceUri = null;
    int resultUploadStatus = 3;
    Uri statusUri = null;

    String serviceName;
    String branchID;
    ServiceRequest newRequest;

    Spinner licenseSpinner;
    TextView firstNameTitleView;
    TextView lastNameTitleView;
    TextView addressTitleView;
    TextView dobTitleView;
    TextView licenseTypeTitleView;
    TextView proofOfResidenceTitleView;
    TextView proofOfStatusTitleView;
    TextView customerPhotoTitleView;

    Button residenceButton;
    Button statusButton;
    Button photoButton;


    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText addressEditText;
    EditText dobEditText;

    private FirebaseStorage storageReference = FirebaseStorage.getInstance();
    private FirebaseAuth authReference = FirebaseAuth.getInstance();
    private DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_request);

        firstNameTitleView = (TextView) findViewById(R.id.firstNameTitle);
        lastNameTitleView = (TextView) findViewById(R.id.lastNameTitle);
        addressTitleView = (TextView) findViewById(R.id.addressTitle);
        dobTitleView = (TextView) findViewById(R.id.dobTitle);
        licenseTypeTitleView = (TextView) findViewById(R.id.licenseTypeTitle);
        proofOfResidenceTitleView = (TextView) findViewById(R.id.proofOfResidenceTitle);
        proofOfStatusTitleView = (TextView) findViewById(R.id.proofOfStatusTitle);
        customerPhotoTitleView = (TextView) findViewById(R.id.customerPhotoTitle);
        residenceButton = (Button) findViewById(R.id.uploadProofOfResidence);
        statusButton = (Button) findViewById(R.id.uploadProofOfStatus);
        photoButton = (Button) findViewById(R.id.uploadCustomerPhoto);
        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);
        addressEditText = (EditText) findViewById(R.id.addressEditText);
        dobEditText = (EditText) findViewById(R.id.dobEditText);


        //gets the service name passed from previous activity
        Bundle bundle = getIntent().getExtras();
        branchID = bundle.getString("branchID");
        serviceName = bundle.getString("serviceName");

        licenseSpinner = (Spinner) findViewById(R.id.licenseTypeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.licenseTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        licenseSpinner.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference serviceReference = dbReference.child("services").child(serviceName);
        serviceReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Boolean> infoMap = new HashMap((Map) dataSnapshot.child("requiredInfo").getValue());
                HashMap<String, Boolean> docsMap = new HashMap((Map) dataSnapshot.child("requiredDocs").getValue());

                Boolean firstNameRequired = infoMap.get("firstNameFieldEnable");
                Boolean lastNameRequired = infoMap.get("lastNameFieldEnable");
                Boolean addressRequired = infoMap.get("addressFieldEnable");
                Boolean licenseTypeRequired = infoMap.get("licenseFormEnable");
                Boolean dobFieldRequired = infoMap.get("dobFieldEnable");
                Boolean photoRequired = docsMap.get("photoIDFormEnable");
                Boolean residenceRequired = docsMap.get("residentFormEnable");
                Boolean statusRequired = docsMap.get("statusFormEnable");

                if (firstNameRequired != null) {
                    if (!firstNameRequired) {
                        firstNameTitleView.setVisibility(View.GONE);
                        firstNameEditText.setVisibility(View.GONE);
                    } else {
                        firstNameTitleView.setVisibility(View.VISIBLE);
                        firstNameEditText.setVisibility(View.VISIBLE);
                    }
                }
                if (lastNameRequired != null) {
                    if (!lastNameRequired) {
                        lastNameTitleView.setVisibility(View.GONE);
                        lastNameEditText.setVisibility(View.GONE);
                    }else {
                        lastNameTitleView.setVisibility(View.VISIBLE);
                        lastNameEditText.setVisibility(View.VISIBLE);
                    }
                }
                if (addressRequired != null) {
                    if (!addressRequired) {
                        addressTitleView.setVisibility(View.GONE);
                        addressEditText.setVisibility(View.GONE);
                    }else {
                        addressTitleView.setVisibility(View.VISIBLE);
                        addressEditText.setVisibility(View.VISIBLE);
                    }
                }
                if (dobFieldRequired != null) {
                    if (!dobFieldRequired) {
                        dobTitleView.setVisibility(View.GONE);
                        dobEditText.setVisibility(View.GONE);
                    }else {
                        dobTitleView.setVisibility(View.VISIBLE);
                        dobEditText.setVisibility(View.VISIBLE);
                    }
                }
                if (licenseTypeRequired != null) {
                    if (!licenseTypeRequired) {
                        licenseTypeTitleView.setVisibility(View.GONE);
                        licenseSpinner.setVisibility(View.GONE);
                    }else {
                        licenseTypeTitleView.setVisibility(View.VISIBLE);
                        licenseSpinner.setVisibility(View.VISIBLE);
                    }
                }
                if (photoRequired != null) {
                    if (!photoRequired) {
                        customerPhotoTitleView.setVisibility(View.GONE);
                        photoButton.setVisibility(View.GONE);
                    }else {
                        customerPhotoTitleView.setVisibility(View.VISIBLE);
                        photoButton.setVisibility(View.VISIBLE);
                    }
                }
                if (residenceRequired != null) {
                    if (!residenceRequired) {
                        proofOfResidenceTitleView.setVisibility(View.GONE);
                        residenceButton.setVisibility(View.GONE);
                    }else {
                        proofOfResidenceTitleView.setVisibility(View.VISIBLE);
                        residenceButton.setVisibility(View.VISIBLE);
                    }
                }
                if (statusRequired != null) {
                    if (!statusRequired) {
                        proofOfStatusTitleView.setVisibility(View.GONE);
                        statusButton.setVisibility(View.GONE);
                    }else {
                        proofOfStatusTitleView.setVisibility(View.VISIBLE);
                        statusButton.setVisibility(View.VISIBLE);
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void UploadPhoto(View view) {
        Intent galleryIntent;
        switch(view.getId()) {
            case R.id.uploadCustomerPhoto:
                galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, resultUploadPhoto);
                break;
            case R.id.uploadProofOfResidence:
                galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, resultUploadResidence);
                break;
            case R.id.uploadProofOfStatus:
                galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, resultUploadStatus);
                break;
        }
    }

    public void Back(View view) {
        Intent intent = new Intent(this, WelcomePage.class);
        startActivity(intent);
    }

    public void Submit(View view) {
        String firstName;
        String lastName;
        String address;
        String dob;
        LicenseType licenseType;
        String customerPhotoPath = null;
        String proofOfResidencePath = null;
        String proofOfStatusPath = null;


        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        firstName = firstNameEditText.getText().toString();
        if (firstName.equals("")) {
            firstName = null;
        }

        lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);
        lastName = lastNameEditText.getText().toString();
        if (lastName.equals("")) {
            lastName = null;
        }

        addressEditText = (EditText) findViewById(R.id.addressEditText);
        address = addressEditText.getText().toString();
        if (address.equals("")) {
            address = null;
        }

        dobEditText = (EditText) findViewById(R.id.dobEditText);
        dob = dobEditText.getText().toString();
        if (dob.equals("")) {
            dob = null;
        }

        String licenseTypeString = licenseSpinner.getSelectedItem().toString();
       /* switch (licenseTypeString) {
            case "G1":
                licenseType = LicenseType.G1;
            case "G2":
                licenseType = LicenseType.G2;
            case "G":
                licenseType = LicenseType.G;
            default:
                licenseType = null;
        }*/
        if(licenseTypeString.equals("G1")){
            licenseType = LicenseType.G1;
        }else if (licenseTypeString.equals("G2")){
            licenseType = LicenseType.G2;
        }else if (licenseTypeString.equals("G")){
            licenseType = LicenseType.G;
        }else{
            licenseType = null;
        }

        if (photoUri != null) {
            customerPhotoPath = "images/" + authReference.getCurrentUser().getUid() + "/" + serviceName + "/" + "customerPhoto.png";
            StorageReference photoLocation = storageReference.getReference(customerPhotoPath);
            UploadTask uploadTask = photoLocation.putFile(photoUri);
        }
        if (residenceUri != null) {
            proofOfResidencePath = "images/" + authReference.getCurrentUser().getUid() + "/" + serviceName + "/" + "residencePhoto.png";
            StorageReference photoLocation = storageReference.getReference(proofOfResidencePath);
            UploadTask uploadTask = photoLocation.putFile(residenceUri);
        }
        if (statusUri != null) {
            proofOfStatusPath = "images/" + authReference.getCurrentUser().getUid() + "/" + serviceName + "/" + "StatusPhoto.png";
            StorageReference photoLocation = storageReference.getReference(proofOfStatusPath);
            UploadTask uploadTask = photoLocation.putFile(statusUri);
        }

        newRequest = new ServiceRequest(serviceName, authReference.getCurrentUser().getUid(), firstName, lastName, address, dob, licenseType, customerPhotoPath, proofOfResidencePath, proofOfStatusPath);

        DatabaseReference dbRequestsRef = dbReference.child("users").child(branchID).child("serviceRequests");
        if (dbRequestsRef != null) {
            dbRequestsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<ServiceRequest> requests = new ArrayList<ServiceRequest>();

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        ServiceRequest request = postSnapshot.getValue(ServiceRequest.class);
                        requests.add(request);

                    }
                    requests.add(newRequest);

                    dbReference.child("users").child(branchID).child("serviceRequests").setValue(requests);
                    Toast.makeText(getApplicationContext(), "Request Submitted", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SubmitRequest.this, WelcomePage.class);
                    startActivity(intent);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri image = data.getData();
            if (requestCode == resultUploadPhoto) {
                photoUri = image;
            } else if (requestCode == resultUploadResidence) {
                residenceUri = image;
            } else if (requestCode == resultUploadStatus) {
                statusUri = image;
            }
        }
    }
}