package com.cricket.ipl2021.iplteamclone.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cricket.ipl2021.iplteamclone.R;
import com.cricket.ipl2021.iplteamclone.Utils.BannerAds;
import com.cricket.ipl2021.iplteamclone.Utils.Constants;
import com.cricket.ipl2021.iplteamclone.Utils.MyApplication;
import com.cricket.ipl2021.iplteamclone.databinding.ActivityBattingBinding;
import com.cricket.ipl2021.iplteamclone.databinding.ActivityBowlingBinding;

public class BowlingActivity extends AppCompatActivity {

    ActivityBowlingBinding bowlingBinding;
    String type;
    MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bowlingBinding = ActivityBowlingBinding.inflate(getLayoutInflater());
        setContentView(bowlingBinding.getRoot());

        myApplication=MyApplication.getInstance();

        if (getIntent() != null) {
            type = getIntent().getStringExtra("type");
        }

        Glide.with(getApplicationContext())
                .load(getResources().getDrawable(R.drawable.iplbutton3))
                .into(bowlingBinding.ivAd);

        bowlingBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        bowlingBinding.ivAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.QurekaAd(BowlingActivity.this);
            }
        });

        if (type.equals("t20")) {
            bowlingBinding.llT20.setVisibility(View.VISIBLE);
        } else if (type.equals("od")) {
            bowlingBinding.llOd.setVisibility(View.VISIBLE);
        } else if (type.equals("test")) {
            bowlingBinding.llTest.setVisibility(View.VISIBLE);
        }

        BannerAds.showBannerAds(myApplication, BowlingActivity.this, bowlingBinding.adView);

    }
}