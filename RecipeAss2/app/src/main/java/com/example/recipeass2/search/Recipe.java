package com.example.recipeass2.search;

import com.example.recipeass2.recipe.Ingredient;

import java.util.List;

public class Recipe {
    private String title;
    private int id;
    private String imageType;
    private List<Ingredient> extendedIngredients;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getImageUrl() {
        return "https://spoonacular.com/recipeImages/"+id+"-"+"240x150."+imageType;
    }


    public String getImageType() {
        return imageType;
    }

    public void setType(String type) {
        this.imageType = type;
    }
    public List<Ingredient> getIngredients() {
        return extendedIngredients;
    }

    public void setIngredients(List<Ingredient> extendedIngredients) {
        this.extendedIngredients = extendedIngredients;
    }
}
