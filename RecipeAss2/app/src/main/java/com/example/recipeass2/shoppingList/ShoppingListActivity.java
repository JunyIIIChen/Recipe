package com.example.recipeass2.shoppingList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.recipeass2.R;
import com.example.recipeass2.database.AppDatabase;
import com.example.recipeass2.databinding.ActivityShoppingListBinding;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {
    private ActivityShoppingListBinding binding;
    private ShoppingListAdapter adapter;
    private RecyclerView recyclerView;
    private List<ShoppingListItem> shoppingList;
    private AppDatabase db;
    private ShoppingListViewModel shoppingListViewModel;

    //current screen name
    private final String CURRENT_SCREEN_NAME = "Shopping list";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityShoppingListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get the current user's email
        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("email", "");

        // Initialize ViewModel
        shoppingListViewModel = new ViewModelProvider(this).get(ShoppingListViewModel.class);

        db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "shopping_list_database")
                        .fallbackToDestructiveMigration()
                        .build();

        // Initialize ViewModel
        shoppingListViewModel = new ViewModelProvider(this).get(ShoppingListViewModel.class);

        // Set current screen name
        binding.topBar.currentScreenName.setText(CURRENT_SCREEN_NAME);

        binding.shopListRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShoppingListAdapter(new ArrayList<>(), db);
        binding.shopListRecyclerview.setAdapter(adapter);

        // Observe shopping list items for the current user
        shoppingListViewModel.getShoppingListItemsForUser(userEmail).observe(this, new Observer<List<ShoppingListItem>>() {
            @Override
            public void onChanged(List<ShoppingListItem> shoppingListItems) {
                adapter.notifyDataSetChanged();
                adapter.setShoppingList(shoppingListItems);
            }
        });
        binding.clearShopListButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clearShopList();
                    }
                });
    }

    private void clearShopList(){
        binding.clearShopListButton.setOnClickListener(view -> {
            shoppingListViewModel.deleteAllShoppingListItems();
            Toast.makeText(this, "Shopping list cleared.", Toast.LENGTH_SHORT).show();
        });
    }
}