package com.example.recipeass2.favoriteRecipe;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.recipeass2.database.AppDatabase;
import com.example.recipeass2.user.FavoriteRecipe;
import com.example.recipeass2.user.UserRepository;

import java.util.List;

public class FavoriteRecipeViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private LiveData<List<FavoriteRecipe>> favoriteRecipes;

    public FavoriteRecipeViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        String currentUserEmail = getCurrentUserEmail();
        if (currentUserEmail != null) {
            favoriteRecipes = Transformations.map(userRepository.getUserWithFavoriteRecipes(currentUserEmail),
                    userWithFavoriteRecipes -> userWithFavoriteRecipes != null ? userWithFavoriteRecipes.getFavoriteRecipes() : null);
        }
    }

    public LiveData<List<FavoriteRecipe>> getFavoriteRecipes() {
        return favoriteRecipes;
    }

    public void clearFavoriteRecipes() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<FavoriteRecipe> currentFavoriteRecipes = favoriteRecipes.getValue();
            if (currentFavoriteRecipes != null) {
                String currentUserEmail = getCurrentUserEmail();
                if (currentUserEmail != null) {
                    for (FavoriteRecipe favoriteRecipe : currentFavoriteRecipes) {
                        userRepository.deleteFavoriteRecipe(currentUserEmail, favoriteRecipe.getId());
                    }
                }
            }
        });
    }

    private String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        return sharedPreferences.getString("email", null);
    }
}
