package com.cricket.ipl2021.iplteamclone.Utils;

import android.Manifest;
import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.RequiresPermission;

public class NetworkUtils {
    /**
     * Get the network info
     *
     * @param context the context
     * @return network info
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    private static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        return cm.getActiveNetworkInfo();
    }

    /**
     * Check if there is any connectivity
     *
     * @param context the context
     * @return boolean boolean
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isConnected(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected());
    }
    public static int getScreenWidth(Context context) {
        int columnWidth = 0;
        try {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();

            final Point point = new Point();

            point.x = display.getWidth();
            point.y = display.getHeight();

            columnWidth = point.x;
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return columnWidth;
    }

}
