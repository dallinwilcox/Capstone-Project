package com.dallinwilcox.turnitdown.data;

import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by dcwilcox on 1/31/2017.
 * Referenced https://developer.android.com/studio/write/annotations.html#enum-annotations
 */

public class Device extends BaseObservable implements Parcelable{

    public static final String DEVICE_EXTRA = "item_id";

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({PHONE, TABLET, WATCH, TV})
    public @interface DeviceType {}
    public static final int PHONE = 0;
    public static final int TABLET = 1;
    public static final int WATCH = 2;
    public static final int TV = 3;

    String id;
    String user;
    String name;
    String model;
    String manufacturer;
    int deviceType;
    int mediaVolume;
    int alarmVolume;
    int ringVolume;
    int notificationVolume;

    //empty constructor
    public Device(){}

    public Device(String id, String user)
    {
        this.id = id;
        this.user = user;
        this.name = Build.MODEL;
        this.model = Build.MODEL;
        this.manufacturer = Build.MANUFACTURER;
        this.deviceType = PHONE;
    }

    protected Device(Parcel in) {
        id = in.readString();
        user = in.readString();
        name = in.readString();
        model = in.readString();
        manufacturer = in.readString();
        deviceType = in.readInt();
        mediaVolume = in.readInt();
        alarmVolume = in.readInt();
        ringVolume = in.readInt();
        notificationVolume = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(user);
        dest.writeString(name);
        dest.writeString(model);
        dest.writeString(manufacturer);
        dest.writeInt(deviceType);
        dest.writeInt(mediaVolume);
        dest.writeInt(alarmVolume);
        dest.writeInt(ringVolume);
        dest.writeInt(notificationVolume);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Device> CREATOR = new Creator<Device>() {
        @Override
        public Device createFromParcel(Parcel in) {
            return new Device(in);
        }

        @Override
        public Device[] newArray(int size) {
            return new Device[size];
        }
    };
    public String getId() { return id; }

    public void setId(String id) {
        this.user = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @DeviceType
    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(@DeviceType int deviceType) {
        this.deviceType = deviceType;
    }

    public int getMediaVolume() {
        return mediaVolume;
    }

    public void setMediaVolume(int mediaVolume) {
        this.mediaVolume = mediaVolume;
    }

    public int getAlarmVolume() {
        return alarmVolume;
    }

    public void setAlarmVolume(int alarmVolume) {
        this.alarmVolume = alarmVolume;
    }

    public int getRingVolume() {
        return ringVolume;
    }

    public void setRingVolume(int ringVolume) {
        this.ringVolume = ringVolume;
    }

    public int getNotificationVolume() {
        return notificationVolume;
    }

    public void setNotificationVolume(int notificationVolume) {
        this.notificationVolume = notificationVolume;
    }
}
