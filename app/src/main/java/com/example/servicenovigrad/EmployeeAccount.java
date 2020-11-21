package com.example.servicenovigrad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EmployeeAccount extends Account {

    private List<ServiceRequest> activeRequests;
    private List<String> branchServices;

    EmployeeAccount(String uid, String firstName, String lastName, String eMail, String password) {
        super(uid, firstName, lastName, eMail, password);
        double servicePriceField = 12.65;

        HashMap<String, Boolean> fieldsEnableTemp = new HashMap<String, Boolean>();
        HashMap<String, Boolean> formsEnableTemp = new HashMap<String, Boolean>();

        fieldsEnableTemp.put("firstNameFieldEnable", true);
        fieldsEnableTemp.put("lastNameFieldEnable", true);
        fieldsEnableTemp.put("addressFieldEnable", true);
        fieldsEnableTemp.put("dobFieldEnable", false);
        fieldsEnableTemp.put("licenseFormEnable", false);

        formsEnableTemp.put("statusFormEnable", false);
        formsEnableTemp.put("photoIDFormEnable", true);
        formsEnableTemp.put("residentFormEnable", true);

        HashMap<String, Boolean> fieldsEnable = fieldsEnableTemp;
        HashMap<String, Boolean> formsEnable = formsEnableTemp;

        activeRequests = new ArrayList<ServiceRequest>();
        branchServices = new ArrayList<String>();

        branchServices.add("asdfg");
        branchServices.add("asdfg");


        activeRequests.add(new ServiceRequest("Marshall", "Steele", "myAddress", "12/22/1997", LicenseType.G2, "asd", "asd", "asd"));
        activeRequests.add(new ServiceRequest("Marshall2", "Steele2", "myAddress2", "12/22/1997", LicenseType.G2, "asd", "asd", "asd"));
    }
    public String getAccountType() {
        return "employee";
    }

    public List<ServiceRequest> getServiceRequests(){
        return activeRequests;
    }

    public List<String> getBranchServices(){
        return branchServices;
    }

}