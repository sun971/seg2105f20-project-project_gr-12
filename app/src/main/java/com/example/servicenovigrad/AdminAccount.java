package com.example.servicenovigrad;

public class AdminAccount extends Account {
    AdminAccount(String firstName, String lastName, String eMail, String password) {
        super("admin", "admin", "admin@admin.com", "adminpassword");
    }
    public String getAccountType() {
        return "admin";
    }
}
