package com.example.recipeass2.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.example.recipeass2.MainActivity;
import com.example.recipeass2.databinding.ActivitySearchRecipeBinding;
import com.example.recipeass2.recipe.RecipeActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SearchRecipeActivity extends AppCompatActivity implements RecipeAdapter.OnRecipeClickListener{
    private ActivitySearchRecipeBinding binding;
    private RecipeAdapter recipeAdapter;
    private static final String API_KEY = "c92ff870e8ae441ba53380608f13ed3c";
    private static final String BASE_URL = "https://api.spoonacular.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchRecipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recipeAdapter = new RecipeAdapter(this);
        binding.searchResultsRecyclerview.setAdapter(recipeAdapter);
        binding.searchResultsRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        binding.searchEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchRecipes(s.toString());
            }
        });
    }

    private void searchRecipes(String query) {
        // Create a Retrofit instance for API requests
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SpoonacularApiService spoonacularApiService = retrofit.create(SpoonacularApiService.class);

        // Make an API request using the query string
        Call<RecipeSearchResponse> call = spoonacularApiService.searchRecipes(API_KEY, query);

        call.enqueue(new Callback<RecipeSearchResponse>() {
            @Override
            public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
                if (response.isSuccessful()) {
                    List<Recipe> recipes = response.body().getResults();
                    recipeAdapter.setRecipes(recipes);
                } else {
                    // Show an error message or handle the error
                }
            }

            @Override
            public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {
                // Show an error message or handle the error
            }

        });
    }
    public void onRecipeClick(Recipe recipe) {
        Intent intent = new Intent(SearchRecipeActivity.this, RecipeActivity.class);
        intent.putExtra("recipe_id", recipe.getId()); // Pass the recipe ID
        startActivity(intent);
    }
}