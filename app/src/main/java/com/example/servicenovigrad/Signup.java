package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class Signup extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
    }

    public void onStart()   {
        super.onStart();


    }

    public void goToLoginPage(View view) {
        Intent intent = new Intent(this, Login.class);

        startActivity(intent);
    }

    public void onClickSignup(View view) {
        EditText firstNameField = (EditText)findViewById(R.id.signupFirstName);
        EditText lastNameField = (EditText)findViewById(R.id.signupLastName);
        EditText usernameField = (EditText)findViewById(R.id.signupUsername);
        EditText emailField = (EditText)findViewById(R.id.signupEmail);
        EditText passwordField = (EditText)findViewById(R.id.signupPassword);
        RadioGroup accountTypeRadioGroup = (RadioGroup)findViewById(R.id.accountTypeRadioGroup);
        int checkedId = accountTypeRadioGroup.getCheckedRadioButtonId();



        if (firstNameField == null || lastNameField == null || usernameField == null || emailField == null || passwordField == null) {
            //They didnt enter a field.... sned toast notif
        }

        String firstName = firstNameField.getText().toString();
        String lastName = lastNameField.getText().toString();
        String userName = usernameField.getText().toString();
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        String accountType;

        switch (checkedId) {
            case R.id.employeeAccountSelect:
                accountType = "employee";
                break;
            case R.id.customerAccountSelect:
                accountType = "customer";
                break;
            default:
                //send toast notif
        }


        mAuth.createUserWithEmailAndPassword(email, password)
                .then((auth: firebase.auth.Auth) => { return auth; } )
                .catch((error: Error) => {

            let authError = error as firebase.auth.Error;
            let errorCode = authError.code;
            let errorMessage = authError.message;

            if (errorMessage === "auth/weak-password") {
                alert("The password is too weak.");
            } else {
                alert(errorMessage);
            }
            console.log(error);
        });


        //SEND CREDENTIALS TO REALTIME DATABASE

        Log.d("First Name", firstName);
        Log.d("Last Name", lastName);
        Log.d("Username",userName);
        Log.d("Email",email);
        Log.d("Password",password);
    }
}