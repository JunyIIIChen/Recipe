package com.example.recipeass2.Retrofit;

import com.google.gson.annotations.SerializedName;

public class Recipes {
    @SerializedName("name")
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}