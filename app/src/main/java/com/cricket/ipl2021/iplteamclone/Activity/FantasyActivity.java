package com.cricket.ipl2021.iplteamclone.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.cricket.ipl2021.iplteamclone.Fragments.ODFragment;
import com.cricket.ipl2021.iplteamclone.Fragments.T20Fragment;
import com.cricket.ipl2021.iplteamclone.Fragments.TestFragment;
import com.cricket.ipl2021.iplteamclone.R;
import com.cricket.ipl2021.iplteamclone.Utils.BannerAds;
import com.cricket.ipl2021.iplteamclone.Utils.Constants;
import com.cricket.ipl2021.iplteamclone.Utils.IntentOnClickListener;
import com.cricket.ipl2021.iplteamclone.Utils.MyApplication;
import com.cricket.ipl2021.iplteamclone.Utils.PopUpAdsFull;
import com.cricket.ipl2021.iplteamclone.databinding.ActivityFantasyBinding;

import java.util.ArrayList;
import java.util.List;

public class FantasyActivity extends AppCompatActivity implements IntentOnClickListener {
    ActivityFantasyBinding fantasyBinding;
    MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fantasyBinding = ActivityFantasyBinding.inflate(getLayoutInflater());
        setContentView(fantasyBinding.getRoot());
        myApplication = MyApplication.getInstance();
        fantasyBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Glide.with(getApplicationContext())
                .load(getResources().getDrawable(R.drawable.iplbutton3))
                .into(fantasyBinding.ivAd);

        fantasyBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUpAdsFull.showInterstitialAds(myApplication, FantasyActivity.this, FantasyActivity.this, "Back", 0);
            }
        });

        fantasyBinding.ivAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.QurekaAd(FantasyActivity.this);
            }
        });


        setupViewPager();
        fantasyBinding.tabs.setupWithViewPager(fantasyBinding.viewPager);

        BannerAds.showBannerAds(myApplication, FantasyActivity.this, fantasyBinding.adView);


    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new T20Fragment(), "T20");
        adapter.addFragment(new ODFragment(), "OD");
        adapter.addFragment(new TestFragment(), "TEST");
        fantasyBinding.viewPager.setAdapter(adapter);
    }

    @Override
    public void onIntentClick(String type) {
        if (type.equals("Back")){
            onBackPressed();
        }
    }

    @Override
    public void onPosClick(int position) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}