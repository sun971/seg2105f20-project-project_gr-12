package com.example.servicenovigrad;

public abstract class Account {
    private String eMail;
    private String firstName;
    private String lastName;
    private String password;
    Account(String firstName, String lastName, String eMail, String password) {
        this.eMail = eMail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public String geteMail() {
        return eMail;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }
    public abstract String getAccountType();

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

/*
How to create an account from the db:

FirebaseUser user = session.getCurrentUser();
    if (user != null) {
        String id = user.getUid();
        DatabaseReference userData = mDatabase.getReference("users/" + id);
        userData.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String accountType = (String) snapshot.child("accountType").getValue();
                        Account userAccount;
                        if (accountType.equals("admin")) {
                            //make admin object
                            userAccount = new AdminAccount();
                        } else if (accountType.equals("employee")) {
                            //make employee object
                            userAccount = new EmployeeAccount((String) snapshot.child("firstName").getValue(), (String) snapshot.child("lastName").getValue(), (String) snapshot.child("eMail").getValue(), (String) snapshot.child("password").getValue());
                        } else {
                            //make customer object
                            userAccount = new CustomerAccount((String) snapshot.child("firstName").getValue(), (String) snapshot.child("lastName").getValue(), (String) snapshot.child("eMail").getValue(), (String) snapshot.child("password").getValue());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }

        );


    } else {

    }
 */