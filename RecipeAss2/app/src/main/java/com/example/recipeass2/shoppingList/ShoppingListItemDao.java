package com.example.recipeass2.shoppingList;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ShoppingListItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ShoppingListItem shoppingListItem);

    @Query("SELECT * FROM shopping_list_items")
    LiveData<List<ShoppingListItem>> getAllShoppingListItems();

    @Delete
    void delete(ShoppingListItem shoppingListItem);

    @Query("UPDATE shopping_list_items SET amount = :newAmount WHERE id = :itemId")
    void updateAmount(int itemId, int newAmount);
    @Query("DELETE FROM shopping_list_items")
    void deleteAll();
}
