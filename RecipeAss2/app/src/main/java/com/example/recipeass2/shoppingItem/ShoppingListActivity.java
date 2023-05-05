package com.example.recipeass2.shoppingItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;

import com.example.recipeass2.R;
import com.example.recipeass2.model.ShoppingItem;
import com.example.recipeass2.database.AppDatabase;
import com.example.recipeass2.databinding.ActivityShoppingListBinding;

import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {
    private ActivityShoppingListBinding binding;
    private ShoppingListAdapter adapter;
    private RecyclerView recyclerView;
//    private ShoppingListAdapter adapter;
    private List<ShoppingItem> shoppingList;

    private AppDatabase db;

    //current screen name
    private final String CURRENT_SCREEN_NAME = "Shopping list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityShoppingListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "shopping_list_database").allowMainThreadQueries().build();

        //set current screen name
        binding.topBar.currentScreenName.setText(CURRENT_SCREEN_NAME);
        loadData();

        binding.shopListRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShoppingListAdapter(shoppingList, db);
        binding.shopListRecyclerview.setAdapter(adapter);
        recyclerView = findViewById(R.id.shop_list_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new ShoppingListAdapter(shoppingList, db);
//        recyclerView.setAdapter(adapter);
    }


    private void goBackPrevScreen(){
        binding.topBar.goBackButton.setOnClickListener(view -> {
            /**
             *
             ********  TO DO *************
             *
             * **/
        });
    }

    private void clearShopList(){
        binding.clearShopListButton.setOnClickListener(view -> {
            /**
             *
             ********  TO DO *************
             *
             * **/
        });
    }

    private void createShopList(){
        binding.createShopListButton.setOnClickListener(view -> {
            /**
             *
             ********  TO DO *************
             *
             * **/
        });
    }

    private void loadData() {
        shoppingList = db.shoppingItemDao().getAllShoppingItems();
    }

//    private void updateQuantity(int itemId, int newQuantity) {
//        db.shoppingItemDao().updateQuantity(itemId, newQuantity);
//    }


}