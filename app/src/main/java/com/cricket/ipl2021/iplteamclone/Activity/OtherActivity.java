package com.cricket.ipl2021.iplteamclone.Activity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

public class OtherActivity extends AppCompatActivity implements IntentOnClickListener {

    ActivityPredictionBinding predictionBinding;
    MyApplication myApplication;
    NativeAd nativeAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        predictionBinding = ActivityPredictionBinding.inflate(getLayoutInflater());
        setContentView(predictionBinding.getRoot());
        myApplication = MyApplication.getInstance();
        predictionBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopUpAdsFull.showInterstitialAds(myApplication, OtherActivity.this, OtherActivity.this, "Back", 0);

            }
        });

        Glide.with(getApplicationContext())
                .load(getResources().getDrawable(R.drawable.iplbutton3))
                .into(predictionBinding.ivAd);

        predictionBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        predictionBinding.ivAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.QurekaAd(OtherActivity.this);
            }
        });

        predictionBinding.tvAdTxtMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.QurekaAd(OtherActivity.this);
            }
        });

        predictionBinding.tvTitle.setText("What is Dream Team and how to make");
        predictionBinding.btnOther.setVisibility(View.GONE);
        predictionBinding.tvTxt.setText(Html.fromHtml("<pre> For those who play Dream Team,<br />what will be Aaj Ki Dream Team and<br />how to make Dream Team team, it<br />takes a lot of analysis to get maximum<br />points in Dream Team and get good<br />rank and win thousands of lakhs of<br />rupees as reward.<br /> <br /> Dream Team is the most popular<br />fantasy sports game on which you can<br />earn thousands and millions of rupees<br />by making and winning teams of<br />almost all types of sports like cricket,<br />football, kabaddi, baseball, basketball,<br />hockey, handball, volleyball etc.<br /><br /> For which you must have the<br />knowledge of any sport, using which<br />you create your own team and<br />select the players who are the best<br />performers, then only you can win<br />money from Dream Team.<br /><br /> Because cricket is the most<br />craze in India and everyone likes to<br />play and watch cricket, so most of<br />the people play in Dream Team by<br />making a cricket team and before<br />the match starts how to choose<br />Aaj Ki Dream Team Team or Aaj Ki<br />Dream Team Team Everyone wants to<br />know what will happen.<br /><br /> So today we are going to tell<br />you about all the ways with the help<br />of which you can get information<br />about Aaj Ki Dream Team and<br />which will help you to create your<br />Dream Team.</pre>"));

        BannerAds.showBannerAds(myApplication, OtherActivity.this, predictionBinding.adView);
        refreshAd();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onIntentClick(String type) {
        if (type.equals("Back")) {
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