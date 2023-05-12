package com.example.recipeass2.search;

import com.example.recipeass2.Retrofit.SearchResponse;

import retrofit2.Call;

import retrofit2.http.Path;
import retrofit2.http.Query;

import retrofit2.http.GET;


public interface SpoonacularApiService {
    @GET("recipes/complexSearch")
    Call<RecipeSearchResponse> searchRecipes(@Query("apiKey") String apiKey, @Query("query") String query);
    @GET("recipes/{id}/information")
    Call<Recipe> getRecipeInformation(@Path("id") int recipeId, @Query("apiKey") String apiKey);

    @GET("recipes/random")
    Call<SearchResponse> customSearch(@Query("key") String API_KEY);
}
