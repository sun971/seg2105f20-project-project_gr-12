package com.example.servicenovigrad;

public class EmployeeAccount extends Account {
    EmployeeAccount(String uid, String firstName, String lastName, String eMail, String password) {
        super(uid, firstName, lastName, eMail, password);
    }
    public String getAccountType() {
        return "employee";
    }
}
