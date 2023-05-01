package com.example.recipeass2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.recipeass2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

private ActivityMainBinding binding;



// coooll
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        // Get a reference to the root view
        View view = binding.getRoot();
        // The root view needs to be passed as an input to setContentView()
        setContentView(view);
        // setContentView(R.layout.activity_main);


        // OnClickListener to the registration screen
        binding.registerButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            MainActivity.this.startActivity(intent);
        });
    }
}