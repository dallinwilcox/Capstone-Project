package com.dallinwilcox.turnitdown.data;

import android.os.Build;

/**
 * Created by dcwilcox on 1/31/2017.
 */

public class Device {
    String id;
    String name;
    String model;
    String manufacturer;
    int deviceType;
    int mediaVolume;
    int alarmVolume;
    int ringVolume;
    int notificationVolume;

    public Device()
    {
        //TODO generate an ID
        this.id = "";
        this.name = Build.MODEL;
        this.model = Build.MODEL;
        this.manufacturer = Build.MANUFACTURER;
        this.deviceType = 0;
    }
}
