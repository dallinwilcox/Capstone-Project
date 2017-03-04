package com.dallinwilcox.turnitdown.inf;

import android.content.Context;
import android.media.AudioManager;

import com.dallinwilcox.turnitdown.R;
import com.dallinwilcox.turnitdown.data.DeviceVolumes;

/**
 * Created by dcwilcox on 2/17/2017.
 */

public class DeviceAttributes {

    public static boolean isTablet(Context context)
    {
        //referenced http://stackoverflow.com/questions/9279111/determine-if-the-device-is-a-smartphone-or-tablet
        //returns value defined in isTablet boolean, which returns true if resolved from sw-600dp
        return context.getResources().getBoolean(R.bool.isTablet);
    }

    public static DeviceVolumes getVolumes(AudioManager audioMgr )
    {
        int mediaVol = audioMgr.getStreamVolume(AudioManager.STREAM_MUSIC);
        int mediaMaxVol = audioMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int alarmVol = audioMgr.getStreamVolume(AudioManager.STREAM_ALARM);
        int alarmMaxVol = audioMgr.getStreamMaxVolume(AudioManager.STREAM_ALARM);
        int ringVol = audioMgr.getStreamVolume(AudioManager.STREAM_RING);
        int ringMaxVol = audioMgr.getStreamMaxVolume(AudioManager.STREAM_RING);
        int notificationVol = audioMgr.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
        int notificationMaxVol = audioMgr.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);

        return new DeviceVolumes (
                mediaVol, mediaMaxVol, alarmVol, alarmMaxVol, ringVol, ringMaxVol, notificationVol, notificationMaxVol);
    }
}
