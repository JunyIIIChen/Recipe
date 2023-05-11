package com.example.recipeass2.map;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.recipeass2.R;

import java.util.ArrayList;
import java.util.List;

public class NearShopListAdapter extends RecyclerView.Adapter<NearShopListAdapter.ShopViewHolder> {
    private List<Supermarket> supermarkets;

    public NearShopListAdapter() {
        this.supermarkets = new ArrayList<>();
    }

    public void updateData(List<Supermarket> newData) {
        supermarkets.clear();
        supermarkets.addAll(newData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.near_shop_item_shop, parent, false);
        return new ShopViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
        Supermarket supermarket = supermarkets.get(position);
        holder.bind(supermarket);
    }

    @Override
    public int getItemCount() {
        return supermarkets.size();
    }

    public class ShopViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView addressTextView;
        private TextView phoneTextView;

        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            addressTextView = itemView.findViewById(R.id.addressTextView);
            phoneTextView = itemView.findViewById(R.id.phoneTextView);
        }

        public void bind(Supermarket supermarket) {
            nameTextView.setText(supermarket.getName());
            addressTextView.setText(supermarket.getAddress());
            phoneTextView.setText(supermarket.getPhone());
        }
    }
}


