package com.example.recipeass2.recipe;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.recipeass2.shoppingList.ShoppingListItem;
import com.example.recipeass2.shoppingList.ShoppingListRepository;

public class RecipeViewModel extends AndroidViewModel{

    private ShoppingListRepository shoppingListRepository;

    public RecipeViewModel(Application application) {
        super(application);
        shoppingListRepository = new ShoppingListRepository(application);
    }
    public void insertShoppingListItem(ShoppingListItem shoppingListItem) {
    shoppingListRepository.insert(shoppingListItem);
    }


}
