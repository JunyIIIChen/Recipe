package com.example.recipeass2.favoriteRecipe;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeass2.R;
import com.example.recipeass2.user.FavoriteRecipe;

import java.util.ArrayList;
import java.util.List;

public class FavoriteRecipeAdapter extends RecyclerView.Adapter<FavoriteRecipeAdapter.FavoriteRecipeViewHolder> {
    private List<FavoriteRecipe> favoriteRecipes = new ArrayList<>();

    @NonNull
    @Override
    public FavoriteRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_list_item, parent, false);
        return new FavoriteRecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteRecipeViewHolder holder, int position) {
        FavoriteRecipe favoriteRecipe = favoriteRecipes.get(position);
        holder.recipeNameTextView.setText(favoriteRecipe.getName());
        holder.recipeTypeTextView.setText(favoriteRecipe.getType());
    }

    @Override
    public int getItemCount() {
        return favoriteRecipes.size();
    }

    public void setFavoriteRecipes(List<FavoriteRecipe> favoriteRecipes) {
        this.favoriteRecipes = favoriteRecipes;
        notifyDataSetChanged();
    }

    class FavoriteRecipeViewHolder extends RecyclerView.ViewHolder {
        private TextView recipeNameTextView;
        private TextView recipeTypeTextView;

        public FavoriteRecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeNameTextView = itemView.findViewById(R.id.recipe_name);
            recipeTypeTextView = itemView.findViewById(R.id.recipe_type);
        }
    }
}
