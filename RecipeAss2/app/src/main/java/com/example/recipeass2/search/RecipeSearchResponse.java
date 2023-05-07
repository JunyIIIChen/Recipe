package com.example.recipeass2.search;

import java.util.List;

public class RecipeSearchResponse {
    private List<Recipe> results;

    public List<Recipe> getResults() {
        return results;
    }

    public void setResults(List<Recipe> results) {
        this.results = results;
    }
}
