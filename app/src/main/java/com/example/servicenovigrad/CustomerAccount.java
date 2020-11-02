package com.example.servicenovigrad;

public class CustomerAccount extends Account {
    CustomerAccount(String uid, String firstName, String lastName, String eMail, String password) {
        super(uid, firstName, lastName, eMail, password);
    }
    public String getAccountType() {
        return "customer";
    }
}
