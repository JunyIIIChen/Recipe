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


public class AnalyticsFragment extends Fragment {
    private AnalyticsFragmentBinding binding;
    private PieChart pieChart;
    private UserDao userDao;
    private List<FavoriteRecipe> favoriteRecipes;
    private String userEmail;
    private Spinner chartTypeSpinner;
    private Spinner dateSpinner;

    private Calendar calendar;
    private Date selectedDate;


    private BarChart barChart;

    private final String CURRENT_SCREEN_NAME = "Data Report";

    private List<FavoriteRecipe> filteredRecipes;

    public AnalyticsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment using the binding
        binding = AnalyticsFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        chartTypeSpinner = binding.chartTypeSpinner;
        setUpChartTypeSpinner();

        dateSpinner = binding.dateSpinner;
        setDateSpinner();


        // Get a reference to the UserDao
        userDao = AppDatabase.getDatabase(getContext()).userDao();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        userEmail = sharedPreferences.getString("email", "");



        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                UserWithFavoriteRecipes userWithFavoriteRecipes = userDao.getUserWithFavoriteRecipesDirect(userEmail);
                favoriteRecipes = userWithFavoriteRecipes.getFavoriteRecipes();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    updateCharts();
                    }
                });
            }
        });



        //set current screen name to the top nav bar
        binding.topBar.currentScreenName.setText(CURRENT_SCREEN_NAME);

        pieChart = binding.pieChart;
        barChart = binding.barChart;


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

//    private void setPieChart(){
//        // Ensure favoriteRecipes is not null before attempting to iterate over it
//        if (favoriteRecipes != null) {
//            Map<String, Integer> typeCountMap = new HashMap<>();
//            for (FavoriteRecipe recipe : favoriteRecipes) {
//                String type = recipe.getType();
//                int count = typeCountMap.getOrDefault(type, 0);
//                typeCountMap.put(type, count + 1);
//            }
//            // Create a map to count the number of each type of recipe
//            Map<String, Integer> recipeTypeCounts = new HashMap<>();
//            for (FavoriteRecipe recipe : favoriteRecipes) {
//                String type = recipe.getType();
//                int count = recipeTypeCounts.containsKey(type) ? recipeTypeCounts.get(type) : 0;
//                recipeTypeCounts.put(type, count + 1);
//            }
//
//            // Create the pie chart entries
//            List<PieEntry> pieEntries = new ArrayList<>();
//            for (Map.Entry<String, Integer> entry : recipeTypeCounts.entrySet()) {
//                pieEntries.add(new PieEntry(entry.getValue(), entry.getKey()));
//            }
//
//            PieDataSet pieDataSet = new PieDataSet(pieEntries, "Pie Chart");
//            PieData pieData = new PieData(pieDataSet);
//
//            // Increase the text size for labels
//            pieDataSet.setValueTextSize(16f);
//            // Increase the text size for legend
//            pieChart.getLegend().setTextSize(16f);
//            //set Colour
//            pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
//
//            pieChart.setData(pieData);
//            pieChart.invalidate();
//        } else {
//            Log.d("AnalyticsFragment", "favoriteRecipes is null");
//        }
//    }

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

            pieChart.setData(pieData);
            pieChart.invalidate();
        } else {
            Log.d("AnalyticsFragment", "favoriteRecipes is null");
        }
    }

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

            barChart.setData(barData);
            barChart.invalidate();
        } else {
            Log.d("AnalyticsFragment", "favoriteRecipes is null");
        }
    }

    private void setDateSpinner(){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.time_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        dateSpinner.setAdapter(adapter);

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
                updateCharts();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                binding.textView3.setText("");
            }
        });

    }

    private void updateCharts() {
        if (selectedDate != null && favoriteRecipes != null) {
            List<FavoriteRecipe> filteredRecipes = new ArrayList<>();
            for (FavoriteRecipe recipe : favoriteRecipes) {
                if (recipe.getLikeTimestamp() >= selectedDate.getTime()) {
                    filteredRecipes.add(recipe);
                }
            }

            setPieChart(filteredRecipes);
            setBarChart(filteredRecipes);
        }
    }





    private void setUpChartTypeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.chart_types, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        chartTypeSpinner.setAdapter(adapter);

        chartTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        pieChart.setVisibility(View.VISIBLE);
                        barChart.setVisibility(View.GONE);
                        setPieChart(favoriteRecipes);
                        break;
                    case 1:
                        pieChart.setVisibility(View.GONE);
                        barChart.setVisibility(View.VISIBLE);
                        setBarChart(favoriteRecipes);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}