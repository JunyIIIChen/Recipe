package com.example.recipeass2.shoppingItem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.recipeass2.R;
import com.example.recipeass2.database.AppDatabase;
import com.example.recipeass2.databinding.ShoppingListItemBinding;
import com.example.recipeass2.model.ShoppingItem;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingItemViewHolder> {

    private List<ShoppingItem> shoppingList;
    private AppDatabase db;

    public ShoppingListAdapter(List<ShoppingItem> shoppingList, AppDatabase db) {
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
        ShoppingItem item = shoppingList.get(position);
        holder.binding.itemName.setText(item.getName());
        holder.binding.itemQuantity.setText(String.valueOf(item.getQuantity()));

        holder.binding.buttonDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = item.getQuantity();
                if (currentQuantity > 0) {
                    currentQuantity--;
                    item.setQuantity(currentQuantity);
                    holder.binding.itemQuantity.setText(String.valueOf(currentQuantity));
                    updateQuantity(item.getId(), currentQuantity);
                }
            }
        });

        holder.binding.buttonIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = item.getQuantity();
                currentQuantity++;
                item.setQuantity(currentQuantity);
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

    private void updateQuantity(int itemId, int newQuantity) {
        db.shoppingItemDao().updateQuantity(itemId, newQuantity);
    }
}
//package com.example.recipeass2;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingItemViewHolder> {
//
//    private List<ShoppingItem> shoppingList;
//    private AppDatabase db;
//
//    public ShoppingListAdapter(List<ShoppingItem> shoppingList, AppDatabase db) {
//        this.shoppingList = shoppingList;
//        this.db = db;
//    }
//
//    @NonNull
//    @Override
//    public ShoppingItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        View view = inflater.inflate(R.layout.shopping_list_item, parent, false);
//        return new ShoppingItemViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ShoppingItemViewHolder holder, int position) {
//        ShoppingItem item = shoppingList.get(position);
//        holder.itemName.setText(item.getName());
//        holder.itemQuantity.setText(String.valueOf(item.getQuantity()));
//
//        holder.buttonDecrease.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int currentQuantity = item.getQuantity();
//                if (currentQuantity > 0) {
//                    currentQuantity--;
//                    item.setQuantity(currentQuantity);
//                    holder.itemQuantity.setText(String.valueOf(currentQuantity));
//                    updateQuantity(item.getId(), currentQuantity);
//                }
//            }
//        });
//
//        holder.buttonIncrease.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int currentQuantity = item.getQuantity();
//                currentQuantity++;
//                item.setQuantity(currentQuantity);
//                holder.itemQuantity.setText(String.valueOf(currentQuantity));
//                updateQuantity(item.getId(), currentQuantity);
//            }
//        });    }
//
//    @Override
//    public int getItemCount() {
//        return shoppingList.size();
//    }
//
//    static class ShoppingItemViewHolder extends RecyclerView.ViewHolder {
//
//        TextView itemName;
//        TextView itemQuantity;
//        Button buttonDecrease;
//        Button buttonIncrease;
//
//        public ShoppingItemViewHolder(@NonNull View itemView) {
//            super(itemView);
//            itemName = itemView.findViewById(R.id.item_name);
//            itemQuantity = itemView.findViewById(R.id.item_quantity);
//            buttonDecrease = itemView.findViewById(R.id.button_decrease);
//            buttonIncrease = itemView.findViewById(R.id.button_increase);
//        }
//    }
//
//    private void updateQuantity(int itemId, int newQuantity) {
//        db.shoppingItemDao().updateQuantity(itemId, newQuantity);
//    }
//}
