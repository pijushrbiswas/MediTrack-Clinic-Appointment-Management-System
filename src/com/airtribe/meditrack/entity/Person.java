package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.util.Validator;

public abstract class Person extends MedicalEntity {
    private String name;
    private String phone;

    protected Person(String id, String name, String phone) {
        super(id);
        setName(name);
        setPhone(phone);
    }

    public String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = Validator.requireNonBlank(name, "Name cannot be blank");
    }

    public String getPhone() {
        return phone;
    }

    public final void setPhone(String phone) {
        this.phone = Validator.requirePhone(phone);
    }
}

