package com.example.recipeass2;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.example.recipeass2.model.Ingredient;

@Entity(tableName = "ingredient_ShoppingItem",
        primaryKeys = { "ingredientId", "shoppingItemId" },
        foreignKeys = {
                @ForeignKey(entity = Ingredient.class,
                        parentColumns = "id",
                        childColumns = "ingredientId"),
                @ForeignKey(entity = ShoppingItem.class,
                        parentColumns = "id",
                        childColumns = "shoppingItemId")
        })

public class ingredient_Shoppingitem {

    @ColumnInfo(name = "ingredientId")
    private int ingredientId;

    @ColumnInfo(name = "shoppingItemId")
    private int shoppingItemId;

    public ingredient_Shoppingitem(int ingredientId, int shoppingItemId) {
        this.ingredientId = ingredientId;
        this.shoppingItemId = shoppingItemId;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public int getShoppingItemId() {
        return shoppingItemId;
    }

    public void setShoppingItemId(int shoppingItemId) {
        this.shoppingItemId = shoppingItemId;
    }


}
