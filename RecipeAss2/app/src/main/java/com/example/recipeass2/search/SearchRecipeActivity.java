package com.example.recipeass2.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.recipeass2.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SearchRecipeActivity extends AppCompatActivity {
    private EditText searchEditText;
    private RecyclerView searchResultsRecyclerView;

    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipe);

        searchEditText = findViewById(R.id.search_edittext);
        searchResultsRecyclerView = findViewById(R.id.search_results_recyclerview);

        recipeAdapter = new RecipeAdapter();
        searchResultsRecyclerView.setAdapter(recipeAdapter);
        searchResultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchEditText.addTextChangedListener(new TextWatcher() {
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
                .baseUrl("https://api.spoonacular.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SpoonacularApiService spoonacularApiService = retrofit.create(SpoonacularApiService.class);

        // Make an API request using the query string
        Call<RecipeSearchResponse> call = spoonacularApiService.searchRecipes("c92ff870e8ae441ba53380608f13ed3c", query);

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
}