package com.example.recipeass2.user;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class UserWithFavoriteRecipes {
    @Embedded
    public User user;
    @Relation(
            parentColumn = "email",
            entityColumn = "id",
            associateBy = @Junction(UserFavoriteRecipeCrossRef.class)
    )
    public List<FavoriteRecipe> favoriteRecipes;
}

