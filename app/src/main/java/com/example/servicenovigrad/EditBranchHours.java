package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class EditBranchHours extends AppCompatActivity {
    AutoCompleteTextView autocomplete1;
    AutoCompleteTextView autocomplete2;
    AutoCompleteTextView autocomplete3;
    AutoCompleteTextView autocomplete4;
    AutoCompleteTextView autocomplete5;
    AutoCompleteTextView autocomplete6;
    AutoCompleteTextView autocomplete7;

    String [] time = {"9:00 am - 6:00pm", "10:00am - 6:00pm", "11:00 am - 6:00pm", "12:00 pm - 6:00pm", "1:00pm - 6:00pm",
            "2:00pm - 6:00pm","3:00pm - 6:00pm", "4:00 pm - 6:00pm", "5:00pm - 8:00pm", "6:00pm - 8:00pm", "7:00pm - 8:00pm",
            "8:00pm - 12:00 am", "9:00pm - 12:00am", "10:00 pm - 12:00am", "Closed", "closed", "Holiday Hours"};

    private FirebaseAuth session;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_branch_hours);
        session = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        //Monday
        autocomplete1  = (AutoCompleteTextView)
                findViewById(R.id.autoCompMonStart);
        //Tues
        autocomplete2  = (AutoCompleteTextView)
                findViewById(R.id.autoCompTueStart);
        //Wed
        autocomplete3  = (AutoCompleteTextView)
                findViewById(R.id.autoCompWedStart);
        //Thurs
        autocomplete4  = (AutoCompleteTextView)
                findViewById(R.id.autoCompThursStart);
        //Friday
        autocomplete5  = (AutoCompleteTextView)
                findViewById(R.id.autoCompFriStart);
        //Sat
        autocomplete6  = (AutoCompleteTextView)
                findViewById(R.id.autoCompSatStart);
        //Sun
        autocomplete7  = (AutoCompleteTextView)
                findViewById(R.id.autoCompSunStart);



        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, time);

        autocomplete1.setThreshold(1);
        autocomplete1.setAdapter(adapter);
        autocomplete2.setThreshold(1);
        autocomplete2.setAdapter(adapter);
        autocomplete3.setThreshold(2);
        autocomplete3.setAdapter(adapter);
        autocomplete4.setThreshold(2);
        autocomplete4.setAdapter(adapter);
        autocomplete5.setThreshold(2);
        autocomplete5.setAdapter(adapter);
        autocomplete6.setThreshold(2);
        autocomplete6.setAdapter(adapter);
        autocomplete7.setThreshold(2);
        autocomplete7.setAdapter(adapter);
    }

    public void clickSubmit(View view) {

        EditText mondayStart = (EditText) findViewById(R.id.autoCompMonStart);
        final String monday = mondayStart.getText().toString();

        EditText TuesStart = (EditText) findViewById(R.id.autoCompTueStart);
        final String tues = TuesStart.getText().toString();

        EditText WednStart = (EditText) findViewById(R.id.autoCompTueStart);
        final String wed = WednStart.getText().toString();

        EditText ThursStart = (EditText) findViewById(R.id.autoCompTueStart);
        final String thurs = ThursStart.getText().toString();

        EditText FriStart = (EditText) findViewById(R.id.autoCompTueStart);
        final String friday = FriStart.getText().toString();

        EditText SatStart = (EditText) findViewById(R.id.autoCompTueStart);
        final String saturday = SatStart.getText().toString();

        EditText SunStart = (EditText) findViewById(R.id.autoCompTueStart);
        final String sunday = SunStart.getText().toString();


        if (monday.equals("") || tues.equals("") || wed.equals("") || thurs.equals("") || friday.equals("") || saturday.equals("")
                || sunday.equals("")) {
            setContentView(R.layout.activity_edit_branch_hours);
            Toast.makeText(getApplicationContext(), "All fields require a value", Toast.LENGTH_LONG).show();
        } else {
            FirebaseUser user = session.getCurrentUser();
            if (user != null) {
                final String id = user.getUid();
                DatabaseReference userData = mDatabase.getReference("users/" + id);

                userData.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                //EmployeeAccount emp = new EmployeeAccount(id, snapshot.child("firstName").getValue().toString(), snapshot.child("lastName").getValue().toString(), snapshot.child("eMail").getValue().toString(), snapshot.child("password").getValue().toString(), address, phone);
                                //emp.setAddress(address);
                                //emp.setPhone(phone);
                                //Toast.makeText(getApplicationContext(), "address is:" +emp.getAddress(), Toast.LENGTH_LONG).show();


                                DatabaseReference addPhone = mDatabase.getReference("users/" + id);
                                EmployeeAccount emp = new EmployeeAccount(id, snapshot.child("firstName").getValue().toString(), snapshot.child("lastName").getValue().toString(), snapshot.child("eMail").getValue().toString(), snapshot.child("password").getValue().toString(), monday, tues, wed,
                                        thurs, friday, saturday, sunday);
                                addPhone.setValue(emp);
                                emp.setMonday(monday);
                                emp.setTuesday(tues);
                                emp.setWednesday(wed);
                                emp.setThursday(thurs);
                                emp.setFriday(friday);
                                emp.setSaturday(saturday);
                                emp.setSunday(sunday);

                                Toast.makeText(getApplicationContext(), "Updating Branch Hours", Toast.LENGTH_LONG).show();

                                Intent redirect = new Intent(EditBranchHours.this, EmployeeWelcome.class);
                                startActivity(redirect);


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        }

                );


            }


        }
    }
}