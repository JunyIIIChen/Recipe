package com.example.recipeass2.model;

import android.app.Application;

import androidx.room.Dao;
import androidx.room.Database;

import com.example.recipeass2.AppDatabase;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class Repository {

    private IngredientShoppingItemDao ingredientShoppingItemDao;
    //private LiveData<List<>> allCustomers;
    public  Repository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        ingredientShoppingItemDao =db.ingredientShoppingItemDao();
        //allCustomers= customerDao.getAll();
    }
    // Room executes this query on a separate thread
//    public LiveData<List<Customer>> getAllCustomers() {
//        return allCustomers;
//    }
    public void insert(final Ingredient ingredient){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                ingredientShoppingItemDao.insertIngredient(ingredient);
            }
        });
    }
//    public void deleteAll(){
//        CustomerDatabase.databaseWriteExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                customerDao.deleteAll();
//            }
//        });
//    }
//    public void delete(final Customer customer){
//        CustomerDatabase.databaseWriteExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                customerDao.delete(customer);
//            }
//        });
//    }
//    public void updateCustomer(final Customer customer){
//        CustomerDatabase.databaseWriteExecutor.execute(new Runnable() {
//            @Override
//8 of 18
//            public void run() {
//                customerDao.updateCustomer(customer);
//            }
//        });
//    }
    public CompletableFuture<Long> insertIngredientReturnId (final Ingredient ingredient) {
        return CompletableFuture.supplyAsync(new Supplier<Long>() {
            @Override
            public Long get() {
                return ingredientShoppingItemDao.insertIngredient(ingredient);
            }
        }, AppDatabase.databaseWriteExecutor);
    }
}
