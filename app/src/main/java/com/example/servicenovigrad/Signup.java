package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
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

        String firstName = firstNameField.getText().toString();
        String lastName = lastNameField.getText().toString();
        String username = usernameField.getText().toString();
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        String accountType = "";

        if (firstName.equals("") || lastName.equals("") || username.equals("") || email.equals("") || password.equals("")) {
            //They didnt enter a field.... sned toast notif
            setContentView(R.layout.activity_signup);
            Toast.makeText(getApplicationContext(),"All fields require a value",Toast.LENGTH_LONG).show();
        } else {
            if (checkedId != R.id.employeeAccountSelect && checkedId != R.id.customerAccountSelect)  {
                    setContentView(R.layout.activity_signup);
                    Toast.makeText(getApplicationContext(),"Please enter an account type",Toast.LENGTH_LONG).show();
            } else{
                if (checkedId == R.id.employeeAccountSelect) {
                    accountType = "employee";
                } else {
                    accountType = "customer";
                }

                //Attempt to create firebase user
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()) {
                                    try {
                                        throw task.getException();
                                    } catch(FirebaseAuthWeakPasswordException e) {
                                        //Toast for weak password
                                        setContentView(R.layout.activity_signup);
                                        Toast.makeText(getApplicationContext(),"Weak Password",Toast.LENGTH_LONG).show(); //long
                                    } catch(FirebaseAuthInvalidCredentialsException e) {
                                        //Toast for invalid email
                                        setContentView(R.layout.activity_signup);
                                        Toast.makeText(getApplicationContext(),"Invalid email",Toast.LENGTH_LONG).show();
                                    } catch(FirebaseAuthUserCollisionException e) {
                                        //Toast for existing user with that email
                                        setContentView(R.layout.activity_signup);
                                        Toast.makeText(getApplicationContext(),"Error: Existing user with email",Toast.LENGTH_LONG).show();
                                    } catch(Exception e) {
                                        //Toast for general user auth error
                                        setContentView(R.layout.activity_signup);
                                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });

                //If account successfully created
                FirebaseUser user = mAuth.getCurrentUser();

                if (user != null) {
                    //SEND CREDENTIALS TO REALTIME DATABASE
                    //return user to login page
                    String userId = user.getUid();
                    DatabaseReference newUserFirstNameRef = mDatabase.getReference("users/" + userId + "/firstName");
                    DatabaseReference newUserLastNameRef = mDatabase.getReference("users/" + userId + "/lastName");
                    DatabaseReference newUserEmailRef = mDatabase.getReference("users/" + userId + "/email");
                    DatabaseReference newUserUsernameRef = mDatabase.getReference("users/" + userId + "/username");
                    DatabaseReference newUserPasswordRef = mDatabase.getReference("users/" + userId + "/password");
                    DatabaseReference newUserAccountTypeRef = mDatabase.getReference("users/" + userId + "/accountType");

                    newUserFirstNameRef.setValue(firstName);
                    newUserLastNameRef.setValue(lastName);
                    newUserEmailRef.setValue(email);
                    newUserUsernameRef.setValue(username);
                    newUserPasswordRef.setValue(password);
                    newUserAccountTypeRef.setValue(accountType);

                    Intent loginIntent = new Intent(this, Login.class);

                    startActivity(loginIntent);
                }





            }
        }
    }
}