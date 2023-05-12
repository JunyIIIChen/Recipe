package com.example.recipeass2.home;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recipeass2.R;
import com.example.recipeass2.databinding.HomeRecipeItemBinding;
import com.example.recipeass2.recipe.RecipeActivity;
import com.example.recipeass2.search.Recipe;
import com.example.recipeass2.search.RecipeSearchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class HomeRecipeAdapter extends RecyclerView.Adapter<HomeRecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipesList;

    public HomeRecipeAdapter(List<Recipe> recipesList) {
        this.recipesList = recipesList;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        HomeRecipeItemBinding itemBinding = HomeRecipeItemBinding.inflate(layoutInflater, parent, false);
        return new RecipeViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipesList.get(position);
        holder.binding.recipeName.setText(recipe.getTitle());
        Glide.with(holder.itemView.getContext())
                .load(recipe.getImageUrl())
                .error(R.drawable.error_placeholder)
                .into(holder.binding.recipeImage);
    }

    @Override
    public int getItemCount() {
        return recipesList != null ? recipesList.size() : 0;
    }



    public void updateData(List<Recipe> newRecipesList) {
        this.recipesList = newRecipesList;
        notifyDataSetChanged();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {
        private HomeRecipeItemBinding binding;

        public RecipeViewHolder(HomeRecipeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Recipe recipe = recipesList.get(position);
                        Intent intent = new Intent(v.getContext(), RecipeActivity.class);
                        intent.putExtra("recipe_id", recipe.getId());
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }
    }
}
