package com.example.recipeass2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.recipeass2.MainActivity;
import com.example.recipeass2.databinding.SearchRecipeFragmentBinding;
import com.example.recipeass2.recipe.RecipeActivity;
import com.example.recipeass2.search.Recipe;
import com.example.recipeass2.search.RecipeAdapter;
import com.example.recipeass2.search.RecipeSearchResponse;
import com.example.recipeass2.search.SpoonacularApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchRecipeFragment extends Fragment implements RecipeAdapter.OnRecipeClickListener{
    private SearchRecipeFragmentBinding binding;
    private RecipeAdapter recipeAdapter;
    private static final String API_KEY = "c92ff870e8ae441ba53380608f13ed3c";
    private static final String BASE_URL = "https://api.spoonacular.com/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = SearchRecipeFragmentBinding.inflate(inflater, container, false);

        recipeAdapter = new RecipeAdapter(this);
        binding.searchResultsRecyclerview.setAdapter(recipeAdapter);
        binding.searchResultsRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

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

        return binding.getRoot();
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
        Intent intent = new Intent(getActivity(), RecipeActivity.class);
        intent.putExtra("recipe_id", recipe.getId()); // Pass the recipe ID
        startActivity(intent);
    }
}