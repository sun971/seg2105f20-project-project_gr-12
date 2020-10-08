package com.example.servicenovigrad;

public class AdminAccount extends Account {
    AdminAccount() {
        super("admin", "admin", "admin@admin.com", "adminpassword");
    }
    public String getAccountType() {
        return "admin";
    }
}
