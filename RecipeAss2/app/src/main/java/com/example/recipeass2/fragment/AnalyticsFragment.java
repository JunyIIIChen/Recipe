package com.example.recipeass2.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Database;


import com.example.recipeass2.R;
import com.example.recipeass2.database.AppDatabase;
import com.example.recipeass2.databinding.AnalyticsFragmentBinding;
import com.example.recipeass2.user.FavoriteRecipe;
import com.example.recipeass2.user.User;
import com.example.recipeass2.user.UserDao;
import com.example.recipeass2.user.UserFavoriteRecipeCrossRef;
import com.example.recipeass2.user.UserWithFavoriteRecipes;
import com.example.recipeass2.viewmodel.SharedViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * AnalyticsFragment is a Fragment subclass that shows statistical data about a user's favorite recipes.
 * The statistics are represented as a Pie chart and a Bar chart.
 * This class uses DataBinding and communicates with the local database through the UserDao interface.
 */
public class AnalyticsFragment extends Fragment {
    // Binding object for this fragment
    private AnalyticsFragmentBinding binding;
    // Pie chart for displaying recipe data
    private PieChart pieChart;
    // UserDao for interacting with the app's database
    private UserDao userDao;
    // List of favorite recipes for the current user
    private List<FavoriteRecipe> favoriteRecipes;
    // Email of the currently logged in user
    private String userEmail;
    // Spinner for selecting the date range
    private Spinner dateSpinner;
    // Calendar object for date manipulation
    private Calendar calendar;
    // Selected date from the dateSpinner
    private Date selectedDate;
    // Bar chart for displaying recipe data
    private BarChart barChart;
    // Name of the current screen
    private final String CURRENT_SCREEN_NAME = "Data Report";
    // List of filtered recipes based on the selected date
    private List<FavoriteRecipe> filteredRecipes;

    public AnalyticsFragment(){}

    /**
     * This method is called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment using the binding
        binding = AnalyticsFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        dateSpinner = binding.dateSpinner;
        // Initialize dateSpinner with predefined date options
        setDateSpinner();

        // Get a reference to the UserDao
        userDao = AppDatabase.getDatabase(getContext()).userDao();

        // Get the user email from shared preferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        userEmail = sharedPreferences.getString("email", "");

        // Use ExecutorService to run database operations on a separate thread
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                UserWithFavoriteRecipes userWithFavoriteRecipes = userDao.getUserWithFavoriteRecipesDirect(userEmail);
                favoriteRecipes = userWithFavoriteRecipes.getFavoriteRecipes();
                // Update the charts on the UI thread
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateCharts();
                    }
                });
            }
        });

        // Set up the chart type radio group and its radio buttons
        RadioGroup chartTypeRadioGroup;
        RadioButton radioPieChart, radioBarChart;

        chartTypeRadioGroup = binding.chartTypeRadioGroup;
        radioPieChart = binding.radioPieChart;
        radioBarChart = binding.radioBarChart;

        // Change the visibility of the charts based on the selected radio button
        chartTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_pie_chart:
                        pieChart.setVisibility(View.VISIBLE);
                        barChart.setVisibility(View.GONE);
                        setPieChart(favoriteRecipes);
                        break;
                    case R.id.radio_bar_chart:
                        pieChart.setVisibility(View.GONE);
                        barChart.setVisibility(View.VISIBLE);
                        setBarChart(favoriteRecipes);
                        break;
                }
            }
        });


        //set current screen name to the top nav bar
        binding.topBar.currentScreenName.setText(CURRENT_SCREEN_NAME);
        // Get references to the charts from the binding
        pieChart = binding.pieChart;
        barChart = binding.barChart;


        return view;
    }

    /**
     * This method is called when the view previously created by onCreateView has been detached from the fragment.
     * The next time the fragment needs to be displayed, a new view will be created.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Clean up any references to the binding class to prevent memory leaks
        binding = null;
    }


    /**
     * This method sets the Pie chart with the favorite recipes data.
     * Each entry in the Pie chart represents a type of recipe and the number of times it occurs in the favorite recipes.
     */
    private void setPieChart(List<FavoriteRecipe> favoriteRecipes){
        // Ensure favoriteRecipes is not null before attempting to iterate over it
        if (favoriteRecipes != null) {
            // Create a map to count the number of each type of recipe
            Map<String, Integer> recipeTypeCounts = new HashMap<>();
            for (FavoriteRecipe recipe : favoriteRecipes) {
                String type = recipe.getType();
                int count = recipeTypeCounts.containsKey(type) ? recipeTypeCounts.get(type) : 0;
                recipeTypeCounts.put(type, count + 1);
            }

            // Create the pie chart entries
            List<PieEntry> pieEntries = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : recipeTypeCounts.entrySet()) {
                pieEntries.add(new PieEntry(entry.getValue(), entry.getKey()));
            }

            PieDataSet pieDataSet = new PieDataSet(pieEntries, "Pie Chart");
            PieData pieData = new PieData(pieDataSet);

            // Increase the text size for labels
            pieDataSet.setValueTextSize(16f);
            // Increase the text size for legend
            pieChart.getLegend().setTextSize(16f);
            //set Colour
            pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
            // Set the data for the pie chart and invalidate it to trigger a redraw

            pieChart.setData(pieData);
            pieChart.invalidate();
        } else {
            Log.d("AnalyticsFragment", "favoriteRecipes is null");
        }
    }

    /**
     * This method sets the Bar chart with the favorite recipes data.
     * Each entry in the Bar chart represents a type of recipe and the number of times it occurs in the favorite recipes.
     */
    private void setBarChart(List<FavoriteRecipe> favoriteRecipes) {
        if (favoriteRecipes != null) {
            Map<String, Float> typeCountMap = new HashMap<>();
            for (FavoriteRecipe recipe : favoriteRecipes) {
                String type = recipe.getType();
                float count = typeCountMap.getOrDefault(type, 0f);
                typeCountMap.put(type, count + 1);
            }

            List<BarEntry> entries = new ArrayList<>();
            int i = 0;
            for (Map.Entry<String, Float> entry : typeCountMap.entrySet()) {
                entries.add(new BarEntry(i++, entry.getValue(), entry.getKey()));
            }

            BarDataSet barDataSet = new BarDataSet(entries, "Types");
            BarData barData = new BarData(barDataSet);
            barData.setBarWidth(0.9f);
            // Set the data for the bar chart and invalidate it to trigger a redraw
            barChart.setData(barData);
            barChart.invalidate();
        } else {
            Log.d("AnalyticsFragment", "favoriteRecipes is null");
        }
    }

    /**
     * This method initializes the dateSpinner with predefined date options.
     * It sets the selectedDate based on the user's selection from the spinner.
     */
    private void setDateSpinner(){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.time_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        dateSpinner.setAdapter(adapter);
        // Set an onItemSelectedListener to the spinner to handle user's selection
        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                calendar = Calendar.getInstance();
                String selectedItem = adapterView.getItemAtPosition(i).toString();

                // Depending on the selected item, add the correct amount of time
                switch (selectedItem) {
                    case "1 day":
                        calendar.add(Calendar.DAY_OF_MONTH, -1);
                        break;
                    case "15 days":
                        calendar.add(Calendar.DAY_OF_MONTH, -15);
                        break;
                    case "1 month":
                        calendar.add(Calendar.MONTH, -1);
                        break;
                    case "Half year":
                        calendar.add(Calendar.MONTH, -6);
                        break;
                    case "1 year":
                        calendar.add(Calendar.YEAR, -1);
                        break;
                }

                // Get the Date object
                selectedDate = calendar.getTime();
                // Update the charts based on the selected date
                updateCharts();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                binding.textView3.setText("");
            }
        });

    }
    /**
     * This method updates the charts based on the selected date.
     * It filters the favoriteRecipes list to include only recipes liked after the selected date.
     */
    private void updateCharts() {
        if (selectedDate != null && favoriteRecipes != null) {
            List<FavoriteRecipe> filteredRecipes = new ArrayList<>();
            for (FavoriteRecipe recipe : favoriteRecipes) {
                if (recipe.getLikeTimestamp() >= selectedDate.getTime()) {
                    filteredRecipes.add(recipe);
                }
            }
            // Update the pie and bar charts with the filtered list
            setPieChart(filteredRecipes);
            setBarChart(filteredRecipes);
        }
    }
}