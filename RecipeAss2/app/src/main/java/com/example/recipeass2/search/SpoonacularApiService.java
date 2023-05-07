package com.example.recipeass2.search;

import retrofit2.Call;

import retrofit2.http.Query;

import retrofit2.http.GET;


public interface SpoonacularApiService {
    @GET("recipes/complexSearch")
    Call<RecipeSearchResponse> searchRecipes(@Query("apiKey") String apiKey, @Query("query") String query);
}
