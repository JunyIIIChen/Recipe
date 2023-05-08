package com.example.recipeass2.shoppingList;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ShoppingListViewModel extends AndroidViewModel {
    private final ShoppingListRepository repository;
    private final LiveData<List<ShoppingListItem>> allShoppingListItems;

    public ShoppingListViewModel(@NonNull Application application) {
        super(application);
        repository = new ShoppingListRepository(application);
        allShoppingListItems = repository.getAllShoppingListItems();
    }

    public LiveData<List<ShoppingListItem>> getAllShoppingListItems() {
        return allShoppingListItems;
    }

    public void insert(ShoppingListItem shoppingListItem) {
        repository.insert(shoppingListItem);
    }

    public void deleteAllShoppingListItems() {
        repository.deleteAllShoppingListItems();
    }
}
