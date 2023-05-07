package com.example.recipeass2.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.recipeass2.R;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe> recipes;
    private OnRecipeClickListener onRecipeClickListener;

    public RecipeAdapter(OnRecipeClickListener onRecipeClickListener) {
        this.onRecipeClickListener = onRecipeClickListener;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.recipeTitle.setText(recipe.getTitle());
    }

    @Override
    public int getItemCount() {
        return recipes != null ? recipes.size() : 0;
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView recipeTitle;

        RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeTitle = itemView.findViewById(R.id.recipe_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onRecipeClickListener.onRecipeClick(recipes.get(getAdapterPosition()));
        }
    }

    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }
}
