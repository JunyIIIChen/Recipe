package com.example.recipeass2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
//    private ShoppingListAdapter adapter;
    private List<ShoppingItem> shoppingList;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "shopping_list_database").allowMainThreadQueries().build();


        loadData();

        recyclerView = findViewById(R.id.shop_list_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new ShoppingListAdapter(shoppingList, db);
//        recyclerView.setAdapter(adapter);
    }

    private void loadData() {
        shoppingList = db.shoppingItemDao().getAllShoppingItems();
    }

//    private void updateQuantity(int itemId, int newQuantity) {
//        db.shoppingItemDao().updateQuantity(itemId, newQuantity);
//    }



}