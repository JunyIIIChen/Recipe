package com.example.recipeass2.recipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.recipeass2.databinding.ActivityRecipeBinding;
import com.example.recipeass2.model.Ingredient;
import com.example.recipeass2.recipe.adapter.IngredientAdapter;
import com.example.recipeass2.model.Ingredient_Item;
import com.example.recipeass2.model.Item;
import com.example.recipeass2.model.ItemWithIngredient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {

    private ActivityRecipeBinding binding;
    private RecipeViewModel recipeViewModel;

    private IngredientAdapter ingredientAdapter;

    private List<Item> courtItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRecipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        recipeViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(RecipeViewModel.class);
        courtItemList = new ArrayList<>();
        //test();
        initAdapter();
        test2();
        binding.buttonGotoCourt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RecipeActivity.this, CourtActivity.class);
                intent.putExtra("courtName", (Serializable) ingredientAdapter.getCourtItemList());
                startActivity(intent);
            }
        });
    }

    public void test() {
        Ingredient ingredient1 = new Ingredient("Creamy Roasted Pumpkin Soup", "xxx");
        recipeViewModel.insertIngredient(ingredient1);
        Ingredient ingredient2 = new Ingredient("toast", "xxx");
        recipeViewModel.insertIngredient(ingredient2);
        Item item1 = new Item("Garlic");
        recipeViewModel.insertItem(item1);
        Item item2 = new Item("Milk");
        recipeViewModel.insertItem(item2);
        Item item3 = new Item("Pumpkin");
        recipeViewModel.insertItem(item3);
        ItemWithIngredient itemWithIngredient1 = new ItemWithIngredient("Creamy Roasted Pumpkin Soup", "Garlic");
        recipeViewModel.insertItemWithIngredient(itemWithIngredient1);
        ItemWithIngredient itemWithIngredient2 = new ItemWithIngredient("Creamy Roasted Pumpkin Soup", "Milk");
        recipeViewModel.insertItemWithIngredient(itemWithIngredient2);
        ItemWithIngredient itemWithIngredient3 = new ItemWithIngredient("Creamy Roasted Pumpkin Soup", "Pumpkin");
        recipeViewModel.insertItemWithIngredient(itemWithIngredient3);


    }

    public void initAdapter() {
        ingredientAdapter = new IngredientAdapter(new ArrayList<Item>(), courtItemList);
        binding.itemListRecyclerview.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        binding.itemListRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.itemListRecyclerview.setAdapter(ingredientAdapter);
       // ingredientAdapter.notifyDataSetChanged();
    }

    public void test2() {
        recipeViewModel.getAllIngredientItem().observe(this, new Observer<List<Ingredient_Item>>() {
            @Override
            public void onChanged(List<Ingredient_Item> ingredient_Items) {
                Ingredient_Item temp = ingredient_Items.get(0);
                binding.recipeName.setText(temp.ingredient.getIngredientName());
                binding.recipeDescribe1.setText(temp.ingredient.getDescribe() + "\n\n");
                ingredientAdapter.setItemList(temp.items);
                ingredientAdapter.getItemList();
               // initAdapter(temp);
            }
        });


    }
}