package com.dallinwilcox.turnitdown.data;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dcwilcox on 3/4/2017.
 */

public class DeviceVolumes {
    public static final String MEDIA_VOLUME = "mediaVolume";
    public static final String MEDIA_MAX_VOLUME = "mediaMaxVolume";
    public static final String ALARM_VOLUME = "alarmVolume";
    public static final String ALARM_MAX_VOLUME = "alarmMaxVolume";
    public static final String RING_VOLUME = "ringVolume";
    public static final String RING_MAX_VOLUME = "ringMaxVolume";
    public static final String NOTIFICATION_VOLUME = "notificationVolume";
    public static final String NOTIFICATION_MAX_VOLUME = "notificationMaxVolume";

    int mediaVolume;
    int mediaMaxVolume;
    int alarmVolume;
    int alarmMaxVolume;
    int ringVolume;
    int ringMaxVolume;
    int notificationVolume;
    int notificationMaxVolume;

    public DeviceVolumes(int mediaVolume, int mediaMaxVolume, int alarmVolume, int alarmMaxVolume,
                         int ringVolume, int ringMaxVolume, int notificationVolume, int notificationMaxVolume) {
        this.mediaVolume = mediaVolume;
        this.mediaMaxVolume = mediaMaxVolume;
        this.alarmVolume = alarmVolume;
        this.alarmMaxVolume = alarmMaxVolume;
        this.ringVolume = ringVolume;
        this.ringMaxVolume = ringMaxVolume;
        this.notificationVolume = notificationVolume;
        this.notificationMaxVolume = notificationMaxVolume;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put(MEDIA_VOLUME, mediaVolume);
        map.put(MEDIA_MAX_VOLUME, mediaMaxVolume);
        map.put(ALARM_VOLUME, alarmVolume);
        map.put(ALARM_MAX_VOLUME, alarmMaxVolume);
        map.put(RING_VOLUME, ringVolume);
        map.put(RING_MAX_VOLUME, ringMaxVolume);
        map.put(NOTIFICATION_VOLUME, notificationVolume);
        map.put(NOTIFICATION_MAX_VOLUME, notificationMaxVolume);
        return map;
    }
    public DeviceVolumes (Map<String, String> volumes)
    {
        //if this were guaranteed API 24+, would consider using getOrDefault instead
        this.mediaVolume = translate(volumes.get(MEDIA_VOLUME));
        this.mediaMaxVolume = translate(volumes.get(MEDIA_MAX_VOLUME));
        this.alarmVolume = translate(volumes.get(ALARM_VOLUME));
        this.alarmMaxVolume = translate(volumes.get(ALARM_MAX_VOLUME));
        this.ringVolume = translate(volumes.get(RING_VOLUME));
        this.ringMaxVolume = translate(volumes.get(RING_MAX_VOLUME));
        this.notificationVolume = translate(volumes.get(NOTIFICATION_VOLUME));
        this.notificationMaxVolume = translate(volumes.get(NOTIFICATION_MAX_VOLUME));
    }

    private int translate (String string)
    {
        if (TextUtils.isEmpty(string)){ return 0;}
        int integer = 0;
        try {
            integer = Integer.parseInt(string);
        } catch (NumberFormatException e) {
            //do nothing and just return 0
        }
        return integer;
    }

    public int getMediaVolume() {
        return mediaVolume;
    }

    public int getAlarmVolume() {
        return alarmVolume;
    }

    public int getRingVolume() {
        return ringVolume;
    }

    public int getNotificationVolume() {
        return notificationVolume;
    }
}