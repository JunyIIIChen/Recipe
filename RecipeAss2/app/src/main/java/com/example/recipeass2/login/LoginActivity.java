package com.example.recipeass2.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.ui.AppBarConfiguration;

import com.example.recipeass2.MainActivity;
import com.example.recipeass2.R;
import com.example.recipeass2.databinding.ActivityLoginBinding;

import com.example.recipeass2.fragment.HomeFragment;
import com.example.recipeass2.search.SearchRecipeActivity;
import com.example.recipeass2.signup.SignupActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;

    private ActivityLoginBinding binding;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


FirebaseApp.initializeApp(this);



        auth = FirebaseAuth.getInstance();
        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        Button registerButton = findViewById(R.id.signupButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  startActivity(new Intent(LoginActivity.this,
                                                          SignupActivity.class));
                                              }
                                          }
        );
        Button loginButton = findViewById(R.id.signInButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_Email = emailEditText.getText().toString();
                String txt_Pwd = passwordEditText.getText().toString();
                loginUser(txt_Email, txt_Pwd);
            }
        });
    }

    private void loginUser(String txt_email, String txt_pwd) {
// call the object and provide it with email id and password
        auth.signInWithEmailAndPassword(txt_email, txt_pwd)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        String msg = "Login Successful";
                        toastMsg(msg);
                        startActivity(new Intent(LoginActivity.this, SearchRecipeActivity.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String errorMsg = "Login Failed: " + e.getMessage();
                        toastMsg(errorMsg);
                    }

        });
    }

    public void toastMsg(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
