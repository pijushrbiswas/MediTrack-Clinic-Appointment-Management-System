package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.util.Validator;

public class Patient extends Person implements Cloneable {
    private int age;
    private Address address;

    public Patient(String id, String name, String phone, int age, Address address) {
        super(id, name, phone);
        setAge(age);
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        Validator.requireRange(age, 1, 130, "Age must be between 1 and 130");
        this.age = age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public Bill generateBill() {
        double base = 500.0;
        if (age >= 60) {
            base *= 0.9;
        }
        return new Bill(base, "Base patient bill for " + getName());
    }

    @Override
    public Patient clone() {
        Address clonedAddress = address == null ? null : address.clone();
        return new Patient(getId(), getName(), getPhone(), age, clonedAddress);
    }

    @Override
    public String toString() {
        return "Patient{id='" + getId() + "', name='" + getName() + "', age=" + age + ", address=" + address + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Patient other)) return false;
        return getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
