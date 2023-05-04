package com.example.recipeass2;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
//import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.recipeass2.databinding.ActivityHomeBinding;


//
public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // set toolbar
//        setSupportActionBar(binding.appBar.toolbar);
//
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home_fragment,
//                R.id.nav_add_fragment,
//                R.id.nav_view_fragment)
//                .setOpenableLayout(binding.drawerLayout)
//                .build();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        NavHostFragment navHostFragment = (NavHostFragment)
//                fragmentManager.findFragmentById(R.id.nav_host_fragment);
//        NavController navController = navHostFragment.getNavController();
//        //Sets up a NavigationView for use with a NavController.
//        NavigationUI.setupWithNavController(binding.navView, navController);
//        //Sets up a Toolbar for use with a NavController.
//        NavigationUI.setupWithNavController(binding.appBar.toolbar, navController,
//                mAppBarConfiguration);



    }
}