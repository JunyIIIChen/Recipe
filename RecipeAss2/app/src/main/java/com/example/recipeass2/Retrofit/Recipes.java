package com.example.recipeass2.Retrofit;

import com.google.gson.annotations.SerializedName;

public class Recipes {
    @SerializedName("title")
    public String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}