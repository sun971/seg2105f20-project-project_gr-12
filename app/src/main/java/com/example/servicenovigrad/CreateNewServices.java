package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class CreateNewServices extends AppCompatActivity {
    //doc info
    public boolean firstname;
    public boolean lastname;
    public boolean address;
    //form info
    public boolean status;
    public boolean photoID;
    public boolean resident;

    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_services);

        db = FirebaseDatabase.getInstance();
    }


    public void back(View view) {
        Intent intent = new Intent(this, CurrentService.class);
        startActivity(intent);
    }

    public void onClickDone(View view) {

        // make sure the Name of Service  is filled in
        EditText checkName = (EditText) findViewById(R.id.serviceName);
        final String emptyField = checkName.getText().toString();
        if (emptyField.equals("") || emptyField.equals(" ") ){
            setContentView(R.layout.activity_create_new_services);
            Toast.makeText(getApplicationContext(),"Name of Service cannot be empty",Toast.LENGTH_LONG).show();
        }
        else    {
            //select at least one doc or form option
            if ((firstname== true || lastname==true) && (status== true || photoID==true || resident ==true)) {
                Toast.makeText(getApplicationContext(), "Processing new Service", Toast.LENGTH_LONG).show();

                // Add service to database
                EditText serviceNameEditText = (EditText) findViewById(R.id.serviceName); //angus i changed the name to service name
                CheckBox firstNameCheckBox = (CheckBox) findViewById(R.id.cbtnFirstName);
                CheckBox lastNameCheckBox = (CheckBox) findViewById(R.id.cbtnFirstName);
                CheckBox CheckBox = (CheckBox) findViewById(R.id.cbtnFirstName);

                CheckBox statusCheckbox = (CheckBox) findViewById(R.id.cbtnStatus);
                CheckBox photoIDCheckBox = (CheckBox) findViewById(R.id.cbtnPhoto);
                CheckBox residentCheckBox = (CheckBox) findViewById(R.id.cbtnRes);

                final String serviceNameField = serviceNameEditText.getText().toString();

                HashMap<String, Boolean> fieldsEnable = new HashMap<String, Boolean>();

                HashMap<String, Boolean> formsEnable = new HashMap<String, Boolean>();

                fieldsEnable.put("firstNameFieldEnable", firstname);
                fieldsEnable.put("lastNameFieldEnable", lastname);
                fieldsEnable.put("addressFieldEnable", address);

                formsEnable.put("statusFormEnable", status);
                formsEnable.put("photoIDFormEnable", photoID);
                formsEnable.put("residentFormEnable", resident);


                Log.e("Service:", serviceNameField);
                DatabaseReference addService = db.getReference("services/" + serviceNameField);

                addService.setValue(new Service(serviceNameField, fieldsEnable, formsEnable));


                Intent intent = new Intent(this, CurrentService.class);
                startActivity(intent);
            }
            //none selected
            if((firstname== false && lastname==false && address ==false) && (status== false && photoID==false && resident==false)){
                setContentView(R.layout.activity_create_new_services);
                Toast.makeText(getApplicationContext(),"Empty form",Toast.LENGTH_LONG).show();
            }
            //nothing in docs selected
            else if (firstname== false && lastname==false && address ==false)   {
                setContentView(R.layout.activity_create_new_services);
                Toast.makeText(getApplicationContext(),"Select at least one in docs",Toast.LENGTH_LONG).show();
            }
            //nothing in forms selected
            else if (status== false && photoID==false && resident ==false)  {
                setContentView(R.layout.activity_create_new_services);
                Toast.makeText(getApplicationContext(),"Select at least one in form",Toast.LENGTH_LONG).show();
            }
        }





    }

    public void onClickWelomePage(View view) {
        Intent intent = new Intent(this, AdminWelcome.class);
        startActivity(intent);
    }

    public void firstName(View view) {

        CheckBox checkBox = (CheckBox) (view);
        if (checkBox.isChecked()) {
            firstname=true;

        }
        if(!checkBox.isChecked()) {
            firstname = false;
        }
    }

    public void lastName(View view) {
        CheckBox checkBox = (CheckBox) (view);
        if (checkBox.isChecked()) {
            lastname=true;

        }
        if(!checkBox.isChecked()) {
            lastname = false;
        }
    }

    public void address(View view) {

        CheckBox checkBox = (CheckBox) (view);
        if (checkBox.isChecked()) {
            address=true;

        }
        if(!checkBox.isChecked()) {
            address = false;
        }
    }

    public void status(View view) {
        CheckBox checkBox = (CheckBox) (view);
        if (checkBox.isChecked()) {
            status=true;

        }
        if(!checkBox.isChecked()) {
            status = false;
        }
    }

    public void photoID(View view) {
        CheckBox checkBox = (CheckBox) (view);
        if (checkBox.isChecked()) {
            photoID=true;

        }
        if(!checkBox.isChecked()) {
            photoID = false;
        }
    }

    public void resident(View view) {
        CheckBox checkBox = (CheckBox) (view);
        if (checkBox.isChecked()) {
            resident =true;

        }
        if(!checkBox.isChecked()) {
            resident = false;
        }
    }
}
