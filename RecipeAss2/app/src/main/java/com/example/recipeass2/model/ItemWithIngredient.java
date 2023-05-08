package com.example.recipeass2.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import com.example.recipeass2.recipe.Ingredient;

import org.jetbrains.annotations.NotNull;

@Entity(primaryKeys = {"ingredientName", "itemName"},
        indices = {

                @Index(value = "ingredientName"),
                @Index(value = "itemName")},

        foreignKeys = {
                @ForeignKey(entity = Ingredient.class,
                        parentColumns = "ingredientName",
                        childColumns = "ingredientName"),
                @ForeignKey(entity = Item.class,
                        parentColumns = "itemName",
                        childColumns = "itemName")}
)
public class ItemWithIngredient {

    //    private final String ingredientName;
//    private final String itemName;
    @NotNull
    @ColumnInfo(name = "ingredientName")
    public String ingredientName;

    @NotNull
    @ColumnInfo(name = "itemName")
    public String itemName;


    public ItemWithIngredient(String ingredientName, String itemName) {
        this.ingredientName = ingredientName;
        this.itemName = itemName;
    }
}
