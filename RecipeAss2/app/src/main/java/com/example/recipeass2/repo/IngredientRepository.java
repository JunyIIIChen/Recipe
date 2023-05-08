//package com.example.recipeass2.repo;
//
//import android.app.Application;
//
//import androidx.lifecycle.LiveData;
//
//import com.example.recipeass2.database.AppDatabase;
//import com.example.recipeass2.dao.IngredientItemDao;
//import com.example.recipeass2.recipe.Ingredient;
//import com.example.recipeass2.recipe.Ingredient_Item;
//import com.example.recipeass2.model.Item;
//import com.example.recipeass2.model.ItemWithIngredient;
//
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import java.util.function.Supplier;
//
//public class IngredientRepository {
//
//    private IngredientItemDao ingredientItemDao;
//
//
//    private LiveData<List<Ingredient_Item>> allIngredientWithShopItem;
//
//    public IngredientRepository(Application application) {
//        AppDatabase db = AppDatabase.getInstance(application);
//        ingredientItemDao = db.ingredientShoppingItemDao();
//        allIngredientWithShopItem = ingredientItemDao.getIngredientItems();
//    }
//
//    // Room executes this query on a separate thread
////    public LiveData<List<Customer>> getAllCustomers() {
////        return allCustomers;
////    }
//    public void insertIngredient(final Ingredient ingredient) {
//        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                ingredientItemDao.insertIngredient(ingredient);
//            }
//        });
//    }
//
//    public void insertItem(final Item item) {
//        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                ingredientItemDao.insertItem(item);
//            }
//        });
//    }
//
//    public void insertItemWithIngredient(final ItemWithIngredient itemWithIngredient) {
//        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                ingredientItemDao.insertItemWithIngredient(itemWithIngredient);
//            }
//        });
//    }
//
//    //    public void deleteAll(){
////        CustomerDatabase.databaseWriteExecutor.execute(new Runnable() {
////            @Override
////            public void run() {
////                customerDao.deleteAll();
////            }
////        });
////    }
////    public void delete(final Customer customer){
////        CustomerDatabase.databaseWriteExecutor.execute(new Runnable() {
////            @Override
////            public void run() {
////                customerDao.delete(customer);
////            }
////        });
////    }
////    public void updateCustomer(final Customer customer){
////        CustomerDatabase.databaseWriteExecutor.execute(new Runnable() {
////            @Override
////8 of 18
////            public void run() {
////                customerDao.updateCustomer(customer);
////            }
////        });
////    }
//    public LiveData<List<Ingredient_Item>> getAllIngredientWithShopItem() {
//        return allIngredientWithShopItem;
//    }
//
//    public CompletableFuture<Long> insertIngredientReturnId(final Ingredient ingredient) {
//        return CompletableFuture.supplyAsync(new Supplier<Long>() {
//            @Override
//            public Long get() {
//                return ingredientItemDao.insertIngredient(ingredient);
//            }
//        }, AppDatabase.databaseWriteExecutor);
//    }
//}
