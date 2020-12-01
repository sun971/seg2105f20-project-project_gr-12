package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SubmitRequest extends AppCompatActivity {
    String serviceName;
    String branchID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_request);

        //gets the service name passed from previous activity
        Bundle bundle = getIntent().getExtras();
        branchID = bundle.getString("branchID");
        serviceName = bundle.getString("serviceName");
    }
}