package com.example.recipeass2.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeass2.AppDatabase;
import com.example.recipeass2.R;
import com.example.recipeass2.ShoppingItem;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ItemViewHolder> {

    private List<Item> itemList;
    private List<Item> courtItemList;
    //private AppDatabase db;

    public IngredientAdapter(List<Item> itemList, List<Item> courtItemList) {
        this.itemList = itemList;
        this.courtItemList = courtItemList;
    }

    public List<Item> getCourtItemList() {
        return courtItemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.itemName.setText(item.getItemName());
        holder.buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courtItemList.add(item);
            }
        });

//        holder.buttonIncrease.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int currentQuantity = item.getQuantity();
//                currentQuantity++;
//                item.setQuantity(currentQuantity);
//                holder.itemQuantity.setText(String.valueOf(currentQuantity));
//                updateQuantity(item.getId(), currentQuantity);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView itemName;

        Button buttonAddItem;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name1);
            buttonAddItem = itemView.findViewById(R.id.button_add_item);
        }
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    public List<Item> getItemList() {
        return itemList;
    }
//    private void updateQuantity(int itemId, int newQuantity) {
//        db.shoppingItemDao().updateQuantity(itemId, newQuantity);
//    }
}
