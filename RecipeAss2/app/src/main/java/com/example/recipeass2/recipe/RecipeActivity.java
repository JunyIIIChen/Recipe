package com.example.recipeass2.recipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recipeass2.R;
import com.example.recipeass2.databinding.ActivityRecipeBinding;
import com.example.recipeass2.model.Ingredient;
import com.example.recipeass2.recipe.adapter.IngredientAdapter;
import com.example.recipeass2.model.Ingredient_Item;
import com.example.recipeass2.model.Item;
import com.example.recipeass2.model.ItemWithIngredient;
import com.example.recipeass2.search.Recipe;
import com.example.recipeass2.search.SpoonacularApiService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.bumptech.glide.Glide;

public class RecipeActivity extends AppCompatActivity {
    private Recipe recipe;

    private ActivityRecipeBinding binding;
    private RecipeViewModel recipeViewModel;

    private IngredientAdapter ingredientAdapter;

    private List<Item> courtItemList;
    private static final String API_KEY = "c92ff870e8ae441ba53380608f13ed3c";
    private static final String BASE_URL = "https://api.spoonacular.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int recipeId = getIntent().getIntExtra("recipe_id", -1);
        if (recipeId != -1) {
            fetchRecipeData(recipeId);
        } else {
            return;
            // Handle the error when there is no valid recipeId
        }
    }

    public void initAdapter() {
        ingredientAdapter = new IngredientAdapter(new ArrayList<Item>(), courtItemList);
        binding.ingredientList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        binding.ingredientList.setLayoutManager(new LinearLayoutManager(this));
        binding.ingredientList.setAdapter(ingredientAdapter);
    }

    private void fetchRecipeData(int recipeId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SpoonacularApiService spoonacularApiService = retrofit.create(SpoonacularApiService.class);
        Call<Recipe> call = spoonacularApiService.getRecipeInformation(recipeId, API_KEY);

        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if (response.isSuccessful()) {
                    recipe = response.body();
                    displayRecipeData();
                } else {
                    // Handle the error when the response is not successful
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                // Handle the error when the API call fails
            }
        });
    }
    private void displayRecipeData() {
        // Set recipe title
        binding.recipeName.setText(recipe.getTitle());
        Glide.with(this).load(recipe.getImageUrl()).into(binding.recipeImage);
        initAdapter();
    }
}
