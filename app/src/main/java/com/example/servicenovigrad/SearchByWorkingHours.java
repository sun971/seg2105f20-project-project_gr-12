package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SearchByWorkingHours extends AppCompatActivity {

    ListView listOfWorkingHours;
    DatabaseReference databaseServices;

    List<String> workingHours;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_working_hours);
    }

    public void searchMonday(View view) {
        if (view.getId() == R.id.btnMonday) {
            Intent intent = new Intent(this, ListHoursGivenDate.class);
            intent.putExtra("Day", "monday");
            startActivity(intent);
        }
    }

    public void searchThursday(View view) {
        if (view.getId() == R.id.btnThursday) {
            Intent intent = new Intent(this, ListHoursGivenDate.class);
            intent.putExtra("Day", "thursday");
            startActivity(intent);
        }
    }

    public void searchSunday(View view) {
        if (view.getId() == R.id.btnSunday) {
            Intent intent = new Intent(this, ListHoursGivenDate.class);
            intent.putExtra("Day", "sunday");
            startActivity(intent);
        }
    }

    public void searchWednesday(View view) {
        if (view.getId() == R.id.btnWednesday) {
            Intent intent = new Intent(this, ListHoursGivenDate.class);
            intent.putExtra("Day", "wednesday");
            startActivity(intent);

        }
    }

    public void searchFriday(View view) {
        if (view.getId() == R.id.btnFriday) {
            Intent intent = new Intent(this, ListHoursGivenDate.class);
            intent.putExtra("Day", "friday");
            startActivity(intent);
        }
    }

    public void searchSaturday(View view) {
        if (view.getId() == R.id.btnSaturday) {
            Intent intent = new Intent(this, ListHoursGivenDate.class);
            intent.putExtra("Day", "saturday");
            startActivity(intent);
        }
    }

    public void searchTuesday(View view) {
        if (view.getId() == R.id.btnTuesday) {
            Intent intent = new Intent(this, ListHoursGivenDate.class);
            intent.putExtra("Day", "tuesday");
            startActivity(intent);
        }
    }

    public void backToWelcomeCustomer(View view) {
        if (view.getId() == R.id.btnbackToWelcomeCustomer) {
            Intent intent = new Intent(this, WelcomePage.class);
            startActivity(intent);
        }
    }
}