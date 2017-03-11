package com.dallinwilcox.turnitdown.inf;

import android.media.AudioManager;

import com.dallinwilcox.turnitdown.data.DeviceVolumes;

/**
 * Created by dcwilcox on 3/10/2017.
 */

public class VolumeHelper {

    public static final int NO_FLAGS = 0;

    public static DeviceVolumes getVolumes(AudioManager audioMgr) {
        int mediaVol = audioMgr.getStreamVolume(AudioManager.STREAM_MUSIC);
        int mediaMaxVol = audioMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int alarmVol = audioMgr.getStreamVolume(AudioManager.STREAM_ALARM);
        int alarmMaxVol = audioMgr.getStreamMaxVolume(AudioManager.STREAM_ALARM);
        int ringVol = audioMgr.getStreamVolume(AudioManager.STREAM_RING);
        int ringMaxVol = audioMgr.getStreamMaxVolume(AudioManager.STREAM_RING);
        int notificationVol = audioMgr.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
        int notificationMaxVol = audioMgr.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);

        return new DeviceVolumes(
                mediaVol, mediaMaxVol, alarmVol, alarmMaxVol,
                ringVol, ringMaxVol, notificationVol, notificationMaxVol);
    }

    public static void setVolumes(AudioManager audioMgr, DeviceVolumes deviceVolumes) {
        audioMgr.setStreamVolume(
                AudioManager.STREAM_MUSIC, deviceVolumes.getMediaVolume(), NO_FLAGS);
        audioMgr.setStreamVolume(
                AudioManager.STREAM_ALARM, deviceVolumes.getAlarmVolume(), NO_FLAGS);
        audioMgr.setStreamVolume(
                AudioManager.STREAM_RING, deviceVolumes.getRingVolume(), NO_FLAGS);
        audioMgr.setStreamVolume(
                AudioManager.STREAM_NOTIFICATION, deviceVolumes.getNotificationVolume(), NO_FLAGS);
    }
}
