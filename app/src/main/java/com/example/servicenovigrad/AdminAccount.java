package com.example.servicenovigrad;

public class AdminAccount extends Account {
    AdminAccount() {
        super("U5KARfZlCnWNJw6ZvbOS8eJuGYm1", "admin", "admin", "admin@admin.com", "adminpassword");
    }
    public String getAccountType() {
        return "admin";
    }
}
