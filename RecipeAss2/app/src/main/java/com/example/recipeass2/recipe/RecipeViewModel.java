package com.example.recipeass2.recipe;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.recipeass2.model.Ingredient;
import com.example.recipeass2.model.Ingredient_Item;
import com.example.recipeass2.model.Item;
import com.example.recipeass2.model.ItemWithIngredient;
import com.example.recipeass2.repo.IngredientRepository;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel{

    private IngredientRepository ingredientRepository;


    private LiveData<List<Ingredient_Item>> allIngredientItem;

    public RecipeViewModel(Application application) {
        super(application);
        ingredientRepository = new IngredientRepository(application);
        allIngredientItem = ingredientRepository.getAllIngredientWithShopItem();
    }

    public void insertIngredient(Ingredient ingredient) {
        ingredientRepository.insertIngredient(ingredient);
    }

    public void insertItem(Item items) {
        ingredientRepository.insertItem(items);
    }

    public void insertItemWithIngredient(ItemWithIngredient itemWithIngredient) {
        ingredientRepository.insertItemWithIngredient(itemWithIngredient);
    }
    public LiveData<List<Ingredient_Item>> getAllIngredientItem() {
        return allIngredientItem;
    }

//    public void setAllPlaylistWithSongs(LiveData<List<PlaylistWithSongs>> allPlaylistWithSongs) {
//        this.allPlaylistWithSongs = allPlaylistWithSongs;
//    }
}
