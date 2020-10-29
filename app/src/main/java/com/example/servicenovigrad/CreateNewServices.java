package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Toast;

public class CreateNewServices extends AppCompatActivity {
    public boolean firstname;
    public boolean lastname;
    public boolean address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_services);
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
            setContentView(R.layout.activity_create_new_services);
            Toast.makeText(getApplicationContext(),"Processing new Service",Toast.LENGTH_LONG).show();
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
