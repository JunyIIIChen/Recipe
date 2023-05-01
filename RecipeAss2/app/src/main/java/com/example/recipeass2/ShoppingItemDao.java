package com.example.recipeass2;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ShoppingItemDao {

    @Insert
    void insert(ShoppingItem shoppingItem);

    @Query("SELECT * FROM shopping_items")
    List<ShoppingItem> getAllShoppingItems();

    @Query("UPDATE shopping_items SET quantity = :newQuantity WHERE id = :itemId")
    void updateQuantity(int itemId, int newQuantity);

}
