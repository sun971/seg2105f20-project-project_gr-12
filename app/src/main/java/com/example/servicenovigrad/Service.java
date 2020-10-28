package com.example.servicenovigrad;

import java.util.ArrayList;
import java.util.List;

public class Service {
    private String name;
    private List<String> requiredInfo;
    private List<String> requiredDocs;
    private float price;

    //Creates a service with no required docs or info (to be added through the add methods)
    Service(String name, float price) {
        this.name = name;
        this.requiredInfo = new ArrayList<String>();
        this.requiredDocs = new ArrayList<String>();
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public List<String> getRequiredDocs() {
        return requiredDocs;
    }

    //Adds a new required doc to the list of required docs if the list does not already contain it and returns the new list
    public List<String> addRequiredDoc(String doc) {
        if (!requiredDocs.contains(doc)) {
            requiredDocs.add(doc);
        }
        return requiredDocs;
    }

    //Attempts to remove the  doc from the list of required docs and returns true if successful, false if not
    public boolean removeDoc(String doc) {
        return requiredDocs.remove(doc);
    }

    public List<String> getRequiredInfo() {
        return requiredInfo;
    }

    //Adds a new required info to the list of required info if the list does not already contain it and returns the new list
    public List<String> addRequiredInfo(String info) {
        if (!requiredInfo.contains(info)) {
            requiredInfo.add(info);
        }
        return requiredInfo;
    }

    //Attempts to remove the info from the list of required info and returns true if successful, false if not
    public boolean removeInfo(String info) {
        return requiredInfo.remove(info);
    }
}
