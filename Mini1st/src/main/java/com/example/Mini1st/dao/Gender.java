package com.example.Mini1st.dao;

import lombok.Getter;

public enum Gender {
    MALE("M"),
    FEMALE("F");

    private final String description;

    Gender(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}