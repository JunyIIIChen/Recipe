package com.example.recipeass2.user;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM user_table")
    List<User> getAllUsers();

    @Query("SELECT * FROM user_table WHERE email = :email")
    LiveData<User> getUserByEmail(String email);

//    @Query("SELECT * FROM user_table WHERE email = :email")
//    User getUserByEmailDirect(String email);

    @Insert
    long insert(FavoriteRecipe favoriteRecipe); // This method returns the new rowId for the inserted item.

    @Query("SELECT * FROM favorite_recipe_table WHERE saved_recipe_id = :id")
    LiveData<FavoriteRecipe> getFavoriteRecipeById(int id);

    @Delete
    void delete(FavoriteRecipe favoriteRecipe);

    @Insert
    void insert(UserFavoriteRecipeCrossRef crossRef);

    @Query("DELETE FROM user_favorite_recipe_table WHERE userEmail = :userEmail AND recipeId = :recipeId")
    void delete(String userEmail, int recipeId);

    @Transaction
    @Query("SELECT * FROM user_table WHERE email = :userEmail")
    LiveData<UserWithFavoriteRecipes> getUserWithFavoriteRecipes(String userEmail);

    @Transaction
    @Query("SELECT * FROM user_table WHERE email = :userEmail")
    UserWithFavoriteRecipes getUserWithFavoriteRecipesDirect(String userEmail);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdate(User user);
}

