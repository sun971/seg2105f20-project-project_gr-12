package com.example.servicenovigrad;

import org.junit.Test;

import static org.junit.Assert.*;

/* 
    Tests getter methods for employee. customer, and admin accounts
 */
public class UserAccountsTest {
    @Test
    public void getAccountTypeTest(){
        EmployeeAccount empAccount = new EmployeeAccount("Peter", "Griffin", "pgriff@gmail.com", "peter123");

        assertEquals("getAccountType() fails to return 'employee'","employee", empAccount.getAccountType());
    }
    @Test
    public void getFirstNameTest(){
        CustomerAccount cusAccount = new CustomerAccount("Peter", "Griffin", "pgriff@gmail.com", "peter123");

        assertEquals("getFirstName() fails to return 'Peter'","Peter", cusAccount.getFirstName());
    }
    @Test
    public void getPasswordTest(){
        AdminAccount admAccount = new AdminAccount();

        assertEquals("getPassword() fails to return 'adminpassword'","adminpassword", admAccount.getPassword());
    }
}
