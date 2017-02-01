package com.dallinwilcox.turnitdown.data;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dcwilcox on 1/31/2017.
 */

public class Device implements Parcelable{
    protected Device(Parcel in) {
        id = in.readString();
        name = in.readString();
        model = in.readString();
        manufacturer = in.readString();
        mediaVolume = in.readInt();
        alarmVolume = in.readInt();
        ringVolume = in.readInt();
        notificationVolume = in.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(model);
        dest.writeString(manufacturer);
        dest.writeInt(mediaVolume);
        dest.writeInt(alarmVolume);
        dest.writeInt(ringVolume);
        dest.writeInt(notificationVolume);
    }

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
