package com.example.recipeass2.Recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.recipeass2.databinding.ActivityCourtBinding;
import com.example.recipeass2.databinding.ActivityRecipeBinding;
import com.example.recipeass2.model.Item;

import java.util.ArrayList;
import java.util.List;

public class CourtActivity extends AppCompatActivity {

    private ActivityCourtBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCourtBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ArrayList<Item> courtItemList = (ArrayList<Item>) bundle.getSerializable("courtName");
        String txt = "";
        for(Item item: courtItemList) {
            txt += item.getItemName()+ "\n";

        }
        binding.addCourt.setText(txt);

    }
}