package com.dallinwilcox.turnitdown.inf;

import android.util.Log;

import com.dallinwilcox.turnitdown.data.Device;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

/**
 * Created by dcwilcox on 4/6/2017.
 */

public class NotificationSender {
    public static final String VOLUME_REQUEST = "volumeRequest";
    public static final String DEVICE_DELETED = "deviceDeleted";

    private static final String TAG = "NotificationSender";
    public static void sendNotification(Device device, Map<String, Object> data) {
        //write to FB DB that has a function watching to generate FCM notifications
        Log.d(TAG, "deviceToken = " + device.getToken() + " data = " + data);
        DatabaseReference dbRef = FirebaseDatabase.getInstance()
                .getReference("/notify/" + device.getToken() + "/data");
        dbRef.setValue(data);
    }
}
