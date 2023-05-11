package com.example.recipeass2.user;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "user_favorite_recipe_table", primaryKeys = {"userEmail", "recipeId"})
public class UserFavoriteRecipeCrossRef {
    @NonNull
    private String userEmail;
    private int recipeId;

    public UserFavoriteRecipeCrossRef(@NonNull String userEmail, int recipeId) {
        this.userEmail = userEmail;
        this.recipeId = recipeId;
    }

    @NonNull
    public String getUserEmail() {
        return userEmail;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setUserEmail(@NonNull String userEmail) {
        this.userEmail = userEmail;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    // Constructor, getters and setters
}
