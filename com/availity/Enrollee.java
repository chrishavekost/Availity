package com.availity;

import java.util.Collection;

public class Enrollee {
    /*
     * Object to hold Enrollee fields from CSV files.
     * Name field included for changes going forward, but assignment makes it seem like first
     * and last are given separately  in the CSV.
     */

    private String _userId;
    private String _name;
    private String _firstName;
    private String _lastName;
    private String _insuranceCompany;
    private int _version;

    public String getUserId() {
       return _userId;
    }

    public void setUserId(String id) {
        this._userId = id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public String getFirstName() {
        return _firstName;
    }

    public void setFirstName(String firstName) {
        this._firstName = firstName;
    }

    public String getLastName() {
        return _lastName;
    }

    public void setLastName(String lastName) {
        this._lastName = lastName;
    }

    public String getInsuranceCompany() {
        return _insuranceCompany;
    }

    public void setInsuranceCompany(String company) {
        this._insuranceCompany = company;
    }

    public int getVersion() {
        return _version;
    }

    public void setVersion(String version) {
       this._version =  Integer.parseInt(version);
    }

}
