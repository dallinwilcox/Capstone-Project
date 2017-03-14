package com.dallinwilcox.turnitdown.services;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

import com.dallinwilcox.turnitdown.data.DeviceVolumes;
import com.dallinwilcox.turnitdown.inf.DeviceCache;
import com.dallinwilcox.turnitdown.inf.VolumeHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by dcwilcox on 2/25/2017.
 */

public class TidFirebaseMessagingService extends FirebaseMessagingService {
    public static final String VOLUME_REQUEST = "volumeRequest";
    private static final String TAG = "TIDFBMessagingService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() == 0) {
            //TODO consider handling this as an error scenario
            return;
        }
        Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        Map<String, String> data = remoteMessage.getData();

        if (data.containsKey("deviceDeleted")) {
            DeviceCache.removeDevice(getApplicationContext());
            return;
        }
        AudioManager audioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (data.containsKey(DeviceVolumes.MEDIA_VOLUME)) {
            DeviceVolumes volumes = new DeviceVolumes(data);
            VolumeHelper.setVolumes(audioMgr, volumes);
        }
        //remote is requesting current volumes for this device
        else if (data.containsKey(VOLUME_REQUEST)) {
            DeviceVolumes volumes = VolumeHelper.getVolumes(audioMgr);
            updateDeviceVolumesInDatabase(volumes.toMap());
        }
    }

    private void updateDeviceVolumesInDatabase(Map<String, Object> volumes) {
        Context appContext = getApplicationContext();
        String userId = DeviceCache.getUserId(appContext);
        String deviceId = DeviceCache.getDeviceId(appContext);
        if (!"".equals(userId) && !"".equals(deviceId)) {
            DatabaseReference dbRef = FirebaseDatabase.getInstance()
                    .getReference("devices/" + userId + "/" + deviceId);
            dbRef.updateChildren(volumes);
        }
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }
}
