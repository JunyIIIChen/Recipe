package com.example.recipeass2.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.recipeass2.model.Ingredient;
import com.example.recipeass2.model.Ingredient_Item;
import com.example.recipeass2.model.Item;
import com.example.recipeass2.model.ItemWithIngredient;

import java.util.List;

@Dao
public interface IngredientItemDao {
    // ... other methods ...

    //    @Query("SELECT Ingredient.name, shopping_items.quantity " +
//            "FROM Ingredient " +
//            "INNER JOIN ingredient_ShoppingItem " +
//            "ON Ingredient.id = ingredient_ShoppingItem.ingredientId " +
//            "INNER JOIN shopping_items " +
//            "ON shopping_items.id = ingredient_ShoppingItem.shoppingItemId")
//    List<ShoppingItem> getIngredientShoppingItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertIngredient(Ingredient ingredient);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(Item items);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertItemWithIngredient(ItemWithIngredient itemWithIngredient);

//    @Transaction
//    @Query("SELECT * FROM Ingredient WHERE id_ingredient = :ingredient LIMIT 1")
//    Ingredient findByID(int ingredient);
    @Transaction
    @Query("SELECT * FROM Ingredient")
    LiveData<List<Ingredient_Item>> getIngredientItems();
}

