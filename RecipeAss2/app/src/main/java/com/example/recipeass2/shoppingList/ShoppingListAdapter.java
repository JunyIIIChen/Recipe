package com.example.recipeass2.shoppingList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.Executors;

import com.example.recipeass2.R;
import com.example.recipeass2.database.AppDatabase;
import com.example.recipeass2.databinding.ShoppingListItemBinding;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingItemViewHolder> {

    private List<ShoppingListItem> shoppingList;
    private AppDatabase db;

    public ShoppingListAdapter(List<ShoppingListItem> shoppingList, AppDatabase db) {
        this.shoppingList = shoppingList;
        this.db = db;
    }

    @NonNull
    @Override
    public ShoppingItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.shopping_list_item, parent, false);
        return new ShoppingItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingItemViewHolder holder, int position) {
        ShoppingListItem item = shoppingList.get(position);
        holder.binding.itemName.setText(item.getName());
        holder.binding.itemQuantity.setText(String.valueOf(item.getAmount()));

        holder.binding.buttonDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = (int) item.getAmount();
                if (currentQuantity > 0) {
                    currentQuantity--;
                    item.setAmount(currentQuantity); // 更新item的amount属性
                    holder.binding.itemQuantity.setText(String.valueOf(currentQuantity));
                    updateQuantity(item.getId(), currentQuantity);
                }
            }
        });

        holder.binding.buttonIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = (int) item.getAmount();
                currentQuantity++;
                item.setAmount(currentQuantity); // 更新item的amount属性
                holder.binding.itemQuantity.setText(String.valueOf(currentQuantity));
                updateQuantity(item.getId(), currentQuantity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shoppingList.size();
    }

    static class ShoppingItemViewHolder extends RecyclerView.ViewHolder {
        ShoppingListItemBinding binding;

        public ShoppingItemViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ShoppingListItemBinding.bind(itemView);
        }
    }

    private void updateQuantity(int itemId, int newAmount) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                db.shoppingListItemDao().updateAmount(itemId, newAmount);
            }
        });
    }

    public void setShoppingList(List<ShoppingListItem> shoppingList) {
        this.shoppingList = shoppingList;
        notifyDataSetChanged();
    }
}
