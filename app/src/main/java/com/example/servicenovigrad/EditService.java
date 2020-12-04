package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditService extends AppCompatActivity {

    public boolean firstname;
    public boolean lastname;
    public boolean address;
    public boolean dob;
    //form info
    public boolean status;
    public boolean photoID;
    public boolean resident;
    public boolean license;

    //keeps track if entered price is in valid format
    public boolean priceValid;

    FirebaseDatabase db;
    String serviceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_service);

        db = FirebaseDatabase.getInstance();

        Bundle bundle = getIntent().getExtras();
        serviceName = bundle.getString("serviceName");

        TextView serviceTextView = (TextView) findViewById(R.id.serviceNameText);
        serviceTextView.setText(serviceName);
    }

    public void onClickDone2(View view) {

        priceValid = true;

        //checks what state each check box is in and adds to variables
        CheckBox firstNameCheckBox = (CheckBox) findViewById(R.id.cbtnFirstName2);
        CheckBox lastNameCheckBox = (CheckBox) findViewById(R.id.rbtnlastName2);
        CheckBox addressCheckBox = (CheckBox) findViewById(R.id.rbtnaddress2);
        CheckBox dobCheckBox = (CheckBox) findViewById(R.id.rbtnDob2);
        CheckBox statusCheckbox = (CheckBox) findViewById(R.id.cbtnStatus2);
        CheckBox photoIDCheckBox = (CheckBox) findViewById(R.id.cbtnPhoto2);
        CheckBox residentCheckBox = (CheckBox) findViewById(R.id.cbtnRes2);
        CheckBox licenseCheckBox = (CheckBox) findViewById(R.id.cbtnLicense2);

        firstname = firstNameCheckBox.isChecked();
        lastname = lastNameCheckBox.isChecked();
        address = addressCheckBox.isChecked();
        dob = dobCheckBox.isChecked();
        status = statusCheckbox.isChecked();
        photoID = photoIDCheckBox.isChecked();
        resident = residentCheckBox.isChecked();
        license = licenseCheckBox.isChecked();

        // make sure the Name of Service and price is filled in

        EditText checkPrice = (EditText) findViewById(R.id.servicePrice2);


        String priceField = checkPrice.getText().toString();

        //takes away all spaces. tabs from the Service Name field


        //checks if the price has no more than 2 decimals
        if(!priceField.matches("^\\d+(.\\d{1,2})?$")){
            priceValid = false;
        }


        if(priceField.equals("")) {
            setContentView(R.layout.activity_edit_service);
            Toast.makeText(getApplicationContext(),"Please Enter a Price",Toast.LENGTH_LONG).show();
        }
        else    {
            //select at least one doc or form option
            if ((firstname== true || lastname==true) && (status== true || photoID==true || resident ==true) && (priceValid)) {


                // Add service to database

                EditText servicePriceEditText = (EditText) findViewById(R.id.servicePrice2);

                String servicePriceString = servicePriceEditText.getText().toString();

                //If user entered just a decimal, this will correct it to 0 automatically
                if(servicePriceString.equals(".")){
                    servicePriceString = "0.00";
                }

                final double servicePriceField = Double.parseDouble(servicePriceString);

                HashMap<String, Boolean> fieldsEnableTemp = new HashMap<String, Boolean>();
                HashMap<String, Boolean> formsEnableTemp = new HashMap<String, Boolean>();

                fieldsEnableTemp.put("firstNameFieldEnable", firstname);
                fieldsEnableTemp.put("lastNameFieldEnable", lastname);
                fieldsEnableTemp.put("addressFieldEnable", address);
                fieldsEnableTemp.put("dobFieldEnable", dob);
                fieldsEnableTemp.put("licenseFormEnable", license);

                formsEnableTemp.put("statusFormEnable", status);
                formsEnableTemp.put("photoIDFormEnable", photoID);
                formsEnableTemp.put("residentFormEnable", resident);


                final HashMap<String, Boolean> fieldsEnable = fieldsEnableTemp;
                final HashMap<String, Boolean> formsEnable = formsEnableTemp;

                Toast.makeText(getApplicationContext(), "Editing Service", Toast.LENGTH_SHORT).show();
                DatabaseReference editService = db.getReference("services/" + serviceName + "/price");
                editService.setValue(servicePriceField);
                editService = db.getReference("services/" + serviceName + "/requiredDocs");
                editService.setValue(formsEnable);
                editService = db.getReference("services/" + serviceName + "/requiredInfo");
                editService.setValue(fieldsEnable);


                Intent intent = new Intent(EditService.this, CurrentService.class);
                startActivity(intent);


            }

            //none selected
            if((firstname==false && lastname==false && address==false && dob==false && license==false) && (status==false && photoID==false && resident==false)){
                setContentView(R.layout.activity_edit_service);
                Toast.makeText(getApplicationContext(),"Document and Form Info Not Specified",Toast.LENGTH_LONG).show();
            }
            //nothing in forms selected
            else if (firstname==false && lastname==false && address==false && dob==false && license==false)   {
                setContentView(R.layout.activity_edit_service);
                Toast.makeText(getApplicationContext(),"Select at least one Form Info",Toast.LENGTH_LONG).show();

            }
            //nothing in documents selected
            else if (status==false && photoID==false && resident==false)  {
                setContentView(R.layout.activity_edit_service);
                Toast.makeText(getApplicationContext(),"Select at least one Document Info",Toast.LENGTH_LONG).show();

            }
            //Make sure not only address or dob field is checked
            else if(firstname == false && lastname == false && (address == true || dob == true || license==true))  {
                setContentView(R.layout.activity_edit_service);
                Toast.makeText(getApplicationContext(),"Select at least one name field",Toast.LENGTH_LONG).show();

            }
            //Makes sure the price is in correct format
            else if(!priceValid){
                setContentView(R.layout.activity_edit_service);
                Toast.makeText(getApplicationContext(),"Maximum Two Decimals for Price",Toast.LENGTH_LONG).show();
            }
        }

    }

        public void onClickCancel(View view) {
            Intent intent = new Intent(this, CurrentService.class);
            startActivity(intent);
        }
}