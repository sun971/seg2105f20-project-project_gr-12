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
