package com.example.servicenovigrad;

public class Account {
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
}
