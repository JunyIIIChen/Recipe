package com.example.recipeass2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.recipeass2.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        /**
         * @Author： Junyi Chen
         * Create SP to store registered account and password
         */


        binding.registerSubmitButton.setOnClickListener(view1 ->{
            String account = binding.registerAccount.getText().toString();
            String password = binding.registerPassword.getText().toString();
            String confirmedPassword = binding.confirmedPassword.getText().toString();

            //!account.isEmpty() && !password.isEmpty() &&!confirmedPassword.isEmpty() &&
            if (password.equals(confirmedPassword)){
                SharedPreferences sharedPreferences = getSharedPreferences("myPreferences", MODE_PRIVATE);
                SharedPreferences.Editor spEditor = sharedPreferences.edit();
                spEditor.putString("account", account);
                spEditor.putString("password", password);
                spEditor.apply();

                String userName = sharedPreferences.getString("account", "");
                String userPassword = sharedPreferences.getString("password", "");
                TextView textView = binding.textView;
                textView.setText(userName);
            }



        });
    }
}