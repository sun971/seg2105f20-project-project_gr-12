package com.example.servicenovigrad;

import java.util.List;

public class EmployeeAccount extends Account {

    private List<ServiceRequest> activeRequests;

    EmployeeAccount(String uid, String firstName, String lastName, String eMail, String password) {
        super(uid, firstName, lastName, eMail, password);
    }
    public String getAccountType() {
        return "employee";
    }

    public void addServiceRequest(ServiceRequest request) {
        activeRequests.add(request);
    }

    public boolean removeServiceRequest(ServiceRequest request) {
        return activeRequests.remove(request);
    }
}
