package com.dallinwilcox.turnitdown.inf;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dcwilcox on 3/4/2017.
 */

public class DeviceCache {

    public static final String USER_ID = "user_id";
    public static final String DEVICE_CACHE = "device_cache";
    public static final String DEVICE_ID = "device_id";

    public static String getDeviceId (Context context)
    {
        return context.getSharedPreferences(DEVICE_CACHE, Context.MODE_PRIVATE)
                .getString(DEVICE_ID, "");
    }
    public static void writeDeviceId (Context context, String deviceId)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(DEVICE_CACHE, Context.MODE_PRIVATE).edit();
        editor.putString(DEVICE_ID, deviceId);
        editor.commit();
    }
    public static String getUserId (Context context)
    {
        return context.getSharedPreferences(DEVICE_CACHE, Context.MODE_PRIVATE)
                .getString(USER_ID, "");

    }
    public static void writeUserId (Context context, String userId)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(DEVICE_CACHE, Context.MODE_PRIVATE).edit();
        editor.putString(USER_ID, userId);
        editor.commit();
    }
}
