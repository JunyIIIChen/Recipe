package com.example.recipeass2.signup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.example.recipeass2.database.AppDatabase;
import com.example.recipeass2.databinding.ActivitySignupBinding;
import com.example.recipeass2.login.LoginActivity;
import com.example.recipeass2.user.Address;
import com.example.recipeass2.user.User;
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
                String password_txt = binding.passwordEditText.getText().toString();
                String checkpassword_txt = binding.checkPassword.getText().toString();
                // Get address fields
                String state = binding.stateEditText.getText().toString();
                String city = binding.cityEditText.getText().toString();
                String street = binding.streetEditText.getText().toString();
                String postcode = binding.postcodeEditText.getText().toString();

                String msg = "";
                if (TextUtils.isEmpty(email_txt) ||
                        TextUtils.isEmpty(password_txt)) {
                    msg = "Empty Username or Password";
                } else if (password_txt.length() < 6) {
                     msg = "Password is too short";
                }else if(!password_txt.equals(checkpassword_txt)){
                     msg = "password should be same";
                } else
                    registerUser(email_txt, password_txt, street, city, state, postcode);
                toastMsg(msg);
            }
        });
    }
    private void registerUser(String email_txt, String password_txt, String street, String city, String state, String postcode) {
// To create username and password
        auth.createUserWithEmailAndPassword(email_txt,password_txt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    // Create new address object
                    Address address = new Address(state, city, street, postcode);

                    // Create new user object
                    User newUser = new User(email_txt, password_txt, address);

                    // Store user data in Room database in a separate thread
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            AppDatabase.getDatabase(getApplicationContext()).userDao().insert(newUser);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                        }
                    }.execute();
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