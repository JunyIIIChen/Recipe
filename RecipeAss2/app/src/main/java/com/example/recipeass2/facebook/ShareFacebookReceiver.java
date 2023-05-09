package com.example.recipeass2.facebook;


import android.os.Bundle;
import android.util.Log;

import com.facebook.FacebookBroadcastReceiver;

/**
 * This is a simple example to demonstrate how an app could extend FacebookBroadcastReceiver to
 * handle notifications that long-running operations such as photo uploads have finished.
 */
public class ShareFacebookReceiver extends FacebookBroadcastReceiver {
    @Override
    public void onSuccessfulAppCall(String appCallId, String action, Bundle extras) {
        // A real app could update UI or notify the user that their photo was uploaded.
        Log.d("HelloFacebook", "Photo uploaded by call $appCallId succeeded.");
    }

    @Override
    public void onFailedAppCall(String appCallId, String action, Bundle extras) {
        // A real app could update UI or notify the user that their photo was not uploaded.
        Log.d("HelloFacebook", "Photo uploaded by call $appCallId failed.");
    }
}