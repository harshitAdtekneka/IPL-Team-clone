package com.cricket.ipl2021.iplteamclone.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cricket.ipl2021.iplteamclone.R;
import com.cricket.ipl2021.iplteamclone.Utils.BannerAds;
import com.cricket.ipl2021.iplteamclone.Utils.Constants;
import com.cricket.ipl2021.iplteamclone.Utils.IntentOnClickListener;
import com.cricket.ipl2021.iplteamclone.Utils.MyApplication;
import com.cricket.ipl2021.iplteamclone.Utils.PopUpAdsFull;
import com.cricket.ipl2021.iplteamclone.databinding.ActivityMainBinding;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IntentOnClickListener {

    ActivityMainBinding mainBinding;
    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();
    MyApplication myApplication;
    private NativeAd nativeAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        myApplication = MyApplication.getInstance();
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        mainBinding.tvTeam.setOnClickListener(this);
        mainBinding.tvPredictor.setOnClickListener(this);
        mainBinding.tvFantasy.setOnClickListener(this);
        mainBinding.tvAdTxtMain.setOnClickListener(this);
        mainBinding.llRate.setOnClickListener(this);
        mainBinding.llShare.setOnClickListener(this);

        BannerAds.showBannerAds(myApplication, MainActivity.this, mainBinding.adView);
        refreshAd();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_team:
                PopUpAdsFull.showInterstitialAds(myApplication, MainActivity.this, MainActivity.this, "Team", 0);

                break;
            case R.id.tv_predictor:
                PopUpAdsFull.showInterstitialAds(myApplication, MainActivity.this, MainActivity.this, "Prediction", 0);

                break;
            case R.id.tv_fantasy:
                PopUpAdsFull.showInterstitialAds(myApplication, MainActivity.this, MainActivity.this, "Fantasy", 0);

                break;
            case R.id.ll_rate:
                openRateDialog();
                break;
            case R.id.ll_share:
                openShareDialog();
                break;
            case R.id.tvAdTxtMain:
                Constants.QurekaAd(MainActivity.this);
                break;
        }
    }


    private void openRateDialog() {
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getPackageName())));
        } catch (ActivityNotFoundException unused) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }

    private void openShareDialog() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.TEXT", "Download " + getString(R.string.app_name) + " App at: https://play.google.com/store/apps/details?id=" + getPackageName());
        intent.setType("text/plain");
        startActivity(intent);
    }

    private void refreshAd() {
        AdLoader.Builder builder = new AdLoader.Builder(this, myApplication.getStringData(Constants.Pref_NativeId));

        builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(NativeAd unifiedNativeAd) {

                boolean isDestroyed = false;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    isDestroyed = isDestroyed();
                }
                if (isDestroyed || isFinishing() || isChangingConfigurations()) {
                    unifiedNativeAd.destroy();
                    return;
                }

                mainBinding.tvAdTxtMain.setVisibility(View.GONE);
                if (nativeAd != null) {
                    nativeAd.destroy();
                }
                nativeAd = unifiedNativeAd;
                NativeAdView adView = (NativeAdView) getLayoutInflater().inflate(R.layout.ad_unified, null);
                populateUnifiedNativeAdView(unifiedNativeAd, adView);
                mainBinding.rlAd.removeAllViews();
                mainBinding.rlAd.addView(adView);

            }
        });

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                mainBinding.tvAdTxtMain.setVisibility(View.VISIBLE);
            }
        }).build();

        adLoader.loadAd(new AdRequest.Builder().build());

    }

    private void populateUnifiedNativeAdView(NativeAd nativeAd, NativeAdView adView) {
        // Set the media view.
        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));

        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline and mediaContent are guaranteed to be in every UnifiedNativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        adView.setNativeAd(nativeAd);

    }

    @Override
    protected void onDestroy() {
        if (nativeAd != null) {
            nativeAd.destroy();
        }
        if (mHandler != null) {
            mHandler.removeCallbacks(mRunnable);
        }
        super.onDestroy();
    }

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        mHandler.postDelayed(mRunnable, 2000);
    }

    @Override
    public void onIntentClick(String type) {
        if (type.equals("Team")) {
            startActivity(new Intent(MainActivity.this, TeamActivity.class));
        } else if (type.equals("Prediction")) {
            startActivity(new Intent(MainActivity.this, PredictionActivity.class));
        } else if (type.equals("Fantasy")) {
            startActivity(new Intent(MainActivity.this, FantasyActivity.class));
        }
    }

    @Override
    public void onPosClick(int position) {

    }
}