package com.example.recipeass2.database;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.recipeass2.user.User;
import com.example.recipeass2.user.UserDao;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class UploadWorker extends Worker {
    public UploadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Get the UserDao
        UserDao userDao = AppDatabase.getDatabase(getApplicationContext()).userDao();

        // Get the user data
        List<User> users = userDao.getAllUsers(); // Modify this line according to your UserDao methods

        // Get the Firebase instance
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        // Add or update user data
        for (User user : users) {
            myRef.child(user.getEmail()).setValue(user); // Modify this line according to your User entity
        }

        return Result.success();
    }
}
