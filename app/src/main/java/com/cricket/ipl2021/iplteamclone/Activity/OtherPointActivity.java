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
import com.cricket.ipl2021.iplteamclone.databinding.ActivityOtherPointBinding;

public class OtherPointActivity extends AppCompatActivity {

    ActivityOtherPointBinding otherPointBinding;
    String type;
    MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        otherPointBinding = ActivityOtherPointBinding.inflate(getLayoutInflater());
        setContentView(otherPointBinding.getRoot());

        myApplication=MyApplication.getInstance();

        if (getIntent() != null) {
            type = getIntent().getStringExtra("type");
        }


        Glide.with(getApplicationContext())
                .load(getResources().getDrawable(R.drawable.iplbutton3))
                .into(otherPointBinding.ivAd);

        otherPointBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        otherPointBinding.ivAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.QurekaAd(OtherPointActivity.this);
            }
        });


        if (type.equals("t20")) {
            otherPointBinding.llLineUps.setVisibility(View.VISIBLE);
            otherPointBinding.llStarting.setVisibility(View.GONE);
        } else {
            otherPointBinding.llLineUps.setVisibility(View.GONE);
            otherPointBinding.llStarting.setVisibility(View.VISIBLE);
        }
        BannerAds.showBannerAds(myApplication, OtherPointActivity.this, otherPointBinding.adView);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}