package com.example.recipeass2.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @GET("recipes/random")
    Call<SearchResponse> customSearch(@Query("apiKey") String API_KEY);
//                                      @Query("cx") String SEARCH_ID_cx,
//                                      @Query ("q") String keyword);

}