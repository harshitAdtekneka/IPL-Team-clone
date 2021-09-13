package com.cricket.ipl2021.iplteamclone.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.cricket.ipl2021.iplteamclone.BuildConfig;
import com.cricket.ipl2021.iplteamclone.R;
import com.cricket.ipl2021.iplteamclone.Utils.API;
import com.cricket.ipl2021.iplteamclone.Utils.Constants;
import com.cricket.ipl2021.iplteamclone.Utils.MyApplication;
import com.cricket.ipl2021.iplteamclone.Utils.NetworkUtils;
import com.cricket.ipl2021.iplteamclone.databinding.ActivitySplashScreenBinding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.UnknownHostException;

import cz.msebera.android.httpclient.Header;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = "data-->";
    ActivitySplashScreenBinding splashScreenBinding;
    MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashScreenBinding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(splashScreenBinding.getRoot());

        myApplication = MyApplication.getInstance();


        if (NetworkUtils.isConnected(SplashScreenActivity.this)) {
            checkLicense();
        } else {
            Toast.makeText(myApplication, getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
        }

    }

    private void splashScreen() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Application application = getApplication();
                if (!(application instanceof MyApplication)) {
                    Log.e(TAG, "Failed to cast application to MyApplication.");

                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                    return;
                }

                ((MyApplication) application)
                        .showAdIfAvailable(
                                SplashScreenActivity.this,
                                new MyApplication.OnShowAdCompleteListener() {
                                    @Override
                                    public void onShowAdComplete() {

                                        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();

                                    }
                                });

            }
        }, 5000);
    }

    private void checkLicense() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        JsonObject jsObj = (JsonObject) new Gson().toJsonTree(new API());
        jsObj.addProperty("method_name", Constants.ADS_METHOD);
        jsObj.addProperty("package_name", /*BuildConfig.APPLICATION_ID*/"com.cricket.ipl2021.iplteam");
        params.put("data", API.toBase64(jsObj.toString()));
        client.post(Constants.API_URL, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {

                    JSONObject mainJson = new JSONObject(result);
                    JSONArray jsonArray = mainJson.getJSONArray(Constants.ARRAY_NAME);
                    JSONObject objJson = jsonArray.getJSONObject(0);
                    JSONObject dataJson = objJson.getJSONObject(Constants.OBJECT_NAME);


                    Constants.adType = dataJson.getString("ad_type");
                    Constants.adMobPublisherId = dataJson.getString("publisher_id");

                    Constants.isAdDisplay = dataJson.getBoolean("status");
                    Constants.isInterstitial = dataJson.getBoolean("interstital_ad");
                    Constants.isBanner = dataJson.getBoolean("banner_ad");
                    Constants.isMR = dataJson.getBoolean("mrec_ad");

                    Constants.isNative = dataJson.getBoolean("native_ad");
                    Constants.isAppOpen = dataJson.getBoolean("open_ad");
                    Constants.isReward = dataJson.getBoolean("reward_ad");
                    Constants.bannerId = dataJson.getString("banner_ad_id");
                    Constants.mrId = dataJson.getString("mrec_ad_id");
                    Constants.rewardID = dataJson.getString("reward_ad_id");
                    Constants.nativeID = dataJson.getString("native_ad_id");
                    Constants.interstitialId = dataJson.getString("interstital_ad_id");
                    Constants.appOpenId = dataJson.getString("open_ad_id");

                    Constants.Interstitial_AD_COUNT = dataJson.getInt("interstital_ad_click");
                    Constants.Native_AD_COUNT = dataJson.getInt("native_ad_click");

                    Constants.matchIndex = dataJson.getInt("match_index");

                    myApplication.setStringData(Constants.Pref_adType, Constants.adType);
                    myApplication.setStringData(Constants.Pref_AdMobPublisherId, Constants.adMobPublisherId);

                    myApplication.setBooleanData(Constants.Pref_IsAdDisplay, Constants.isAdDisplay);
                    myApplication.setBooleanData(Constants.Pref_IsInterstitial, Constants.isInterstitial);
                    myApplication.setBooleanData(Constants.Pref_IsBanner, Constants.isBanner);
                    myApplication.setBooleanData(Constants.Pref_IsMr, Constants.isMR);
                    myApplication.setBooleanData(Constants.Pref_IsReward, Constants.isReward);
                    myApplication.setBooleanData(Constants.Pref_IsNative, Constants.isNative);
                    myApplication.setBooleanData(Constants.Pref_IsAppOpen, Constants.isAppOpen);

                    myApplication.setStringData(Constants.Pref_BannerId, Constants.bannerId);
                    myApplication.setStringData(Constants.Pref_MrId, Constants.mrId);
                    myApplication.setStringData(Constants.Pref_RewardId, Constants.rewardID);
                    myApplication.setStringData(Constants.Pref_NativeId, Constants.nativeID);
                    myApplication.setStringData(Constants.Pref_InterstitialId, Constants.interstitialId);
                    myApplication.setStringData(Constants.Pref_AppOpenId, Constants.appOpenId);

                    myApplication.setIntData(Constants.Pref_InterstitialPos, Constants.Interstitial_AD_COUNT);
                    myApplication.setIntData(Constants.Pref_NativePos, Constants.Native_AD_COUNT);

                    myApplication.setIntData(Constants.Pref_MatchIndex, Constants.matchIndex);

                    splashScreen();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i(TAG, "onSuccess: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i(TAG, "onFailure: " + error.getMessage());
            }

        });
    }
}