package com.example.recipeass2.recipe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeass2.databinding.IngredientItemBinding;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private List<Ingredient> ingredientList;

    public IngredientAdapter(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        IngredientItemBinding binding = IngredientItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new IngredientViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredientList.get(position);
        holder.binding.ingredientName.setText(ingredient.getName());
        holder.binding.ingredientAmount.setText(String.format("%s %s", ingredient.getAmount(), ingredient.getUnit()));
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
        notifyDataSetChanged();
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        IngredientItemBinding binding;

        public IngredientViewHolder(IngredientItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
