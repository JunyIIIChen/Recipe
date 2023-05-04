package com.example.recipeass2.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.recipeass2.model.ShoppingItem;
import com.example.recipeass2.dao.ShoppingItemDao;
import com.example.recipeass2.model.Ingredient;
import com.example.recipeass2.dao.IngredientItemDao;
import com.example.recipeass2.model.Item;
import com.example.recipeass2.model.ItemWithIngredient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {ShoppingItem.class, Ingredient.class, ItemWithIngredient.class, Item.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ShoppingItemDao shoppingItemDao();
    public abstract IngredientItemDao ingredientShoppingItemDao();
    private static AppDatabase INSTANCE;
    //we create an ExecutorService with a fixed thread pool so we can later run
    //database operations asynchronously on a background thread.
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    //A synchronized method in a multi threaded environment means that two threads
    //are not allowed to access data at the same time
    public static synchronized AppDatabase getInstance(final Context
                                                                    context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "AppDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}