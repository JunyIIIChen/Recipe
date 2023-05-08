package com.example.recipeass2.recipe;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.recipeass2.model.Item;
import com.example.recipeass2.model.ItemWithIngredient;
import com.example.recipeass2.recipe.Ingredient;

import java.util.List;


// use to get data
public class Ingredient_Item {
    @Embedded
    public Ingredient ingredient;

    @Relation(
            parentColumn = "ingredientName",
            entityColumn = "itemName",
            associateBy = @Junction(
                    value = ItemWithIngredient.class,
                    parentColumn = "ingredientName",
                    entityColumn = "itemName")
    )
    public List<Item> items;

    public Ingredient_Item(Ingredient ingredient, List<Item> items) {
        this.ingredient = ingredient;
        this.items = items;
    }
}
