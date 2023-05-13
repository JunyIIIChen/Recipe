package com.example.recipeass2.recipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.recipeass2.MainActivity;
import com.example.recipeass2.R;
import com.example.recipeass2.database.AppDatabase;
import com.example.recipeass2.databinding.ActivityRecipeBinding;
import com.example.recipeass2.facebook.ShareFacebookActivity;
import com.example.recipeass2.facebook.TempPhoto;
import com.example.recipeass2.search.Recipe;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.bumptech.glide.Glide;
import com.example.recipeass2.search.SpoonacularApiService;
import com.example.recipeass2.shoppingList.ShoppingListItem;
import com.example.recipeass2.user.FavoriteRecipe;
import com.example.recipeass2.user.UserDao;
import com.example.recipeass2.user.UserFavoriteRecipeCrossRef;

public class RecipeActivity extends AppCompatActivity {
    private Recipe recipe;
    private ActivityRecipeBinding binding;
    private RecipeViewModel recipeViewModel;
    private IngredientAdapter ingredientAdapter;

    private UserDao userDao;

    private static final String API_KEY = "c92ff870e8ae441ba53380608f13ed3c";
    private static final String BASE_URL = "https://api.spoonacular.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize userDao
        userDao = AppDatabase.getDatabase(getApplicationContext()).userDao();
        binding.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeRecipe();
            }
        });

        // Handle back button click
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.shareToFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TempPhoto.image = createBitmap(getWindow().getDecorView());
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                image.compress(Bitmap.CompressFormat.PNG, 10, stream);
//                byte[] byteArray = stream.toByteArray();
                Intent intent = new Intent(RecipeActivity.this, ShareFacebookActivity.class);
//                intent.putExtra("share screen", byteArray);
//                try {
//                    stream.close();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
                startActivity(intent);
            }
        });

        ingredientAdapter = new IngredientAdapter(new ArrayList<>());
        binding.ingredientList.setAdapter(ingredientAdapter);
        binding.ingredientList.setLayoutManager(new LinearLayoutManager(this));

        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        binding.addToShoppingListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToShoppingList();
            }
        });

        int recipeId = getIntent().getIntExtra("recipe_id", -1);
        if (recipeId != -1) {
            fetchRecipeData(recipeId);
        } else {
            return;
            // Handle the error when there is no valid recipeId
        }
    }

    private void fetchRecipeData(int recipeId) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SpoonacularApiService spoonacularApiService = retrofit.create(SpoonacularApiService.class);
        Call<Recipe> call = spoonacularApiService.getRecipeInformation(recipeId, API_KEY);

        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if (response.isSuccessful()) {
                    recipe = response.body();
                    displayRecipeData();
                } else {
                    Log.e("RecipeActivity", "API Response not successful, code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                Log.e("RecipeActivity", "API call failed: " + t.getMessage());
                Toast.makeText(RecipeActivity.this, "Failed to load recipe data. Please check your internet connection.", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void displayRecipeData() {
        // Set recipe title
        binding.recipeName.setText(recipe.getTitle());
        Glide.with(this)
                .load(recipe.getImageUrl())
                .error(R.drawable.error_placeholder)
                .into(binding.recipeImage);

        if (recipe.getIngredients() != null) {
            ingredientAdapter.setIngredientList(recipe.getIngredients());
        }
    }
    private void addToShoppingList() {
        if (recipe != null && recipe.getIngredients() != null) {
            for (Ingredient ingredient : recipe.getIngredients()) {
                ShoppingListItem shoppingListItem = new ShoppingListItem();
                shoppingListItem.setName(ingredient.getName());
                shoppingListItem.setUnit(ingredient.getUnit());
                shoppingListItem.setAmount(ingredient.getAmount());
                recipeViewModel.insertShoppingListItem(shoppingListItem);
            }
            Toast.makeText(this, "Ingredients added to shopping list.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to add ingredients to shopping list.", Toast.LENGTH_SHORT).show();
        }
    }

    private void likeRecipe() {
        if (recipe != null) {
            // Get user email
            SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
            String userEmail = sharedPreferences.getString("email", null);
            if (userEmail == null) {
                Toast.makeText(this, "User not logged in.", Toast.LENGTH_SHORT).show();
                return;
            }
            FavoriteRecipe favoriteRecipe = new FavoriteRecipe(recipe.getTitle(), recipe.getDishTypes().get(0), System.currentTimeMillis());
            Executors.newSingleThreadExecutor().execute(() -> {
                long id = userDao.insert(favoriteRecipe);
                UserFavoriteRecipeCrossRef crossRef = new UserFavoriteRecipeCrossRef(userEmail, (int) id);
                userDao.insert(crossRef);
            });
        } else {
            Toast.makeText(this, "Failed to like the recipe.", Toast.LENGTH_SHORT).show();
        }
    }
    private Bitmap createBitmap(View view) {
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }
}
