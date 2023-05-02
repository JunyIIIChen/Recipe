package com.example.recipeass2.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.recipeass2.ShoppingItem;

import java.util.List;

@Dao
public interface IngredientShoppingItemDao {
    // ... other methods ...

    //    @Query("SELECT Ingredient.name, shopping_items.quantity " +
//            "FROM Ingredient " +
//            "INNER JOIN ingredient_ShoppingItem " +
//            "ON Ingredient.id = ingredient_ShoppingItem.ingredientId " +
//            "INNER JOIN shopping_items " +
//            "ON shopping_items.id = ingredient_ShoppingItem.shoppingItemId")
//    List<ShoppingItem> getIngredientShoppingItems();
    @Transaction
    @Insert
    long insertIngredient(Ingredient ingredient);

    @Insert
    void insertStudents(List<ShoppingItem> shoppingItems);

    @Query("SELECT * FROM Ingredient WHERE id_ingredient = :ingredient LIMIT 1")
    Ingredient findByID(int ingredient);
}

