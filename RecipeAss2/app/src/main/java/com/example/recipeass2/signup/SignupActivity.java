package com.example.recipeass2.signup;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.recipeass2.MainActivity;
import com.example.recipeass2.R;
import com.example.recipeass2.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//
public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private ActivitySignupBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        auth = FirebaseAuth.getInstance();
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_txt = binding.emailEditText.getText().toString();
                String password_txt =
                        binding.passwordEditText.getText().toString();
                String checkpassword_txt = binding.checkPassword
                       .getText().toString();
                String msg = "";
                if (TextUtils.isEmpty(email_txt) ||
                        TextUtils.isEmpty(password_txt)) {
                    msg = "Empty Username or Password";
                } else if (password_txt.length() < 6) {
                     msg = "Password is too short";
                }else if(!password_txt.equals(checkpassword_txt)){
                     msg = "password should be same";
                } else
                    registerUser(email_txt, password_txt);
                toastMsg(msg);
            }
        });

      //  Button recipeButton=findViewById(R.id.recipeButton);
//        recipeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SignupActivity.this,
//                        RecipeActivity.class));
//            }
//        });
    }
    private void registerUser(String email_txt, String password_txt) {
// To create username and password
        auth.createUserWithEmailAndPassword(email_txt,password_txt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    String msg = "Registration Successful";
                    startActivity(new Intent(SignupActivity.this,
                            MainActivity.class));
                }else {
                    String msg = "Registration Unsuccessful";
                    toastMsg(msg);
                }
            }
        });
    }
    public void toastMsg(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}