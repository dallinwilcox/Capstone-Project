package com.dallinwilcox.turnitdown.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dcwilcox on 3/4/2017.
 */

public class DeviceVolumes {
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
        map.put("mediaVolume", mediaVolume);
        map.put("mediaMaxVolume", mediaMaxVolume);
        map.put("alarmVolume", alarmVolume);
        map.put("alarmMaxVolume", alarmMaxVolume);
        map.put("ringVolume", ringVolume);
        map.put("ringMaxVolume", ringMaxVolume);
        map.put("notificationVolume", notificationVolume);
        map.put("notificationMaxVolume", notificationMaxVolume);
        return map;
    }
}