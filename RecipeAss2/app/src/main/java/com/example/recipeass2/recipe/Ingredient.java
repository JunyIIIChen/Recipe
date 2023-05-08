package com.example.recipeass2.recipe;

public class Ingredient {
    private String name;
    private String unit;
    private float amount;

    public Ingredient(String name, String unit, float amount) {
        this.name = name;
        this.unit = unit;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public float getAmount() {
        return amount;
    }
}