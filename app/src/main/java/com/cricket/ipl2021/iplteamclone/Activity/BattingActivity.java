package com.cricket.ipl2021.iplteamclone.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.cricket.ipl2021.iplteamclone.R;
import com.cricket.ipl2021.iplteamclone.Utils.BannerAds;
import com.cricket.ipl2021.iplteamclone.Utils.Constants;
import com.cricket.ipl2021.iplteamclone.Utils.MyApplication;
import com.cricket.ipl2021.iplteamclone.databinding.ActivityBattingBinding;

public class BattingActivity extends AppCompatActivity {

    ActivityBattingBinding battingBinding;
    String type;
    MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        battingBinding = ActivityBattingBinding.inflate(getLayoutInflater());
        setContentView(battingBinding.getRoot());
        myApplication = MyApplication.getInstance();

        if (getIntent() != null) {
            type = getIntent().getStringExtra("type");
        }

        Glide.with(getApplicationContext())
                .load(getResources().getDrawable(R.drawable.iplbutton3))
                .into(battingBinding.ivAd);

        battingBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        battingBinding.ivAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.QurekaAd(BattingActivity.this);
            }
        });


        if (type.equals("t20")) {
            battingBinding.llT20.setVisibility(View.VISIBLE);
        } else if (type.equals("od")) {
            battingBinding.llOd.setVisibility(View.VISIBLE);
        } else if (type.equals("test")) {
            battingBinding.llTest.setVisibility(View.VISIBLE);
        }

        BannerAds.showBannerAds(myApplication, BattingActivity.this, battingBinding.adView);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}