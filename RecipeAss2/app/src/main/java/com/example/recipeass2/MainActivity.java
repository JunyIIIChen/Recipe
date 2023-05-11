package com.example.recipeass2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.recipeass2.signup.SignupActivity;
import com.example.recipeass2.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;

    private ActivityMainBinding binding;
    private AppBarConfiguration mAppBarConfiguration;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        View view = binding.getRoot();
//        setContentView(view);
//
//
//        setSupportActionBar(binding.appBar.toolbar);
//
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home_fragment,
//                R.id.nav_search_fragment,
//                R.id.nav_shopping_list_fragment,
//                R.id.nav_analytics_fragment,
//                R.id.nav_logout_fragment)
////to display the Navigation button as a drawer symbol,not being shown as an Up
////        button
//                .setOpenableLayout(binding.drawerLayout)
//                .build();
//        FragmentManager fragmentManager= getSupportFragmentManager();
//        NavHostFragment navHostFragment = (NavHostFragment)
//                fragmentManager.findFragmentById(R.id.nav_host_fragment);
//        NavController navController = navHostFragment.getNavController();
//        //Sets up a NavigationView for use with a NavController.
//        NavigationUI.setupWithNavController(binding.navView, navController);
//        //Sets up a Toolbar for use with a NavController.
//        NavigationUI.setupWithNavController(binding.appBar.toolbar,navController,
//                mAppBarConfiguration);
//    }









//
////FirebaseApp.initializeApp(this);
//        auth = FirebaseAuth.getInstance();
//        EditText emailEditText = findViewById(R.id.emailEditText);
//        EditText passwordEditText = findViewById(R.id.passwordEditText);
//        Button registerButton = findViewById(R.id.signupButton);
//        registerButton.setOnClickListener(new View.OnClickListener() {
//                                              @Override
//                                              public void onClick(View v) {
//                                                  startActivity(new Intent(MainActivity.this,
//                                                          SignupActivity.class));
//                                              }
//                                          }
//        );
//        Button loginButton = findViewById(R.id.signInButton);
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String txt_Email = emailEditText.getText().toString();
//                String txt_Pwd = passwordEditText.getText().toString();
//                loginUser(txt_Email, txt_Pwd);
//            }
//        });
//    }
//
//    private void loginUser(String txt_email, String txt_pwd) {
//// call the object and provide it with email id and password
//        auth.signInWithEmailAndPassword(txt_email, txt_pwd)
//                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                    @Override
//                    public void onSuccess(AuthResult authResult) {
//                        String msg = "Login Successful";
//                        toastMsg(msg);
//                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        String errorMsg = "Login Failed: " + e.getMessage();
//                        toastMsg(errorMsg);
//                    }
//
//        });
//    }
//
//    public void toastMsg(String message) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }