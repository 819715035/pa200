package com.ys.pa200.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.provider.Settings;

/**
 * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
 * Created by Administrator on 2016/9/1.
 */
public class NetworkUtls {
    private NetworkUtls() {
    }

    /**
     * 判断网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable();
    }

    /**
     * 判断网络是否已经连接
     *
     * @param context
     * @return
     */
    public static boolean isConnceted(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getState().equals(NetworkInfo.State.CONNECTED);
    }

    /**
     * 判断WIFI是否可用
     *
     * @param context
     * @return
     */
    public static boolean iwWifiConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 打开系统网络设置页面
     *
     * @param activity
     */
    public static void openNetSetting(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        activity.startActivity(intent);
    }

    /**
     * 打开系统WIFI设置
     *
     * @param activity
     */
    public static void openWifiSetting(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        activity.startActivity(intent);
    }


    //通过反射的方式去判断wifi是否已经连接上，并且可以开始传输数据
    private boolean checkWiFiConnectSuccess()
    {
        Class classType = WifiInfo.class;
        try
        {
            Object invo = classType.newInstance();
            Object result = invo.getClass().getMethod("getMeteredHint").invoke(invo);
            return (boolean) result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}

//    /**
//     * 打开网络设置界面
//     */3.0 ，即SDK10以前
//    public static void openSetting(Activity activity)
//    {
//        Intent intent = new Intent("/");
//        ComponentName cm = new ComponentName("com.android.settings",
//                "com.android.settings.WirelessSettings");
//        intent.setComponent(cm);
//        intent.setAction("android.intent.action.VIEW");
//        activity.startActivityForResult(intent, 0);
//    }
