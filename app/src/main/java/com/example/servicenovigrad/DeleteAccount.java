package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
//import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeleteAccount extends AppCompatActivity {
    private DatabaseReference dbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);


    }

    public void onStart()   {
        super.onStart();
        dbRef = FirebaseDatabase.getInstance().getReference("users");
    }

    private boolean deleteUser(String id) {

        DatabaseReference dR = dbRef.child(id);
        if (dR != null) {
            //"users/"+email)

            /*dR.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //TextView helloTextView = (TextView) findViewById(R.id.welcomeMessage);
                            //snapshot.child("password").getValue();

                            FirebaseAuth session = FirebaseAuth.getInstance();
                            session.signOut();

                            session.signInWithEmailAndPassword((String) snapshot.child("email").getValue(), (String) snapshot.child("password").getValue());
                            session.getCurrentUser().delete();
                            session.signOut();

                            session.signInWithEmailAndPassword("admin@admin.com", "adminpassword");
                            //helloTextView.setText("Welcome "+snapshot.child("firstName").getValue()+"! you are logged in as "+snapshot.child("accountType").getValue());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    }

            );*/

            dR.removeValue();
            Toast.makeText(getApplicationContext(), "Deleted User", Toast.LENGTH_LONG).show();
            return true;
        }else{
            return false;
        }
    }

    public void clickDelete(View v) {
        //if (v.getId() == R.id.deleteButton) {
            EditText emailField = (EditText)findViewById(R.id.deletedEmail);
            String email = emailField.getText().toString();

            dbRef.orderByChild("eMail").equalTo(email).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.getValue() != null) {

                                String user = String.valueOf(snapshot.getValue());
                                Log.e("DATA", user);
                            }else{
                                Toast.makeText(getApplicationContext(), "User doesn't exist", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    }
            );

        //}
    }

    public void onClick(View v) {
        if (v.getId() == R.id.backButton) {
            Intent intent = new Intent(this, AdminWelcome.class);
            startActivity(intent);

        }
    }
}