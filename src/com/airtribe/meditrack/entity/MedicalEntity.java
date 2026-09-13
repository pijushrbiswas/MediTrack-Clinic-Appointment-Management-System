package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.util.Validator;

import java.io.Serializable;

public abstract class MedicalEntity implements Serializable {
    private final String id;

    protected MedicalEntity(String id) {
        Validator.requireNonBlank(id, "ID cannot be blank");
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public abstract Bill generateBill();
}
