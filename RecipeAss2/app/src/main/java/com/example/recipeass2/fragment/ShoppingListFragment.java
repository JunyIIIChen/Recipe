package com.example.recipeass2.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;


import com.example.recipeass2.database.AppDatabase;
import com.example.recipeass2.databinding.ShoppingListFragmentBinding;
import com.example.recipeass2.shoppingList.ShoppingListAdapter;
import com.example.recipeass2.shoppingList.ShoppingListItem;
import com.example.recipeass2.shoppingList.ShoppingListViewModel;
import com.example.recipeass2.viewmodel.SharedViewModel;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListFragment extends Fragment {
    private ShoppingListFragmentBinding binding;
    private ShoppingListAdapter adapter;
    private List<ShoppingListItem> shoppingList;
    private AppDatabase db;
    private ShoppingListViewModel shoppingListViewModel;

    //current screen name
    private final String CURRENT_SCREEN_NAME = "Shopping list";

    private String userEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment using the binding
        binding = ShoppingListFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        db = Room.databaseBuilder(getActivity().getApplicationContext(),
                        AppDatabase.class, "shopping_list_database")
                .fallbackToDestructiveMigration()
                .build();

        // Get the current user's email
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        userEmail = sharedPreferences.getString("email", "");

        // Initialize ViewModel
        shoppingListViewModel = new ViewModelProvider(this).get(ShoppingListViewModel.class);

//        // Set current screen name
//        binding.topBar.currentScreenName.setText(CURRENT_SCREEN_NAME);

        binding.shopListRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ShoppingListAdapter(new ArrayList<>(), db);
        binding.shopListRecyclerview.setAdapter(adapter);

        // Observe shopping list items for the current user
        shoppingListViewModel.getShoppingListItemsForUser(userEmail).observe(getViewLifecycleOwner(), new Observer<List<ShoppingListItem>>() {
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

//        goBackPrevScreen();

        return view;
    }

//    private void goBackPrevScreen(){
//        binding.topBar.goBackButton.setOnClickListener(view -> {
//            // ...
//        });
//    }

    private void clearShopList() {
        binding.clearShopListButton.setOnClickListener(view -> {
            shoppingListViewModel.deleteAllShoppingListItemsForUser(userEmail);
            Toast.makeText(getActivity(), "Shopping list cleared.", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}