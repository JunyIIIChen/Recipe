package com.example.recipeass2.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.recipeass2.database.UploadWorker;
import com.example.recipeass2.databinding.HomeFragmentBinding;
import com.example.recipeass2.viewmodel.SharedViewModel;

public class HomeFragment extends Fragment {
    private SharedViewModel model;
    private HomeFragmentBinding addBinding;
    public HomeFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        addBinding = HomeFragmentBinding.inflate(inflater, container, false);
        View view = addBinding.getRoot();

        // Set up the upload button click handler
        addBinding.triggerUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                triggerUpload();
            }
        });

        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        addBinding = null;
    }

    public void triggerUpload() {
        OneTimeWorkRequest uploadWorkRequest = new OneTimeWorkRequest.Builder(UploadWorker.class).build();
        WorkManager.getInstance(requireContext()).enqueue(uploadWorkRequest);
    }
}