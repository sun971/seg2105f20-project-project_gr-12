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


        // Check and populate fields if existing branch hours in database
        FirebaseUser user = session.getCurrentUser();
        final String id = user.getUid();
        DatabaseReference getHours = mDatabase.getReference("users/" + id);
        getHours.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild("monday") && snapshot.hasChild("tuesday") && snapshot.hasChild("wednesday")
                && snapshot.hasChild("thursday") && snapshot.hasChild("friday") && snapshot.hasChild("saturday") && snapshot.hasChild("sunday")) {

                    // Populate fields with existing values

                    EditText mondayStart = (EditText) findViewById(R.id.autoCompMonStart);
                    mondayStart.setText(snapshot.child("monday").getValue().toString());

                    EditText TuesStart = (EditText) findViewById(R.id.autoCompTueStart);
                    TuesStart.setText(snapshot.child("tuesday").getValue().toString());

                    EditText WednStart = (EditText) findViewById(R.id.autoCompWedStart);
                    WednStart.setText(snapshot.child("wednesday").getValue().toString());

                    EditText ThursStart = (EditText) findViewById(R.id.autoCompThursStart);
                    ThursStart.setText(snapshot.child("thursday").getValue().toString());

                    EditText FriStart = (EditText) findViewById(R.id.autoCompFriStart);
                    FriStart.setText(snapshot.child("friday").getValue().toString());

                    EditText SatStart = (EditText) findViewById(R.id.autoCompSatStart);
                    SatStart.setText(snapshot.child("saturday").getValue().toString());

                    EditText SunStart = (EditText) findViewById(R.id.autoCompSunStart);
                    SunStart.setText(snapshot.child("sunday").getValue().toString());
                }
                else    {
                    // Populate fields with default values

                    EditText mondayStart = (EditText) findViewById(R.id.autoCompMonStart);
                    mondayStart.setText("10:00 - 20:00");

                    EditText TuesStart = (EditText) findViewById(R.id.autoCompTueStart);
                    TuesStart.setText("10:00 - 20:00");

                    EditText WednStart = (EditText) findViewById(R.id.autoCompWedStart);
                    WednStart.setText("10:00 - 20:00");

                    EditText ThursStart = (EditText) findViewById(R.id.autoCompThursStart);
                    ThursStart.setText("10:00 - 20:00");

                    EditText FriStart = (EditText) findViewById(R.id.autoCompFriStart);
                    FriStart.setText("10:00 - 20:00");

                    EditText SatStart = (EditText) findViewById(R.id.autoCompSatStart);
                    SatStart.setText("10:00 - 20:00");

                    EditText SunStart = (EditText) findViewById(R.id.autoCompSunStart);
                    SunStart.setText("10:00 - 20:00");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public static boolean checkValidFormat(String branchHoursString)   {

        if(branchHoursString.toUpperCase().equals("CLOSED"))	{
            return true;
        }

        try	{

            String[] splitHours = branchHoursString.replace(" ", "").split("-");	// Strip spaces, make uppercase, and split into open and closing hours

            String rawOpenHours = splitHours[0];
            String rawCloseHours = splitHours[1];

            // Strip punctuation



            String[] openHoursDissected = rawOpenHours.split(":");
            String[] closeHoursDissected = rawCloseHours.split(":");

            if(!(openHoursDissected[0].matches("[0-9]+") && openHoursDissected[1].matches("[0-9]+")))   {
                return false;
            }

            if(!(closeHoursDissected[0].matches("[0-9]+") && closeHoursDissected[1].matches("[0-9]+")))   {
                return false;
            }

            int openHour = Integer.parseInt(openHoursDissected[0]);
            int openMin = Integer.parseInt(openHoursDissected[1]);

            int closeHour = Integer.parseInt(closeHoursDissected[0]);
            int closeMin = Integer.parseInt(closeHoursDissected[1]);

            // Check if hours are within the 24 hour clock

            if(!(openHour >= 0 && openHour <= 23 && closeHour >= 0 && closeHour <= 23))	{
                return false;
            }

            // Check if minutes are within the hour

            if(!(openMin >= 0 && openMin <= 59 && closeMin >= 0 && closeMin <= 49))	{
                return false;
            }

            // Check if times are the same

            if(openHour == closeHour && openMin == closeMin)	{
                return false;
            }

        }
        catch(Exception e)	{
            return false;
        }

        return true;



    }

    public void clickSubmit(View view) {

        EditText mondayStart = (EditText) findViewById(R.id.autoCompMonStart);
        final String monday = mondayStart.getText().toString();

        EditText TuesStart = (EditText) findViewById(R.id.autoCompTueStart);
        final String tues = TuesStart.getText().toString();

        EditText WednStart = (EditText) findViewById(R.id.autoCompWedStart);
        final String wed = WednStart.getText().toString();

        EditText ThursStart = (EditText) findViewById(R.id.autoCompThursStart);
        final String thurs = ThursStart.getText().toString();

        EditText FriStart = (EditText) findViewById(R.id.autoCompFriStart);
        final String friday = FriStart.getText().toString();

        EditText SatStart = (EditText) findViewById(R.id.autoCompSatStart);
        final String saturday = SatStart.getText().toString();

        EditText SunStart = (EditText) findViewById(R.id.autoCompSunStart);
        final String sunday = SunStart.getText().toString();


        if (monday.equals("") || tues.equals("") || wed.equals("") || thurs.equals("") || friday.equals("") || saturday.equals("")
                || sunday.equals("")) {
            setContentView(R.layout.activity_edit_branch_hours);
            Toast.makeText(getApplicationContext(), "All fields require a value", Toast.LENGTH_LONG).show();
        } else {

            // Check branch hours are valid formatting
            if(checkValidFormat(monday) && checkValidFormat(tues) && checkValidFormat(wed) && checkValidFormat(thurs)
                    && checkValidFormat(friday) && checkValidFormat(saturday) && checkValidFormat(sunday))  {

                FirebaseUser user = session.getCurrentUser();
                if (user != null) {
                    final String id = user.getUid();
                    DatabaseReference userData = mDatabase.getReference("users/" + id);

                    userData.child("monday").setValue(monday);
                    userData.child("tuesday").setValue(tues);
                    userData.child("wednesday").setValue(wed);
                    userData.child("thursday").setValue(thurs);
                    userData.child("friday").setValue(friday);
                    userData.child("saturday").setValue(saturday);
                    userData.child("sunday").setValue(sunday);

                    Toast.makeText(getApplicationContext(), "Updating Branch Hours", Toast.LENGTH_LONG).show();

                    Intent redirect = new Intent(EditBranchHours.this, EmployeeWelcome.class);
                    startActivity(redirect);

                }
            }
            else    {
                Toast.makeText(getApplicationContext(), "Invalid branch hours format entered", Toast.LENGTH_LONG).show();
            }


        }
    }
}