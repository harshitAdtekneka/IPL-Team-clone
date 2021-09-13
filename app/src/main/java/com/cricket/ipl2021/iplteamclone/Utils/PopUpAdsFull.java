package com.cricket.ipl2021.iplteamclone.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class PopUpAdsFull {
    private static final String TAG = "PopUps-->";

    public static void showInterstitialAds(MyApplication myApplication, Context context, IntentOnClickListener intentOnClickListener, String type, int pos) {

        ProgressDialog progressDialog = new ProgressDialog((AppCompatActivity) context);
        progressDialog.setMessage("Advertise Loading...");
        progressDialog.setCancelable(false);

        if (myApplication.getBooleanData(Constants.Pref_IsInterstitial)) {
            Constants.AD_COUNT += 1;
            if (Constants.AD_COUNT == myApplication.getIntData(Constants.Pref_InterstitialPos)) {
                progressDialog.show();
                if (myApplication.getStringData(Constants.Pref_adType).equals(Constants.ADMOB)) {
                    Constants.AD_COUNT = 0;
                    MobileAds.initialize(context, new OnInitializationCompleteListener() {
                        @Override
                        public void onInitializationComplete(InitializationStatus initializationStatus) {
                        }
                    });

                    AdRequest adRequest = new AdRequest.Builder().build();

                    InterstitialAd.load(context, myApplication.getStringData(Constants.Pref_InterstitialId), adRequest, new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {

                            interstitialAd.show((Activity) context);
                            progressDialog.dismiss();

                            interstitialAd.setFullScreenContentCallback(
                                    new FullScreenContentCallback() {
                                        @Override
                                        public void onAdDismissedFullScreenContent() {

                                            intentOnClickListener.onIntentClick(type);
                                            intentOnClickListener.onPosClick(pos);

                                        }

                                        @Override
                                        public void onAdShowedFullScreenContent() {
                                            // Called when fullscreen content is shown.
                                        }
                                    });
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            progressDialog.dismiss();
                            intentOnClickListener.onIntentClick(type);
                            intentOnClickListener.onPosClick(pos);
                        }
                    });
                }
            } else {
                intentOnClickListener.onIntentClick(type);
                intentOnClickListener.onPosClick(pos);
            }
        } else {
            intentOnClickListener.onIntentClick(type);
            intentOnClickListener.onPosClick(pos);
        }
    }
}
