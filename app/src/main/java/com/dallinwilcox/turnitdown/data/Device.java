package com.dallinwilcox.turnitdown.data;

import android.os.Build;

/**
 * Created by dcwilcox on 1/31/2017.
 */

public class Device {
    public enum DeviceType {PHONE, TABLET, WATCH, TV}
    String id;
    String name;
    String model;
    String manufacturer;
    DeviceType deviceType;
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
        this.deviceType = DeviceType.PHONE;
    }
}
