package com.example.recipeass2.recipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.load.model.GlideUrl;
import com.example.recipeass2.R;
import com.example.recipeass2.databinding.ActivityRecipeBinding;
import com.example.recipeass2.model.Item;
import com.example.recipeass2.search.Recipe;
import com.example.recipeass2.search.SpoonacularApiService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.bumptech.glide.Glide;
import com.example.recipeass2.shoppingList.ShoppingListActivity;
import com.example.recipeass2.shoppingList.ShoppingListItem;

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

        ingredientAdapter = new IngredientAdapter(new ArrayList<>());
        binding.ingredientList.setAdapter(ingredientAdapter);
        binding.ingredientList.setLayoutManager(new LinearLayoutManager(this));

        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        binding.addToShoppingListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToShoppingList();
            }
        });

        int recipeId = getIntent().getIntExtra("recipe_id", -1);
        if (recipeId != -1) {
            fetchRecipeData(recipeId);
        } else {
            return;
            // Handle the error when there is no valid recipeId
        }
    }

    private void fetchRecipeData(int recipeId) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
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
                    Log.e("RecipeActivity", "API Response not successful, code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                Log.e("RecipeActivity", "API call failed: " + t.getMessage());
                Toast.makeText(RecipeActivity.this, "Failed to load recipe data. Please check your internet connection.", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void displayRecipeData() {
        // Set recipe title
        binding.recipeName.setText(recipe.getTitle());
        Glide.with(this)
                .load(recipe.getImageUrl())
                .error(R.drawable.error_placeholder)
                .into(binding.recipeImage);

        if (recipe.getIngredients() != null) {
            ingredientAdapter.setIngredientList(recipe.getIngredients());
        }
    }
    private void addToShoppingList() {
        if (recipe != null && recipe.getIngredients() != null) {
            for (Ingredient ingredient : recipe.getIngredients()) {
                ShoppingListItem shoppingListItem = new ShoppingListItem();
                shoppingListItem.setName(ingredient.getName());
                shoppingListItem.setUnit(ingredient.getUnit());
                shoppingListItem.setAmount(ingredient.getAmount());
                recipeViewModel.insertShoppingListItem(shoppingListItem);
            }
            Toast.makeText(this, "Ingredients added to shopping list.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ShoppingListActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Failed to add ingredients to shopping list.", Toast.LENGTH_SHORT).show();
        }
    }
}
