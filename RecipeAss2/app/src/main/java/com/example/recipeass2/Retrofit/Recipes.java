package com.example.recipeass2.Retrofit;

import com.google.gson.annotations.SerializedName;

public class Recipes {
    @SerializedName("title")
    public String title;

    @SerializedName("image")
    public String image;

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}