package com.example.recipeass2;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.recipeass2.database.UploadWorker;
import com.example.recipeass2.databinding.ActivityMainBinding;
import com.example.recipeass2.search.Recipe;
import com.example.recipeass2.search.RecipeSearchResponse;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        setSupportActionBar(binding.appBar.toolbar);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home_fragment,
                R.id.nav_search_recipe_fragment,
                R.id.nav_shopping_list_fragment,
                R.id.nav_analytics_fragment,
                R.id.activity_login)
//to display the Navigation button as a drawer symbol,not being shown as an Up
//        button
                .setOpenableLayout(binding.drawerLayout)
                .build();
        FragmentManager fragmentManager= getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment)
                fragmentManager.findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        //Sets up a NavigationView for use with a NavController.
        NavigationUI.setupWithNavController(binding.navView, navController);
        //Sets up a Toolbar for use with a NavController.
        NavigationUI.setupWithNavController(binding.appBar.toolbar,navController,
                mAppBarConfiguration);

        // work manager
        // Get now time
        Calendar now = Calendar.getInstance();

        // Get target time 10:00
        Calendar tenOClock = Calendar.getInstance();
        tenOClock.set(Calendar.HOUR_OF_DAY, 10);
        tenOClock.set(Calendar.MINUTE, 0);
        tenOClock.set(Calendar.SECOND, 0);

        if (now.after(tenOClock)) {
            tenOClock.add(Calendar.DATE, 1);
        }

        // Calculate time to 10:00
        long delay = tenOClock.getTimeInMillis() - now.getTimeInMillis();

        // Set Constraints
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        // Create and set PeriodicWorkRequest
        PeriodicWorkRequest uploadWorkRequest =
                new PeriodicWorkRequest.Builder(UploadWorker.class, 24, TimeUnit.HOURS)
                        .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                        .setConstraints(constraints)
                        .build();

        // Add to WorkManager
        WorkManager.getInstance(getApplicationContext()).enqueue(uploadWorkRequest);
    }
}