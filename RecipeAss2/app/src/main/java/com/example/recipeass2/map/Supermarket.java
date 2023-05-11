package com.example.recipeass2.map;

public class Supermarket  {
    private String name;
    private String address;
    private String phone;

    public Supermarket(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}


