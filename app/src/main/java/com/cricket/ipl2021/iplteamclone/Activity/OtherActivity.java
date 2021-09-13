package com.cricket.ipl2021.iplteamclone.Activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cricket.ipl2021.iplteamclone.R;
import com.cricket.ipl2021.iplteamclone.Utils.BannerAds;
import com.cricket.ipl2021.iplteamclone.Utils.Constants;
import com.cricket.ipl2021.iplteamclone.Utils.IntentOnClickListener;
import com.cricket.ipl2021.iplteamclone.Utils.MyApplication;
import com.cricket.ipl2021.iplteamclone.Utils.PopUpAdsFull;
import com.cricket.ipl2021.iplteamclone.databinding.ActivityPredictionBinding;

public class OtherActivity extends AppCompatActivity implements IntentOnClickListener {

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

        predictionBinding.tvTitle.setText("What is Dream Team and how to make");
        predictionBinding.btnOther.setVisibility(View.GONE);
        predictionBinding.tvTxt.setText(Html.fromHtml("<pre> For those who play Dream Team,<br />what will be Aaj Ki Dream Team and<br />how to make Dream Team team, it<br />takes a lot of analysis to get maximum<br />points in Dream Team and get good<br />rank and win thousands of lakhs of<br />rupees as reward.<br /> <br /> Dream Team is the most popular<br />fantasy sports game on which you can<br />earn thousands and millions of rupees<br />by making and winning teams of<br />almost all types of sports like cricket,<br />football, kabaddi, baseball, basketball,<br />hockey, handball, volleyball etc.<br /><br /> For which you must have the<br />knowledge of any sport, using which<br />you create your own team and<br />select the players who are the best<br />performers, then only you can win<br />money from Dream Team.<br /><br /> Because cricket is the most<br />craze in India and everyone likes to<br />play and watch cricket, so most of<br />the people play in Dream Team by<br />making a cricket team and before<br />the match starts how to choose<br />Aaj Ki Dream Team Team or Aaj Ki<br />Dream Team Team Everyone wants to<br />know what will happen.<br /><br /> So today we are going to tell<br />you about all the ways with the help<br />of which you can get information<br />about Aaj Ki Dream Team and<br />which will help you to create your<br />Dream Team.</pre>"));

        BannerAds.showBannerAds(myApplication, OtherActivity.this, predictionBinding.adView);

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