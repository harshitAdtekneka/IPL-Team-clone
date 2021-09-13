package com.cricket.ipl2021.iplteamclone.Utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class BannerAds {
    private static final String TAG = "data-->";

    public static void showBannerAds(MyApplication myApplication, Context context, LinearLayout mAdViewLayout) {

        if (myApplication.getBooleanData(Constants.Pref_IsBanner)) {
            if (myApplication.getStringData(Constants.Pref_adType).equals(Constants.ADMOB)) {
                MobileAds.initialize(context, new OnInitializationCompleteListener() {
                    @Override
                    public void onInitializationComplete(InitializationStatus initializationStatus) {
                    }
                });

                AdView mAdView = new AdView(context);
                mAdView.setAdSize(AdSize.BANNER);
                mAdView.setAdUnitId(myApplication.getStringData(Constants.Pref_BannerId));

                AdRequest.Builder builder = new AdRequest.Builder();

                mAdView.loadAd(builder.build());
                //  MediationTestSuite.launch(context);
                mAdViewLayout.addView(mAdView);
                mAdViewLayout.setGravity(Gravity.CENTER);

                mAdView.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        mAdViewLayout.setVisibility(View.GONE);

                    }

                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        mAdViewLayout.setVisibility(View.VISIBLE);

                    }
                });


            }  /*else {
                com.facebook.ads.AdView adView = new com.facebook.ads.AdView(context, context.getResources().getString(R.string.admob_banner_id), com.facebook.ads.AdSize.BANNER_HEIGHT_50);
                adView.loadAd();
                mAdViewLayout.addView(adView);
                mAdViewLayout.setGravity(Gravity.CENTER);

            }*/
        } else {
            mAdViewLayout.setVisibility(View.GONE);
        }
    }


}
