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
    public boolean firstname;
    public boolean lastname;
    public boolean address;

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


        //docs require both first name and last name, where as address is optional
        if (firstname== false || lastname==false && address ==false)  {
            setContentView(R.layout.activity_create_new_services);
            Toast.makeText(getApplicationContext(),"Select both first name and last name",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplicationContext(),"Processing new Service",Toast.LENGTH_LONG).show();

            // Add service to database
            EditText serviceNameEditText = (EditText)findViewById(R.id.signupFirstName);
            CheckBox firstNameCheckBox = (CheckBox)findViewById(R.id.cbtnFirstName);
            CheckBox lastNameCheckBox = (CheckBox)findViewById(R.id.cbtnFirstName);
            CheckBox CheckBox = (CheckBox)findViewById(R.id.cbtnFirstName);

            final String serviceNameField = serviceNameEditText.getText().toString();

            HashMap<String, Boolean> fieldsEnable = new HashMap<String, Boolean>();

            fieldsEnable.put("firstNameFieldEnable", true);
            fieldsEnable.put("lastNameFieldEnable", true);
            fieldsEnable.put("addressFieldEnable", false);

            Log.e("Service:",serviceNameField);
            DatabaseReference addService = db.getReference("services/"+serviceNameField);

            addService.setValue(new Service(serviceNameField, fieldsEnable));


            Intent intent = new Intent(this, CurrentService.class);
            startActivity(intent);
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
}
