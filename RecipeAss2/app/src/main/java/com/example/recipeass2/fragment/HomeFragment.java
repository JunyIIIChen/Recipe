package com.example.recipeass2.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.recipeass2.Retrofit.Recipes;
import com.example.recipeass2.Retrofit.RetrofitClient;
import com.example.recipeass2.Retrofit.RetrofitInterface;
import com.example.recipeass2.Retrofit.SearchResponse;
import com.example.recipeass2.database.UploadWorker;
import com.example.recipeass2.databinding.HomeFragmentBinding;
import com.example.recipeass2.viewmodel.SharedViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private static final String API_KEY = "c92ff870e8ae441ba53380608f13ed3c";
//    private static final String SEARCH_ID_cx = "8721745eed177489e";
    private String keyword;
    private RetrofitInterface retrofitInterface;

    private SharedViewModel model;
    private HomeFragmentBinding binding;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        binding = HomeFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        // Set up the upload button click handler
        binding.triggerUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                triggerUpload();
            }
        });

        retrofitInterface = RetrofitClient.getRetrofitService();



                Call<SearchResponse> callAsync =
                        retrofitInterface.customSearch(API_KEY);
                //makes an async request & invokes callback methods when the response returns
                callAsync.enqueue(new Callback<SearchResponse>() {
                    @Override
                    public void onResponse(Call<SearchResponse> call,
                                           Response<SearchResponse> response) {

                            if (response.isSuccessful()) {

                                List<Recipes> list = response.body().getRecipes();
                                //getting name from the object in the position 0
                                String result = list.get(0).getTitle();
                                String imageUrl = list.get(0).getImage();
                                binding.tvResult.setText(result);
                                // load image to imageview
                                Picasso.get().load(imageUrl).into(binding.imageView);


                            } else {
                            Log.i("Error ", "Response failed");
                        }
                    }

                    // MainActivity.this 这里的toast.makeText 报错了，改成了getContext 不一定对，有可能是要改成getfragment
                    @Override
                    public void onFailure(Call<SearchResponse> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT);
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