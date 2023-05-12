package com.example.recipeass2.search;

import com.example.recipeass2.home.RecipeRandomSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SpoonacularApiService {
    @GET("recipes/complexSearch")
    Call<RecipeSearchResponse> searchRecipes(@Query("apiKey") String apiKey, @Query("query") String query);
    @GET("recipes/{id}/information")
    Call<Recipe> getRecipeInformation(@Path("id") int recipeId, @Query("apiKey") String apiKey);

    @GET("recipes/random")
    Call<RecipeRandomSearchResponse> customSearch(@Query("apiKey") String API_KEY, @Query("number") int number);
}
