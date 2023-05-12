package com.example.recipeass2.Retrofit;

import com.example.recipeass2.search.Recipe;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {
    //No need to map all keys, only those the elements you need
    @SerializedName("recipes")
    public List<Recipes> recipes;
    public List<Recipes> getRecipes() {
        return recipes;
    }
    public void setRecipes(List<Recipes> recipes) {
        this.recipes = recipes;
    }
}