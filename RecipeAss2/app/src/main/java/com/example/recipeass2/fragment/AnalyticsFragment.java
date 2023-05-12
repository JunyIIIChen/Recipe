package com.example.recipeass2.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.example.recipeass2.databinding.AnalyticsFragmentBinding;
import com.example.recipeass2.viewmodel.SharedViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class AnalyticsFragment extends Fragment {
    private AnalyticsFragmentBinding binding;
    private PieChart pieChart;

    private final String CURRENT_SCREEN_NAME = "Data Report";

    public AnalyticsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment using the binding
        binding = AnalyticsFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //set current screen name to the top nav bar
        binding.topBar.currentScreenName.setText(CURRENT_SCREEN_NAME);

        pieChart = binding.pieChart;
        setPieChart();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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