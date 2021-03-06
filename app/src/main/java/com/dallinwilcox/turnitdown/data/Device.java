package com.dallinwilcox.turnitdown.data;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;

import com.dallinwilcox.turnitdown.BR;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by dcwilcox on 1/31/2017.
 * Referenced
 * https://developer.android.com/studio/write/annotations.html#enum-annotations
 * https://halfthought.wordpress.com/2016/03/23/2-way-data-binding-on-android/
 * https://medium.com/google-developers/android-data-binding-observability-9de4ff3fe038
 */

public class Device extends BaseObservable implements Parcelable{

    public static final String DEVICE_EXTRA = "deviceExtra";

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({PHONE, TABLET, WATCH, TV})
    public @interface DeviceType {}
    public static final int PHONE = 0;
    public static final int TABLET = 1;
    public static final int WATCH = 2;
    public static final int TV = 3;

    String id;
    String token;
    String user;
    String name;
    String model;
    String manufacturer;
    int deviceType;
    int mediaVolume;
    int mediaMaxVolume;
    int alarmVolume;
    int alarmMaxVolume;
    int ringVolume;
    int ringMaxVolume;
    int notificationVolume;
    int notificationMaxVolume;

    //Default constructor w/ no arguments for FireBase Database
    public Device()
    {}

    public Device(String token, String user)
    {
        this.token = token;
        this.user = user;
        this.name = Build.MODEL;
        this.model = Build.MODEL;
        this.manufacturer = Build.MANUFACTURER;
        this.deviceType = PHONE;

    }

    protected Device(Parcel in) {
        id = in.readString();
        token = in.readString();
        user = in.readString();
        name = in.readString();
        model = in.readString();
        manufacturer = in.readString();
        deviceType = in.readInt();
        mediaVolume = in.readInt();
        mediaMaxVolume = in.readInt();
        alarmVolume = in.readInt();
        alarmMaxVolume = in.readInt();
        ringVolume = in.readInt();
        ringMaxVolume = in.readInt();
        notificationVolume = in.readInt();
        notificationMaxVolume = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(token);
        dest.writeString(user);
        dest.writeString(name);
        dest.writeString(model);
        dest.writeString(manufacturer);
        dest.writeInt(deviceType);
        dest.writeInt(mediaVolume);
        dest.writeInt(mediaMaxVolume);
        dest.writeInt(alarmVolume);
        dest.writeInt(alarmMaxVolume);
        dest.writeInt(ringVolume);
        dest.writeInt(ringMaxVolume);
        dest.writeInt(notificationVolume);
        dest.writeInt(notificationMaxVolume);
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

    @Bindable
    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }
    @Bindable
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        notifyPropertyChanged(BR.token);
    }

    @Bindable
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
        notifyPropertyChanged(BR.user);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
        notifyPropertyChanged(BR.model);
    }

    @Bindable
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        notifyPropertyChanged(BR.manufacturer);
    }

    @DeviceType @Bindable
    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(@DeviceType int deviceType) {
        this.deviceType = deviceType;
        notifyPropertyChanged(BR.deviceType);
    }

    @Bindable
    public int getMediaVolume() {
        return mediaVolume;
    }

    public void setMediaVolume(int mediaVolume) {
        this.mediaVolume = mediaVolume;
        notifyPropertyChanged(BR.mediaVolume);
    }

    @Bindable
    public int getMediaMaxVolume() {
        return mediaMaxVolume;
    }

    public void setMediaMaxVolume(int mediaMaxVolume) {
        this.mediaMaxVolume = mediaMaxVolume;
        notifyPropertyChanged(BR.mediaMaxVolume);
    }

    @Bindable
    public int getAlarmVolume() {
        return alarmVolume;
    }

    public void setAlarmVolume(int alarmVolume) {
        this.alarmVolume = alarmVolume;
        notifyPropertyChanged(BR.alarmVolume);
    }

    @Bindable
    public int getAlarmMaxVolume() {
        return alarmMaxVolume;
    }

    public void setAlarmMaxVolume(int alarmMaxVolume) {
        this.alarmMaxVolume = alarmMaxVolume;
        notifyPropertyChanged(BR.alarmMaxVolume);
    }

    @Bindable
    public int getRingVolume() {
        return ringVolume;
    }

    public void setRingVolume(int ringVolume) {
        this.ringVolume = ringVolume;
        notifyPropertyChanged(BR.ringVolume);
    }

    @Bindable
    public int getRingMaxVolume() {
        return ringMaxVolume;
    }

    public void setRingMaxVolume(int ringMaxVolume) {
        this.ringMaxVolume = ringMaxVolume;
        notifyPropertyChanged(BR.ringMaxVolume);
    }

    @Bindable
    public int getNotificationVolume() {
        return notificationVolume;
    }

    public void setNotificationVolume(int notificationVolume) {
        this.notificationVolume = notificationVolume;
        notifyPropertyChanged(BR.notificationMaxVolume);
    }

    @Bindable
    public int getNotificationMaxVolume() {
        return notificationMaxVolume;
    }

    public void setNotificationMaxVolume(int notificationMaxVolume) {
        this.notificationMaxVolume = notificationMaxVolume;
        notifyPropertyChanged(BR.notificationMaxVolume);
    }

    public void setVolumes(DeviceVolumes deviceVolumes)
    {
        this.mediaVolume = deviceVolumes.mediaVolume;
        this.mediaMaxVolume = deviceVolumes.mediaMaxVolume;
        this.alarmVolume = deviceVolumes.alarmVolume;
        this.alarmMaxVolume = deviceVolumes.alarmMaxVolume;
        this.ringVolume = deviceVolumes.ringVolume;
        this.ringMaxVolume = deviceVolumes.ringMaxVolume;
        this.notificationVolume = deviceVolumes.notificationVolume;
        this.notificationMaxVolume = deviceVolumes.notificationMaxVolume;
        //notify entire object has changed instead of each field individually since majority changed
        notifyChange();
    }
    public DeviceVolumes getVolumes()
    {
        return new DeviceVolumes(
                mediaVolume, mediaMaxVolume,
                alarmVolume, alarmMaxVolume,
                ringVolume, ringMaxVolume,
                notificationVolume, notificationMaxVolume
                );
    }

    @Override
    public String toString() {
        return "Device{" +
                "id='" + id + '\'' +
                ", token='" + token +'\'' +
                ", user='" + user + '\'' +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", deviceType=" + deviceType +
                ", mediaVolume=" + mediaVolume +
                ", alarmVolume=" + alarmVolume +
                ", ringVolume=" + ringVolume +
                ", notificationVolume=" + notificationVolume +
                '}';
    }
}
