package com.cricket.ipl2021.iplteamclone.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.cricket.ipl2021.iplteamclone.Adapter.TeamAdapter;
import com.cricket.ipl2021.iplteamclone.R;
import com.cricket.ipl2021.iplteamclone.Utils.BannerAds;
import com.cricket.ipl2021.iplteamclone.Utils.Constants;
import com.cricket.ipl2021.iplteamclone.Utils.IntentOnClickListener;
import com.cricket.ipl2021.iplteamclone.Utils.MyApplication;
import com.cricket.ipl2021.iplteamclone.Utils.PopUpAdsFull;
import com.cricket.ipl2021.iplteamclone.databinding.ActivityTeamBinding;


public class TeamActivity extends AppCompatActivity implements IntentOnClickListener {

    ActivityTeamBinding teamBinding;
    MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        teamBinding = ActivityTeamBinding.inflate(getLayoutInflater());
        setContentView(teamBinding.getRoot());

        myApplication = MyApplication.getInstance();
        teamBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Glide.with(getApplicationContext())
                .load(getResources().getDrawable(R.drawable.iplbutton3))
                .into(teamBinding.ivAd);

        teamBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopUpAdsFull.showInterstitialAds(myApplication, TeamActivity.this, TeamActivity.this, "Back", 0);

            }
        });

        teamBinding.ivAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.QurekaAd(TeamActivity.this);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(TeamActivity.this, 2, RecyclerView.VERTICAL, false);
        teamBinding.rvList.setLayoutManager(gridLayoutManager);
        TeamAdapter teamAdapter = new TeamAdapter(TeamActivity.this);
        teamBinding.rvList.setAdapter(teamAdapter);

        BannerAds.showBannerAds(myApplication, TeamActivity.this, teamBinding.adView);

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
}