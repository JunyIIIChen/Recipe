package com.example.recipeass2.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.ui.AppBarConfiguration;

import com.example.recipeass2.MainActivity;
import com.example.recipeass2.R;
import com.example.recipeass2.database.AppDatabase;
import com.example.recipeass2.databinding.ActivityLoginBinding;

import com.example.recipeass2.signup.SignupActivity;
import com.example.recipeass2.user.User;
import com.example.recipeass2.user.UserDao;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executors;

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
        if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_pwd)) {
            toastMsg("Please enter both email and password");
            return;
        }

        // Get the UserDao
        UserDao userDao = AppDatabase.getDatabase(getApplicationContext()).userDao();

        // Get the Firebase instance
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        String pathEmail = txt_email.replace('.', ','); // Replace '.' with ','

        // Query the user from Firebase Database
        myRef.child(pathEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // The user exists in Firebase Database, get the user data
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        // Insert the user data into Room database
                        Executors.newSingleThreadExecutor().execute(() -> {
                            userDao.insertOrUpdate(user);
                        });
                    }
                }

                // Proceed the Firebase Authentication
                auth.signInWithEmailAndPassword(txt_email, txt_pwd)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                String msg = "Login Successful";
                                toastMsg(msg);

                                // Save user email
                                SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("email", txt_email);
                                editor.apply();

                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Log the error
                Log.e("Firebase", "Failed to read user data", error.toException());

                // Show a toast message to the user
                toastMsg("Failed to read user data: " + error.getMessage());
            }
        });
}

    private void toastMsg(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
