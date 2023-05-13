package com.example.recipeass2.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.recipeass2.R;
import com.example.recipeass2.favoriteRecipe.FavoriteRecipeAdapter;
import com.example.recipeass2.favoriteRecipe.FavoriteRecipeViewModel;


public class FavoriteRecipeFragment extends Fragment {
    private FavoriteRecipeViewModel favoriteRecipeViewModel;
    private FavoriteRecipeAdapter favoriteRecipeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_recipe, container, false);

        favoriteRecipeViewModel = new ViewModelProvider(this).get(FavoriteRecipeViewModel.class);

        ProgressBar progressBar = view.findViewById(R.id.progress_bar);
        RecyclerView recyclerView = view.findViewById(R.id.shop_list_recyclerview);
        Button clearListButton = view.findViewById(R.id.clearListButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        favoriteRecipeAdapter = new FavoriteRecipeAdapter();
        recyclerView.setAdapter(favoriteRecipeAdapter);

        favoriteRecipeViewModel.getFavoriteRecipes().observe(getViewLifecycleOwner(), favoriteRecipes -> {
            progressBar.setVisibility(View.GONE);
            favoriteRecipeAdapter.setFavoriteRecipes(favoriteRecipes);
        });

        clearListButton.setOnClickListener(v -> {
            favoriteRecipeViewModel.clearFavoriteRecipes();
        });

        return view;
    }
}