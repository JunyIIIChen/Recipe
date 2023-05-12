package com.example.recipeass2.database;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.recipeass2.shoppingList.ShoppingListItem;
import com.example.recipeass2.shoppingList.ShoppingListItemDao;
import com.example.recipeass2.user.FavoriteRecipe;
import com.example.recipeass2.user.User;
import com.example.recipeass2.user.UserDao;
import com.example.recipeass2.user.UserFavoriteRecipeCrossRef;
import com.example.recipeass2.user.UserWithFavoriteRecipes;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class UploadWorker extends Worker {
    public UploadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Get the UserDao
        UserDao userDao = AppDatabase.getDatabase(getApplicationContext()).userDao();

        // Get the ShoppingListItemDao
        ShoppingListItemDao shoppingListItemDao = AppDatabase.getDatabase(getApplicationContext()).shoppingListItemDao();

        // Get the user data
        List<User> users = userDao.getAllUsers(); // Modify this line according to your UserDao methods

        // Get the Firebase instance
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef  = database.getReference("users");

        // Add or update user data
        for (User user : users) {
            String emailPath = user.getEmail().replace('.', ',');
            usersRef .child(emailPath).setValue(user);

            // Get the user's favorite recipes
            UserWithFavoriteRecipes userWithFavoriteRecipes  = userDao.getUserWithFavoriteRecipesDirect(user.getEmail());
            // Add or update the user's favorite recipes
            for (FavoriteRecipe favoriteRecipe : userWithFavoriteRecipes.getFavoriteRecipes()) {
                usersRef.child(emailPath).child("favoriteRecipes").child(String.valueOf(favoriteRecipe.getId())).setValue(favoriteRecipe);
            }

            // Get the user's shopping list items
            List<ShoppingListItem> shoppingListItems = shoppingListItemDao.getAllShoppingListItemsDirect(); // Modify this line according to your ShoppingListItemDao methods
            // Add or update the user's shopping list items
            for (ShoppingListItem item : shoppingListItems) {
                usersRef.child(emailPath).child("shoppingList").child(String.valueOf(item.getId())).setValue(item);
            }
        }

        return Result.success();
    }
}
