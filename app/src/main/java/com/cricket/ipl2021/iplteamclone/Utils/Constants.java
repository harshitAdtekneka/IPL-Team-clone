package com.cricket.ipl2021.iplteamclone.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;

import androidx.browser.customtabs.CustomTabsIntent;

import com.cricket.ipl2021.iplteamclone.BuildConfig;
import com.cricket.ipl2021.iplteamclone.R;


public class Constants {

    private static String SERVER_URL = BuildConfig.SERVER_URL;
    public static final String API_URL = SERVER_URL + "/api.php/";


    public static final String ARRAY_NAME = "adtekneka";
    public static final String OBJECT_NAME = "data";
    public static final String ADS_METHOD = "ads";
    public static final String PAYMENT_METHOD = "add_payment_request";

    public static final String ADMOB = "admob";
    public static int AD_COUNT = 0;
    public static int Interstitial_AD_COUNT;
    public static int Native_AD_COUNT = 0;
    public static boolean isBanner = true, isInterstitial = true, isNative = true, isMR = false, isAdDisplay = true, isAppOpen = false, isReward = true;
    public static String bannerId, mrId, interstitialId, nativeID, adMobPublisherId, appOpenId, rewardID;
    public static String adType;
    public static int matchIndex;

    public static String CREDIT = "C";
    public static String DEBIT = "D";

    public static String Pref_adType = "pref_adtype";
    public static String Pref_IsBanner = "pref_is_banner";
    public static String Pref_IsInterstitial = "pref_is_interstitial";
    public static String Pref_IsNative = "pref_is_native";
    public static String Pref_IsMr = "pref_is_mr";
    public static String Pref_IsAdDisplay = "pref_is_on_play_interstitial";
    public static String Pref_IsAppOpen = "pref_is_app_open";
    public static String Pref_IsReward = "pref_is_reward";

    public static String Pref_BannerId = "pref_banner_id";
    public static String Pref_MrId = "pref_mr_id";
    public static String Pref_InterstitialId = "pref_interstitial_id";
    public static String Pref_NativeId = "pref_native_id";
    public static String Pref_AdMobPublisherId = "pref_admob_publisher_id";
    public static String Pref_AppOpenId = "pref_app_open_id";
    public static String Pref_RewardId = "pref_reward_id";

    public static String Pref_NativePos = "pref_native_show_pos";
    public static String Pref_InterstitialPos = "pref_native_movie_pos";

    public static String Pref_MatchIndex = "pref_match_index";


    public static String CSK = "Chennai Super Kings";
    public static String DC = "Delhi Capitals";
    public static String KKR = "Kolkata Knight Riders";
    public static String MI = "Mumbai Indians";
    public static String PK = "Punjab Kings";
    public static String RR = "Rajasthan Royals";
    public static String RCB = "Royal Challengers Bangalore";
    public static String SRH = "Sunrisers Hydrabad";

    public static int teamLogoArray[] = {R.drawable.csk_logo,
            R.drawable.mi_logo,
            R.drawable.rcb_logo,
            R.drawable.rr_logo,
            R.drawable.dc_logo,
            R.drawable.kkr_logo,
            R.drawable.pk_logo,
            R.drawable.srh_logo,
    };

    public static String teamNameArray[] = {CSK, MI, RCB, RR, DC, KKR, PK, SRH};
    public static int teamColorArray[] = {R.color.csk, R.color.mi, R.color.rcb, R.color.rr, R.color.dc, R.color.kkr, R.color.pk, R.color.srh};
    public static String fantasyArray[] = {"T20","OD","TEST","T10"};

    public static void QurekaAd(Context context) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.intent.setPackage("com.android.chrome");
        customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        customTabsIntent.launchUrl(context, Uri.parse(BuildConfig.QUREKA_URL));
    }

    public static GradientDrawable backGrad(int color1, int color2) {
        int[] colors = new int[2];
        colors[0] = color1;
        colors[1] = color2;


        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, colors);

        gd.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        gd.setGradientRadius(300f);
        gd.setCornerRadius(0f);
        return gd;
    }
}
