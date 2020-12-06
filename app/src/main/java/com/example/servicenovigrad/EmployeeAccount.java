package com.example.servicenovigrad;


import java.util.ArrayList;
import java.util.List;

public class EmployeeAccount extends Account {
    private String address;
    private String phone;

    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String saturday;
    private String sunday;

    private List<ServiceRequest> serviceRequests;
    private List<String> branchServices;

    EmployeeAccount(String uid, String firstName, String lastName, String eMail, String password) {
        super(uid, firstName, lastName, eMail, password);
        address = null;
        phone = null;

        serviceRequests = new ArrayList<ServiceRequest>();
        branchServices = new ArrayList<String>();
    }
    EmployeeAccount(String uid, String firstName, String lastName, String eMail, String password, String address, String phone) {
        super(uid, firstName, lastName, eMail, password);
        this.address= address;
        this.phone = phone;
    }
    EmployeeAccount(String uid, String firstName, String lastName, String eMail, String password, String Monday, String Tuesday, String Wednesday,
                    String Thursday, String Friday, String Saturday, String Sunday) {
        super(uid, firstName, lastName, eMail, password);
        this.monday= Monday;
        this.tuesday = Tuesday;
        this.wednesday = Wednesday;
        this.thursday = Thursday;
        this.friday= Friday;
        this.saturday=Saturday;
        this.sunday= Sunday;
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
    public String getMonday() {
        return monday;
    }
    public void setMonday(String monday) {
        this.monday = monday;
    }
    public String getTuesday() {
        return tuesday;
    }
    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }
    public String getWednesday() {
        return wednesday;
    }
    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }
    public String getThursday() {
        return thursday;
    }
    public void setThursday(String thursday) {
        this.thursday = thursday;
    }
    public String getFriday() {
        return friday;
    }
    public void setFriday(String friday) {
        this.friday = friday;
    }
    public String getSaturday() {
        return saturday;
    }
    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }
    public String getSunday() {
        return sunday;
    }
    public void setSunday(String sunday) {
        this.sunday = sunday;
    }


    public List<ServiceRequest> getServiceRequests(){
        return serviceRequests;
    }

    public void removeServiceRequest(ServiceRequest request) {serviceRequests.remove(request); }

    public List<String> getBranchServices(){
        return branchServices;
    }

}