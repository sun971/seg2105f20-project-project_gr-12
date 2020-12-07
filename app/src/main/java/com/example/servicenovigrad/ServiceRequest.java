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

    private String serviceName;
    private String customerUid;
    private String firstName;
    private String lastName;
    private String address;
    private String dob;
    private LicenseType licenseType;
    private String customerPhotoUid;
    private String proofOfResidenceUid;
    private String proofOfStatusUid;

    ServiceRequest(String serviceName, String customerUid, String firstName, String lastName, String address, String dob, LicenseType licenseType, String customerPhotoUid, String proofOfResidenceUid, String proofOfStatusUid) {
        this.serviceName = serviceName;
        this.customerUid = customerUid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.dob = dob;
        this.licenseType = licenseType;
        this.customerPhotoUid = customerPhotoUid;
        this.proofOfResidenceUid = proofOfResidenceUid;
        this.proofOfStatusUid = proofOfStatusUid;
    }

    ServiceRequest() {}

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setCustomerUid(String customerUid) {
        this.customerUid = customerUid;
    }

    public String getCustomerUid() {
        return customerUid;
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

    public boolean equals(ServiceRequest request) {
        boolean sameFirstName;
        if (firstName == null) {
            sameFirstName = request.getFirstName() == null;
        } else {
            sameFirstName = firstName.equals(request.getFirstName());
        }

        boolean sameServiceName;
        if (serviceName == null) {
            sameServiceName = request.getServiceName() == null;
        } else {
            sameServiceName = serviceName.equals(request.getServiceName());
        }

        boolean sameCustomerUid;
        if (customerUid == null) {
            sameCustomerUid = request.getCustomerUid() == null;
        } else {
            sameCustomerUid = customerUid.equals(request.getCustomerUid());
        }

        boolean sameLastName;
        if (lastName == null) {
            sameLastName = request.getLastName() == null;
        } else {
            sameLastName = lastName.equals(request.getLastName());
        }

        boolean sameAddress;
        if (address == null) {
            sameAddress = request.getAddress() == null;
        } else {
            sameAddress = address.equals(request.getAddress());
        }

        boolean sameDob;
        if (dob == null) {
            sameDob = request.getDob() == null;
        } else {
            sameDob = dob.equals(request.getDob());
        }

        boolean sameLicenseType;
        if (licenseType == null) {
            sameLicenseType = request.getLicenseType() == null;
        } else {
            sameLicenseType = licenseType == request.getLicenseType();
        }

        boolean sameCustomerPhotoUid;
        if (customerPhotoUid == null) {
            sameCustomerPhotoUid = request.getCustomerPhotoUid() == null;
        }else {
            sameCustomerPhotoUid = customerPhotoUid.equals(request.getCustomerPhotoUid());
        }

        boolean sameProofOfResidencePhotoUid;
        if (proofOfResidenceUid == null) {
            sameProofOfResidencePhotoUid = request.getProofOfResidenceUid() == null;
        } else {
            sameProofOfResidencePhotoUid = proofOfResidenceUid.equals(request.getProofOfResidenceUid());
        }

        boolean sameProofOfStatusPhotoUid;
        if (proofOfStatusUid == null) {
            sameProofOfStatusPhotoUid = request.getProofOfStatusUid() == null;
        } else {
            sameProofOfStatusPhotoUid = proofOfStatusUid.equals(request.getProofOfStatusUid());
        }
        return (sameCustomerUid && sameServiceName && sameFirstName && sameLastName && sameAddress && sameDob && sameLicenseType && sameCustomerPhotoUid && sameProofOfResidencePhotoUid && sameProofOfStatusPhotoUid);
    }

}
