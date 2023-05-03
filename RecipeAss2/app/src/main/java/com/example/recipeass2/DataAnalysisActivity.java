package com.example.recipeass2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;


import com.example.recipeass2.databinding.ActivityDataAnalysisBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class DataAnalysisActivity extends AppCompatActivity {

    private ActivityDataAnalysisBinding binding;

    private final String CURRENT_SCREEN_NAME = "Data Report";

    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivityDataAnalysisBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //set current screen name to the top nav bar
        binding.topBar.currentScreenName.setText(CURRENT_SCREEN_NAME);

        pieChart = binding.pieChart;
        setPieChart();


    }

    private void setPieChart(){
        /**
         * sample data to display piechart
         */
        //loading data
        List<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(30, "A"));
        pieEntries.add(new PieEntry(20, "B"));
        pieEntries.add(new PieEntry(50, "C"));

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Example Pie Chart");
        PieData pieData = new PieData(pieDataSet);

        // Increase the text size for labels
        pieDataSet.setValueTextSize(16f);
        // Increase the text size for legend
        pieChart.getLegend().setTextSize(16f);
        //set Colour
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}