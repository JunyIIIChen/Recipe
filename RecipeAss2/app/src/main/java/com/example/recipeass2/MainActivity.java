package com.example.recipeass2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.recipeass2.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private ActivityMainBinding binding;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        // login test code
//        binding.signInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,
//                        HomeActivity.class);
//                startActivity(intent);
//        }});


        //FirebaseApp.initializeApp(this);

        // for test reason I annotate this part of code. Chen

        auth = FirebaseAuth.getInstance();
        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        Button registerButton =findViewById(R.id.signupButton);
        registerButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,
                SignupActivity.class))
        );
        Button loginButton =findViewById(R.id.signInButton);
        loginButton.setOnClickListener(v -> {
            String txt_Email = emailEditText.getText().toString();
            String txt_Pwd = passwordEditText.getText().toString();
            loginUser(txt_Email,txt_Pwd);
        });
    }
    private void loginUser(String txt_email, String txt_pwd) {
// call the object and provide it with email id and password
        auth.signInWithEmailAndPassword(txt_email,
                txt_pwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                String msg = "Login Successful";
                toastMsg(msg);
                startActivity(new Intent(MainActivity.this,
                        HomeActivity.class));
            }
        });
    }
    public void toastMsg(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}