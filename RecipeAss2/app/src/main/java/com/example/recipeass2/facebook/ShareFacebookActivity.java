package com.example.recipeass2.facebook;



import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.recipeass2.MainActivity;
import com.example.recipeass2.databinding.ActivityShareFacebookBinding;
import com.example.recipeass2.recipe.RecipeActivity;
import com.facebook.*;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import com.example.recipeass2.R;
public class ShareFacebookActivity extends FragmentActivity {

    private static final String PERMISSION = "publish_actions";


    private final String PENDING_ACTION_BUNDLE_KEY = "com.example.hellofacebook:PendingAction";

    private ActivityShareFacebookBinding binding;

    private PendingAction pendingAction = PendingAction.NONE;
    private boolean canPresentShareDialog;
    private boolean canPresentShareDialogWithPhotos;
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    private ShareDialog shareDialog;

//    private Bitmap image;
    private FacebookCallback<Sharer.Result> shareCallback =
            new FacebookCallback<Sharer.Result>() {
                @Override
                public void onCancel() {
                    Log.d("HelloFacebook", "Canceled");
                }

                @Override
                public void onError(@NonNull FacebookException error) {
                    Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
                    String title = getString(R.string.error);
                    String alertMessage = error.getMessage();
                    showResult(title, alertMessage);
                }

                @Override
                public void onSuccess(@NonNull Sharer.Result result) {
                    Log.d("HelloFacebook", "Success!");
                    if (result.getPostId() != null) {
                        String title = getString(R.string.success);
                        String id = result.getPostId();
                        String alertMessage = getString(R.string.successfully_posted_post, id);
                        showResult(title, alertMessage);
                    }
                }

                private void showResult(String title, String alertMessage) {
                    new AlertDialog.Builder(ShareFacebookActivity.this)
                            .setTitle(title)
                            .setMessage(alertMessage)
                            .setPositiveButton(R.string.ok, null)
                            .show();
                }
            };

    private enum PendingAction {
        NONE,

        POST_STATUS_UPDATE
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add code to print out the key hash

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance()
                .registerCallback(
                        callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(@NonNull LoginResult loginResult) {
                                handlePendingAction();
                                updateUI();
                            }

                            @Override
                            public void onCancel() {
                                if (pendingAction != PendingAction.NONE) {
                                    showAlert();
                                    pendingAction = PendingAction.NONE;
                                }
                                updateUI();
                            }

                            @Override
                            public void onError(@NonNull FacebookException exception) {
                                if (pendingAction != PendingAction.NONE
                                        && exception instanceof FacebookAuthorizationException) {
                                    showAlert();
                                    pendingAction = PendingAction.NONE;
                                }
                                updateUI();
                            }

                            private void showAlert() {
                                new AlertDialog.Builder(ShareFacebookActivity.this)
                                        .setTitle(R.string.cancelled)
                                        .setMessage(R.string.permission_not_granted)
                                        .setPositiveButton(R.string.ok, null)
                                        .show();
                            }
                        });

        shareDialog = new ShareDialog(this);
        shareDialog.registerCallback(callbackManager, shareCallback);

        if (savedInstanceState != null) {
            String name = savedInstanceState.getString(PENDING_ACTION_BUNDLE_KEY);
            pendingAction = PendingAction.valueOf(name);
        }
        binding = ActivityShareFacebookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        profileTracker =
                new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                        updateUI();
                        // It's possible that we were waiting for Profile to be populated in order to
                        // post a status update.
                        handlePendingAction();
                    }
                };


        binding.postStatusUpdateButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        onClickPostStatusUpdate();
                    }
                });

        binding.returnButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        startActivity(new Intent(ShareFacebookActivity.this, MainActivity.class));
                    }
                });

        // Can we present the share dialog for regular links?
        canPresentShareDialog = ShareDialog.canShow(ShareLinkContent.class);

        // Can we present the share dialog for photos?
        canPresentShareDialogWithPhotos = ShareDialog.canShow(SharePhotoContent.class);
        //byte[] byteArray = getIntent().getByteArrayExtra("share screen");
        //image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        if(TempPhoto.image != null)
        {
            binding.shareImage.setImageBitmap(TempPhoto.image);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(PENDING_ACTION_BUNDLE_KEY, pendingAction.name());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        profileTracker.stopTracking();
        LoginManager.getInstance().unregisterCallback(callbackManager);
    }

    private void updateUI() {
        boolean enableButtons = AccessToken.isCurrentAccessTokenActive();

        binding.postStatusUpdateButton.setEnabled(enableButtons);

    }

    private void handlePendingAction() {
        PendingAction previouslyPendingAction = pendingAction;
        // These actions may re-set pendingAction if they are still pending, but we assume they
        // will succeed.
        pendingAction = PendingAction.NONE;

        switch (previouslyPendingAction) {
            case NONE:
                break;

            case POST_STATUS_UPDATE:
                postStatusUpdate();
                break;
        }
    }

    private void onClickPostStatusUpdate() {
        performPublish(PendingAction.POST_STATUS_UPDATE, canPresentShareDialog);
    }

    private void postStatusUpdate() {
        Profile profile = Profile.getCurrentProfile();
        if (TempPhoto.image != null) {
            SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap(TempPhoto.image)
                    .build();
            SharePhotoContent content = new SharePhotoContent.Builder()
                    .addPhoto(photo)
                    .build();
            if (canPresentShareDialog) {

                shareDialog.show(content);
            } else if (profile != null && hasPublishPermission()) {
                ShareApi.share(content, shareCallback);
            } else {
                pendingAction = PendingAction.POST_STATUS_UPDATE;
            }
        }
    }



    private boolean hasPublishPermission() {
        return AccessToken.isCurrentAccessTokenActive()
                && AccessToken.getCurrentAccessToken().getPermissions().contains("publish_actions");
    }

    private void performPublish(PendingAction action, boolean allowNoToken) {
        if (AccessToken.isCurrentAccessTokenActive() || allowNoToken) {
            pendingAction = action;
            handlePendingAction();
        }
    }
}