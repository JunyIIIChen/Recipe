package com.example.recipeass2.repo;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.recipeass2.ShoppingItem;
import com.example.recipeass2.model.Ingredient;
import com.example.recipeass2.model.Ingredient_Item;
import com.example.recipeass2.model.Item;
import com.example.recipeass2.model.ItemWithIngredient;
import com.example.recipeass2.model.Repository;

import java.util.List;

public class IngredientViewModel extends AndroidViewModel{

    private Repository repository;


    private LiveData<List<Ingredient_Item>> allIngredientItem;

    public IngredientViewModel(Application application) {
        super(application);
        repository = new Repository(application);
        allIngredientItem = repository.getAllIngredientWithShopItem();
    }

    public void insertIngredient(Ingredient ingredient) {
        repository.insertIngredient(ingredient);
    }

    public void insertItem(Item items) {
        repository.insertItem(items);
    }

    public void insertItemWithIngredient(ItemWithIngredient itemWithIngredient) {
        repository.insertItemWithIngredient(itemWithIngredient);
    }
    public LiveData<List<Ingredient_Item>> getAllIngredientItem() {
        return allIngredientItem;
    }

//    public void setAllPlaylistWithSongs(LiveData<List<PlaylistWithSongs>> allPlaylistWithSongs) {
//        this.allPlaylistWithSongs = allPlaylistWithSongs;
//    }
}
