package com.example.servicenovigrad;

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
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

public class WelcomePage extends AppCompatActivity {
    private FirebaseAuth session;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        session = FirebaseAuth.getInstance();
        //final TextView helloTextView = (TextView) findViewById(R.id.welcomeMessage);
        //helloTextView.setText(getCurrentUser());
    }

    public void onStart()   {
        super.onStart();

        boolean loggedIn = false;

        //Check and redirect to login
        if(loggedIn == false)
        {
            Log.d("Msg","No one signed in");


        }
        session.signOut();
    }



    public void goToLoginPage(View view) {
        Intent intent = new Intent(this, Login.class);

        startActivity(intent);
    }

    public String getCurrentUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String id = user.getUid();

            //String firstName = user.getDisplayName();

            DatabaseReference firstName = mDatabase.getReference("users/" + id + "/firstName");
            DatabaseReference role = mDatabase.getReference("users/" + id + "/accountType");

            String str = "Welcome "+firstName.toString()+"! You are logged in as "+role.toString();
            return str;

            //String role = user.

            //EditText firstNameField = (EditText)findViewById(R.id.signupFirstName);

            // User is signed in
        } else {
            // No user is signed in
            return null;
        }
    }


}