package com.cricket.ipl2021.iplteamclone.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cricket.ipl2021.iplteamclone.R;
import com.cricket.ipl2021.iplteamclone.Utils.BannerAds;
import com.cricket.ipl2021.iplteamclone.Utils.Constants;
import com.cricket.ipl2021.iplteamclone.Utils.IntentOnClickListener;
import com.cricket.ipl2021.iplteamclone.Utils.MyApplication;
import com.cricket.ipl2021.iplteamclone.Utils.PopUpAdsFull;
import com.cricket.ipl2021.iplteamclone.databinding.ActivityPredictionBinding;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;

public class PredictionActivity extends AppCompatActivity implements IntentOnClickListener {

    ActivityPredictionBinding predictionBinding;
    MyApplication myApplication;
    private NativeAd nativeAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        predictionBinding = ActivityPredictionBinding.inflate(getLayoutInflater());
        setContentView(predictionBinding.getRoot());
        myApplication = MyApplication.getInstance();
        predictionBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        Glide.with(getApplicationContext())
                .load(getResources().getDrawable(R.drawable.iplbutton3))
                .into(predictionBinding.ivAd);

        predictionBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUpAdsFull.showInterstitialAds(myApplication, PredictionActivity.this, PredictionActivity.this, "Back", 0);

            }
        });

        predictionBinding.ivAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.QurekaAd(PredictionActivity.this);
            }
        });

        predictionBinding.tvAdTxtMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.QurekaAd(PredictionActivity.this);
            }
        });

        predictionBinding.tvTxt.setText(Html.fromHtml("<pre>Dream Team is an online cricket play and win website. In which you have to make a team using your cricket knowledge. In this, you have to choose the best player from both the teams who perform best in the match. If your selected players perform well then you win that contest and you get the amount of first prize. </pre>"));
        predictionBinding.tvTxt1.setText(Html.fromHtml("<pre><br /><strong>For example -</strong><br />The matches that are going to happen in Dream Team are all shown to you on the home page. Suppose there is a match of CSK vs MI, then you have to select 11 players from both the teams. In which 1 wicket keeper, 3-5 Batsmen, 1-3 all-rounders and 3-5 Bowlers can be selected. You can choose maximum 7 players from any team.<br /><br />After choosing your Dream Team team, you have to select the captain and voice captain, then select the amount of money you want to join the contest. In this way you can play Dream Team game.</pre>"));
        predictionBinding.btnOther.setVisibility(View.VISIBLE);
        predictionBinding.btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUpAdsFull.showInterstitialAds(myApplication, PredictionActivity.this, PredictionActivity.this, "Other", 0);

            }
        });

        BannerAds.showBannerAds(myApplication, PredictionActivity.this, predictionBinding.adView);

        refreshAd();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onIntentClick(String type) {
        if (type.equals("Other")) {
            startActivity(new Intent(PredictionActivity.this, OtherActivity.class));
        } else if (type.equals("Back")) {
            onBackPressed();
        }
    }

    @Override
    public void onPosClick(int position) {

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

                predictionBinding.tvAdTxtMain.setVisibility(View.GONE);
                if (nativeAd != null) {
                    nativeAd.destroy();
                }
                nativeAd = unifiedNativeAd;
                NativeAdView adView = (NativeAdView) getLayoutInflater().inflate(R.layout.ad_unified, null);
                populateUnifiedNativeAdView(unifiedNativeAd, adView);
                predictionBinding.rlAd.removeAllViews();
                predictionBinding.rlAd.addView(adView);

            }
        });

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                predictionBinding.tvAdTxtMain.setVisibility(View.VISIBLE);
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
        super.onDestroy();
    }
}