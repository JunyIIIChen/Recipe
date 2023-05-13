package com.example.recipeass2.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipeass2.R;


public class FavoriteRecipeFragment extends Fragment {



    public FavoriteRecipeFragment() {
        // Required empty public constructor
    }

    public static FavoriteRecipeFragment newInstance(String param1, String param2) {
        FavoriteRecipeFragment fragment = new FavoriteRecipeFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite_recipe, container, false);
    }
}