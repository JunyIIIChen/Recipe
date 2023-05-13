package com.example.recipeass2.workManager;

import android.app.Service;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class WorkManagerService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("WorkManagerService", "onStartCommand: " + getFormattedTimestamp());
        // Get now time
        Calendar now = Calendar.getInstance();

        // Get target time 10:00
        Calendar tenOClock = Calendar.getInstance();
        tenOClock.set(Calendar.HOUR_OF_DAY, 10);
        tenOClock.set(Calendar.MINUTE, 0);
        tenOClock.set(Calendar.SECOND, 0);

        if (now.after(tenOClock)) {
            tenOClock.add(Calendar.DATE, 1);
        }

        // Calculate time to 10:00
        long delay = tenOClock.getTimeInMillis() - now.getTimeInMillis();

        // Set Constraints
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        // Create and set PeriodicWorkRequest
        PeriodicWorkRequest uploadWorkRequest =
                new PeriodicWorkRequest.Builder(UploadWorker.class, 24, TimeUnit.HOURS)
                        .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                        .setConstraints(constraints)
                        .build();

        // Add to WorkManager
        WorkManager.getInstance(getApplicationContext()).enqueue(uploadWorkRequest);

        return START_STICKY;
    }

    private String getFormattedTimestamp() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return simpleDateFormat.format(new Date());
    }
}
