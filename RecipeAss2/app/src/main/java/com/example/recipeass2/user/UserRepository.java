package com.example.recipeass2.user;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.recipeass2.database.AppDatabase;

public class UserRepository {

    private UserDao userDao;


    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
    }




    // User related operations
    public void insert(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDao.insert(user);
        });
    }

    public void update(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDao.update(user);
        });
    }

    public void delete(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDao.delete(user);
        });
    }

    public LiveData<User> getUser(String email) {
        return userDao.getUserByEmail(email);
    }





    // FavoriteRecipe related operations
    public long insert(FavoriteRecipe favoriteRecipe) {
        return userDao.insert(favoriteRecipe);
    }

    public LiveData<FavoriteRecipe> getFavoriteRecipe(int id) {
        return userDao.getFavoriteRecipeById(id);
    }

    public void delete(FavoriteRecipe favoriteRecipe) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDao.delete(favoriteRecipe);
        });
    }





    // UserFavoriteRecipeCrossRef related operations
    public void insert(UserFavoriteRecipeCrossRef crossRef) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDao.insert(crossRef);
        });
    }

    public void deleteFavoriteRecipe(String userEmail, int recipeId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDao.delete(userEmail, recipeId);
        });
    }

    public LiveData<UserWithFavoriteRecipes> getUserWithFavoriteRecipes(String userEmail) {
        return userDao.getUserWithFavoriteRecipes(userEmail);
    }
}

