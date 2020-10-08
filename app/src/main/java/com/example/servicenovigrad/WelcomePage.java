package com.example.servicenovigrad;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.TextView;

//import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

public class WelcomePage extends AppCompatActivity {
    private FirebaseAuth session;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        session = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        getCurrentUser();
    }

    public void onStart()   {
        super.onStart();

        boolean loggedIn = false;

        //Check and redirect to login
        if(loggedIn == false)
        {
            Log.d("Msg","No one signed in");


        }
        //session.signOut();
    }



    public void goToLoginPage(View view) {
        Intent intent = new Intent(this, Login.class);

        startActivity(intent);
    }

    public void getCurrentUser(){
        FirebaseUser user = session.getCurrentUser();
        if (user != null) {
            String id = user.getUid();

            //String firstName = user.getDisplayName();
            String snap;
            DatabaseReference userData = mDatabase.getReference("users/" + id);

            userData.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            TextView helloTextView = (TextView) findViewById(R.id.welcomeMessage);
                            helloTextView.setText("Welcome "+snapshot.child("firstName").getValue()+", you are logged in as "+snapshot.child("accountType").getValue());

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    }

            );

            //String role = user.

            //EditText firstNameField = (EditText)findViewById(R.id.signupFirstName);

            // User is signed in
        } else {
            // No user is signed in
            //return "Nothing";
        }
    }




}