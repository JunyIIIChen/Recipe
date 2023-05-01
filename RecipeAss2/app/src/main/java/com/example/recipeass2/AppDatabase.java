package com.example.recipeass2;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ShoppingItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ShoppingItemDao shoppingItemDao();
}
