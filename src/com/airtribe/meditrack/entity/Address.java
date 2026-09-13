package com.airtribe.meditrack.entity;

import java.io.Serializable;

public class Address implements Cloneable, Serializable {
    private String line1;
    private String city;

    public Address(String line1, String city) {
        this.line1 = line1;
        this.city = city;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public Address clone() {
        return new Address(this.line1, this.city);
    }

    @Override
    public String toString() {
        return line1 + " " + city;
    }
}
