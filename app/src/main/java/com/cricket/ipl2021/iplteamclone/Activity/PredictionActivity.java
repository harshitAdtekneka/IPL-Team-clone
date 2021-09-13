package com.cricket.ipl2021.iplteamclone.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.bumptech.glide.Glide;
import com.cricket.ipl2021.iplteamclone.R;
import com.cricket.ipl2021.iplteamclone.Utils.BannerAds;
import com.cricket.ipl2021.iplteamclone.Utils.Constants;
import com.cricket.ipl2021.iplteamclone.Utils.IntentOnClickListener;
import com.cricket.ipl2021.iplteamclone.Utils.MyApplication;
import com.cricket.ipl2021.iplteamclone.Utils.PopUpAdsFull;
import com.cricket.ipl2021.iplteamclone.databinding.ActivityPredictionBinding;

public class PredictionActivity extends AppCompatActivity implements IntentOnClickListener {

    ActivityPredictionBinding predictionBinding;
    MyApplication myApplication;

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

        predictionBinding.tvTxt.setText(Html.fromHtml("<pre>Dream Team is an online cricket play and win website. In which you have to make a team using your cricket knowledge. In this, you have to choose the best player from both the teams who perform best in the match. If your selected players perform well then you win that contest and you get the amount of first prize. <br /><br /><strong>For example -</strong><br />The matches that are going to happen in Dream Team are all shown to you on the home page. Suppose there is a match of CSK vs MI, then you have to select 11 players from both the teams. In which 1 wicket keeper, 3-5 Batsmen, 1-3 all-rounders and 3-5 Bowlers can be selected. You can choose maximum 7 players from any team.<br /><br />After choosing your Dream Team team, you have to select the captain and voice captain, then select the amount of money you want to join the contest. In this way you can play Dream Team game.</pre>"));
        predictionBinding.btnOther.setVisibility(View.VISIBLE);
        predictionBinding.btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUpAdsFull.showInterstitialAds(myApplication, PredictionActivity.this, PredictionActivity.this, "Other", 0);

            }
        });

        BannerAds.showBannerAds(myApplication, PredictionActivity.this, predictionBinding.adView);

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
}