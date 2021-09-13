package com.cricket.ipl2021.iplteamclone.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cricket.ipl2021.iplteamclone.R;
import com.cricket.ipl2021.iplteamclone.Utils.BannerAds;
import com.cricket.ipl2021.iplteamclone.Utils.Constants;
import com.cricket.ipl2021.iplteamclone.Utils.MyApplication;
import com.cricket.ipl2021.iplteamclone.databinding.ActivityFeildingPointBinding;

public class FieldingPointActivity extends AppCompatActivity {

    ActivityFeildingPointBinding feildingPointBinding;
    String type;
    MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feildingPointBinding = ActivityFeildingPointBinding.inflate(getLayoutInflater());
        setContentView(feildingPointBinding.getRoot());
        myApplication = MyApplication.getInstance();
        if (getIntent() != null) {
            type = getIntent().getStringExtra("type");
        }

        Glide.with(getApplicationContext())
                .load(getResources().getDrawable(R.drawable.iplbutton3))
                .into(feildingPointBinding.ivAd);

        feildingPointBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        feildingPointBinding.ivAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.QurekaAd(FieldingPointActivity.this);
            }
        });


        if (type.equals("test")) {
            feildingPointBinding.llTest.setVisibility(View.VISIBLE);
            feildingPointBinding.llT20.setVisibility(View.GONE);
        } else {
            feildingPointBinding.llTest.setVisibility(View.GONE);
            feildingPointBinding.llT20.setVisibility(View.VISIBLE);
        }

        BannerAds.showBannerAds(myApplication, FieldingPointActivity.this, feildingPointBinding.adView);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}