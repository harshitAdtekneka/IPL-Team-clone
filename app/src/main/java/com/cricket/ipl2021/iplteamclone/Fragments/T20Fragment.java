package com.cricket.ipl2021.iplteamclone.Fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cricket.ipl2021.iplteamclone.Activity.BattingActivity;
import com.cricket.ipl2021.iplteamclone.Activity.BowlingActivity;
import com.cricket.ipl2021.iplteamclone.Activity.FieldingPointActivity;
import com.cricket.ipl2021.iplteamclone.Activity.OtherPointActivity;
import com.cricket.ipl2021.iplteamclone.R;
import com.cricket.ipl2021.iplteamclone.Utils.Constants;
import com.cricket.ipl2021.iplteamclone.Utils.IntentOnClickListener;
import com.cricket.ipl2021.iplteamclone.Utils.MyApplication;
import com.cricket.ipl2021.iplteamclone.Utils.PopUpAdsFull;
import com.cricket.ipl2021.iplteamclone.databinding.FragmentLaoutBinding;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;

public class T20Fragment extends Fragment implements View.OnClickListener, IntentOnClickListener {
    FragmentLaoutBinding fragmentLaoutBinding;
    NativeAd nativeAd;
    MyApplication myApplication;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentLaoutBinding = FragmentLaoutBinding.inflate(inflater, container, false);
        View view = fragmentLaoutBinding.getRoot();
        myApplication = MyApplication.getInstance();
        fragmentLaoutBinding.cvBatting.setOnClickListener(this::onClick);
        fragmentLaoutBinding.cvBowling.setOnClickListener(this::onClick);
        fragmentLaoutBinding.cvFielding.setOnClickListener(this::onClick);
        fragmentLaoutBinding.cvOther.setOnClickListener(this::onClick);
        refreshAd();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (nativeAd != null) {
            nativeAd.destroy();
        }
        fragmentLaoutBinding = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.cv_batting:
                PopUpAdsFull.showInterstitialAds(myApplication, getActivity(), this, "t20", 0);
                break;

            case R.id.cv_bowling:
                PopUpAdsFull.showInterstitialAds(myApplication, getActivity(), this, "t20", 1);
                break;

            case R.id.cv_fielding:
                PopUpAdsFull.showInterstitialAds(myApplication, getActivity(), this, "t20", 2);
                break;

            case R.id.cv_other:
                PopUpAdsFull.showInterstitialAds(myApplication, getActivity(), this, "t20", 3);
                break;
        }
    }

    private void refreshAd() {
        AdLoader.Builder builder = new AdLoader.Builder(getActivity(), myApplication.getStringData(Constants.Pref_NativeId));

        builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(NativeAd unifiedNativeAd) {

                boolean isDestroyed = false;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    isDestroyed = getActivity().isDestroyed();
                }
                if (isDestroyed || getActivity().isFinishing() || getActivity().isChangingConfigurations()) {
                    unifiedNativeAd.destroy();
                    return;
                }

                fragmentLaoutBinding.tvAdTxtMain.setVisibility(View.GONE);
                if (nativeAd != null) {
                    nativeAd.destroy();
                }
                nativeAd = unifiedNativeAd;
                NativeAdView adView = (NativeAdView) getLayoutInflater().inflate(R.layout.ad_unified, null);
                populateUnifiedNativeAdView(unifiedNativeAd, adView);
                fragmentLaoutBinding.rlAd.removeAllViews();
                fragmentLaoutBinding.rlAd.addView(adView);

            }
        });

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                fragmentLaoutBinding.tvAdTxtMain.setVisibility(View.VISIBLE);
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
    public void onIntentClick(String type) {

    }

    @Override
    public void onPosClick(int position) {
        if (position == 0) {
            startActivity(new Intent(getActivity(), BattingActivity.class)
                    .putExtra("type", "t20"));
        } else if (position == 1) {
            startActivity(new Intent(getActivity(), BowlingActivity.class)
                    .putExtra("type", "t20"));
        } else if (position == 2) {
            startActivity(new Intent(getActivity(), FieldingPointActivity.class)
                    .putExtra("type", "t20"));
        } else if (position == 3) {
            startActivity(new Intent(getActivity(), OtherPointActivity.class)
                    .putExtra("type", "t20"));
        }
    }
}
