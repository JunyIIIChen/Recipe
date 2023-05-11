package com.example.recipeass2.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.recipeass2.shoppingList.ShoppingListItem;
import com.example.recipeass2.shoppingList.ShoppingListItemDao;
import com.example.recipeass2.user.FavoriteRecipe;
import com.example.recipeass2.user.User;
import com.example.recipeass2.user.UserDao;
import com.example.recipeass2.user.UserFavoriteRecipeCrossRef;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {ShoppingListItem.class, User.class, FavoriteRecipe.class, UserFavoriteRecipeCrossRef.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract ShoppingListItemDao shoppingListItemDao();


    /*** TMH ***/
    public abstract UserDao userDao();
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /*** TMH ***/


    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}