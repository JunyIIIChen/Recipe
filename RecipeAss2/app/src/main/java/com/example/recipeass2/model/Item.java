package com.example.recipeass2.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Entity(indices = @Index("itemName"))
public class Item implements Serializable {
    @PrimaryKey
    @ColumnInfo(name = "itemName")
    @NotNull

    private String itemName;


    public Item(@NonNull String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }


}
