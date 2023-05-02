package com.example.recipeass2;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.recipeass2.model.Ingredient;


@Entity(tableName = "shopping_items", foreignKeys = {@ForeignKey(entity = Ingredient.class,
        parentColumns = "id_ingredient",
        childColumns = "id",
        onDelete = ForeignKey.CASCADE)
})
public class ShoppingItem {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int quantity;


    private long id_fkShoppingItem;

    public long getId_fkShoppingItem() {
        return id_fkShoppingItem;
    }

    public void setId_fkShoppingItem(long id_fkShoppingItem) {
        this.id_fkShoppingItem = id_fkShoppingItem;
    }
    public ShoppingItem(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
