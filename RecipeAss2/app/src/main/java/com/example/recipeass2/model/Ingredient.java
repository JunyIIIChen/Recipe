package com.example.recipeass2.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Ingredient")
public class Ingredient {


    @PrimaryKey(autoGenerate = true)
    private int id_ingredient;
    private String name;
    private String describe;




    public Ingredient(String name, String describe) {
        this.name = name;
        this.describe = describe;
    }

    public int getId_ingredient() {
        return id_ingredient;
    }

    public void setId_ingredient(int id_ingredient) {
        this.id_ingredient = id_ingredient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }


}
