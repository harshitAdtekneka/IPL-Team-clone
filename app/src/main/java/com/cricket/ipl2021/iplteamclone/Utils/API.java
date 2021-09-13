package com.cricket.ipl2021.iplteamclone.Utils;

import android.util.Base64;

import com.cricket.ipl2021.iplteamclone.BuildConfig;
import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class API {
    @SerializedName("sign")
    private String sign;
    @SerializedName("salt")
    private String salt;

    public API() {
        String apiKey = BuildConfig.API_KEY;
        salt = "" + getRandomSalt();
        sign = getMd5Hash(apiKey+salt);

    }

    private int getRandomSalt() {
        Random random = new Random();
        return random.nextInt(900);
    }

    public static String getMd5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String md5 = number.toString(16);
            while (md5.length() < 32)
                md5 = "0" + md5;
            return md5;
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String toBase64(String input) {
        byte[] encodeValue = Base64.encode(input.getBytes(), Base64.DEFAULT);
        return new String(encodeValue);
    }

}
