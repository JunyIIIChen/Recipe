package com.example.recipeass2.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.recipeass2.ShoppingItem;

import java.util.List;

public class ShoppingItemWithIngredient {

    @Embedded
    public Ingredient ingredient;
    @Relation(
            parentColumn = "id_ingredient",
            entityColumn = "id"
    )
    public List<ShoppingItem> shoppingItems;

    public ShoppingItemWithIngredient(Ingredient ingredient, List<ShoppingItem> shoppingItems) {
        this.ingredient = ingredient;
        this.shoppingItems = shoppingItems;
    }
}
