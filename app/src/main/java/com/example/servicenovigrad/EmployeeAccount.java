package com.example.servicenovigrad;

public class EmployeeAccount extends Account {
    EmployeeAccount(String firstName, String lastName, String eMail, String password) {
        super(firstName, lastName, eMail, password);
    }
    public String getAccountType() {
        return "employee";
    }
}
