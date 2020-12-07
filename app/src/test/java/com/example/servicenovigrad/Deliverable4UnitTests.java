package com.example.servicenovigrad;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Deliverable4UnitTests {

    @Test
    public void serviceGetLicenseType(){

        ServiceRequest service = new ServiceRequest("Photo ID", "ca78gcff8qhfqfh8sgf","Bob", "Smith", "4 Polk Street", "06/22/1995", LicenseType.G, "40222", "a1d1d1", "h12333");

        assertEquals(LicenseType.G, service.getLicenseType());
    }

    @Test
    public void serviceNullPhotoID(){

        ServiceRequest service = new ServiceRequest("Photo ID", "ca78gcff8qhfqfh8sgf","Bob", "Smith", "4 Polk Street", "06/22/1995", LicenseType.G, null, "a1d1d1", "h12333");

        assertEquals(null, service.getCustomerPhotoUid());
    }

    @Test
    public void serviceGetFirstName(){

        ServiceRequest service = new ServiceRequest("Photo ID", "ca78gcff8qhfqfh8sgf","Bob", "Smith", "4 Polk Street", "06/22/1995", LicenseType.G, "40222", "a1d1d1", "h12333");

        assertEquals("Bob", service.getFirstName());
    }

    @Test
    public void serviceGetDob(){

        ServiceRequest service = new ServiceRequest("Photo ID", "ca78gcff8qhfqfh8sgf","Bob", "Smith", "4 Polk Street", "06/22/1995", LicenseType.G, "40222", "a1d1d1", "h12333");

        assertEquals("06/22/1995", service.getDob());
    }

    @Test
    public void serviceGetLastName(){

        ServiceRequest service = new ServiceRequest("Photo ID", "ca78gcff8qhfqfh8sgf","Bob", "Smith", "4 Polk Street", "06/22/1995", LicenseType.G, "40222", "a1d1d1", "h12333");

        assertEquals("Smith", service.getLastName());
    }

    @Test
    public void serviceGetAddress(){

        ServiceRequest service = new ServiceRequest("Photo ID", "ca78gcff8qhfqfh8sgf","Bob", "Smith", "4 Polk Street", "06/22/1995", LicenseType.G, "40222", "a1d1d1", "h12333");

        assertEquals("4 Polk Street", service.getAddress());
    }

    @Test
    public void serviceGetResidence(){

        ServiceRequest service = new ServiceRequest("Photo ID", "ca78gcff8qhfqfh8sgf","Bob", "Smith", "4 Polk Street", "06/22/1995", LicenseType.G, "40222", "a1d1d1", "h12333");

        assertEquals("a1d1d1", service.getProofOfResidenceUid());
    }

    @Test
    public void serviceGetStatus(){

        ServiceRequest service = new ServiceRequest("Photo ID", "ca78gcff8qhfqfh8sgf","Bob", "Smith", "4 Polk Street", "06/22/1995", LicenseType.G, "40222", "a1d1d1", "h12333");

        assertEquals("h12333", service.getProofOfStatusUid());
    }

    @Test
    public void serviceEquals(){

        ServiceRequest service = new ServiceRequest("Photo ID", "ca78gcff8qhfqfh8sgf","Bob", "Smith", "4 Polk Street", "06/22/1995", LicenseType.G, "40222", "a1d1d1", "h12333");
        ServiceRequest service2 = new ServiceRequest("Photo ID", "ca78gcff8qhfqfh8sgf","Bob", "Smith", "4 Polk Street", "06/22/1995", LicenseType.G, "40222", "a1d1d1", "h12333");
        assertEquals(true, service.equals(service2));
    }

    @Test
    public void serviceNotEquals(){

        ServiceRequest service = new ServiceRequest("Photo ID", "ca78gcff8qhfqfh8sgf","Bob", "Smith", "4 Polk Street", "06/22/1995", LicenseType.G, "40222", "a1d1d1", "h12333");
        ServiceRequest service2 = new ServiceRequest("Photo ID", "shfigns89ehfnow8f9awef","Jack", "Smith", "4 Polk Street", "06/22/1997", LicenseType.G, "24243", "a1d1d1", "h12333");
        assertEquals(false, service.equals(service2));
    }
}
