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

        final String firstName = firstNameField.getText().toString();
        final String lastName = lastNameField.getText().toString();
        final String username = usernameField.getText().toString();
        final String email = emailField.getText().toString();
        final String password = passwordField.getText().toString();
        final String accountType;

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
                                else
                                {
                                    String user = mAuth.getCurrentUser().getUid();

                                    //If account successfully created

                                        //SEND CREDENTIALS TO REALTIME DATABASE
                                        //return user to login page
                                        DatabaseReference newUserFirstNameRef = mDatabase.getReference("users/" + user + "/firstName");
                                        DatabaseReference newUserLastNameRef = mDatabase.getReference("users/" + user + "/lastName");
                                        DatabaseReference newUserEmailRef = mDatabase.getReference("users/" + user + "/email");
                                        DatabaseReference newUserUsernameRef = mDatabase.getReference("users/" + user + "/username");
                                        DatabaseReference newUserPasswordRef = mDatabase.getReference("users/" + user + "/password");
                                        DatabaseReference newUserAccountTypeRef = mDatabase.getReference("users/" + user + "/accountType");

                                        newUserFirstNameRef.setValue(firstName);
                                        newUserLastNameRef.setValue(lastName);
                                        newUserEmailRef.setValue(email);
                                        newUserUsernameRef.setValue(username);
                                        newUserPasswordRef.setValue(password);
                                        newUserAccountTypeRef.setValue(accountType);

                                        Intent loginIntent = new Intent(Signup.this, Login.class);

                                        startActivity(loginIntent);


                                }
                            }
                        });







            }
        }
    }
}