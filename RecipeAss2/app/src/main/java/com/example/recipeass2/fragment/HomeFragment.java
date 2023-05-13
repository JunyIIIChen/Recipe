package com.example.recipeass2.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.recipeass2.R;
import com.example.recipeass2.workManager.UploadWorker;
import com.example.recipeass2.databinding.HomeFragmentBinding;
import com.example.recipeass2.home.HomeRecipeAdapter;
import com.example.recipeass2.home.RecipeRandomSearchResponse;
import com.example.recipeass2.search.Recipe;
import com.example.recipeass2.search.SpoonacularApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private static final String API_KEY = "2f820b3f3e334125ba9b5d7e192dcd24";
    // 2f820b3f3e334125ba9b5d7e192dcd24
    // c92ff870e8ae441ba53380608f13ed3c
    private HomeFragmentBinding binding;
    private HomeRecipeAdapter homeRecipeAdapter;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        binding = HomeFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Set up the RecyclerView
        homeRecipeAdapter = new HomeRecipeAdapter(new ArrayList<>());
        binding.recipeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recipeRecyclerView.setAdapter(homeRecipeAdapter);

        // Set up the upload button click handler
        binding.triggerUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                triggerUpload();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spoonacular.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SpoonacularApiService spoonacularApiService = retrofit.create(SpoonacularApiService.class);

        Call<RecipeRandomSearchResponse> callAsync = spoonacularApiService.customSearch(API_KEY,  10);
        //makes an async request & invokes callback methods when the response returns
        callAsync.enqueue(new Callback<RecipeRandomSearchResponse>() {
            @Override
            public void onResponse(Call<RecipeRandomSearchResponse> call, Response<RecipeRandomSearchResponse> response) {
                if (response.isSuccessful()) {
                    List<Recipe> list = response.body().getRecipes();
                    homeRecipeAdapter.updateData(list);
                } else {
                    Log.i("Error ", "Response failed");
                }
            }

            @Override
            public void onFailure(Call<RecipeRandomSearchResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


    public void triggerUpload () {
        OneTimeWorkRequest uploadWorkRequest = new OneTimeWorkRequest.Builder(UploadWorker.class).build();
        WorkManager.getInstance(requireContext()).enqueue(uploadWorkRequest);
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        binding = null;
    }
}
