package com.example.servicenovigrad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EmployeeAccount extends Account {
    String address;
    String phone;

    private List<ServiceRequest> serviceRequests;
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

        serviceRequests = new ArrayList<ServiceRequest>();
        branchServices = new ArrayList<String>();

        branchServices.add("asdfg");
        branchServices.add("asdfg");


        serviceRequests.add(new ServiceRequest("Marshall", "Steele", "myAddress", "12/22/1997", LicenseType.G2, "asd", "asd", "asd"));
        serviceRequests.add(new ServiceRequest("Marshall2", "Steele2", "myAddress2", "12/22/1997", LicenseType.G2, "asd", "asd", "asd"));
    }
    EmployeeAccount(String uid, String firstName, String lastName, String eMail, String password, String address, String phone) {
        super(uid, firstName, lastName, eMail, password);
        this.address= address;
        this.phone = phone;
    }

    public String getAccountType() {
        return "employee";
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<ServiceRequest> getServiceRequests(){
        return serviceRequests;
    }

    public void removeServiceRequest(ServiceRequest request) {serviceRequests.remove(request); }

    public List<String> getBranchServices(){
        return branchServices;
    }

}