package com.example.recipeass2.user;

import androidx.room.Ignore;

public class Address {
    private String street;
    private String city;
    private String state;

    private String postcode;

    public Address() {
    }

    // Constructor, getters and setters
    @Ignore
    public Address(String street, String city, String state, String postcode) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.postcode = postcode;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Override
    public String toString() {
        return street+", "+city+", "+state+", "+postcode;
    }
}

