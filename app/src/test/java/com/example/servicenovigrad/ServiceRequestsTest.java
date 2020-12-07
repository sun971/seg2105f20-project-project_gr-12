package com.example.servicenovigrad;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ServiceRequestsTest {
    @Test
    public void serviceGetLicenseType(){

        ServiceRequest service = new ServiceRequest("Photo ID", "a2h398hd","Bob", "Smith", "4 Polk Street", "06/22/1995", LicenseType.G, "40222", "a1d1d1", "h12333");

        assertEquals(LicenseType.G, service.getLicenseType());
    }

    @Test
    public void serviceNullPhotoID(){

        ServiceRequest service = new ServiceRequest("Photo ID", "a2h398hd","Bob", "Smith", "4 Polk Street", "06/22/1995", LicenseType.G, null, "a1d1d1", "h12333");

        assertEquals(null, service.getCustomerPhotoUid());
    }
}