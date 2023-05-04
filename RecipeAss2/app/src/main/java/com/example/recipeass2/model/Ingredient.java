package com.example.recipeass2.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "Ingredient")
public class Ingredient {


    @PrimaryKey
    @ColumnInfo(name = "ingredientName")
    @NotNull
//    private int id_ingredient;
    private String ingredientName;
    private String describe;




    public Ingredient(String ingredientName, String describe) {
        this.ingredientName = ingredientName;
        this.describe = describe;
    }

//    public int getId_ingredient() {
//        return id_ingredient;
//    }
//
//    public void setId_ingredient(int id_ingredient) {
//        this.id_ingredient = id_ingredient;
//    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }


}
