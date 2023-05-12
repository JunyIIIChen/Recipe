package com.example.recipeass2.home;

import com.example.recipeass2.search.Recipe;

import java.util.List;

public class RecipeRandomSearchResponse {
    private List<Recipe> recipes;

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
