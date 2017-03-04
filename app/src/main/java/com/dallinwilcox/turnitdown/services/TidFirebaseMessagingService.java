package com.dallinwilcox.turnitdown.services;

import android.app.Service;
import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

import com.dallinwilcox.turnitdown.data.DeviceVolumes;
import com.dallinwilcox.turnitdown.inf.DeviceAttributes;
import com.dallinwilcox.turnitdown.inf.DeviceCache;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by dcwilcox on 2/25/2017.
 */

public class TidFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "TIDFBMessagingService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());


        }
        else //remote is requesting current audio settings for this device
        {
            AudioManager audioMgr = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
            DeviceVolumes volumes = DeviceAttributes.getVolumes(audioMgr);
            updateDeviceVolumesInDatabase(volumes.toMap());

        }
    }
    private void updateDeviceVolumesInDatabase(Map<String,Object> volumes) {
        Context appContext = getApplicationContext();
        String userId = DeviceCache.getUserId(appContext);
        String deviceId = DeviceCache.getDeviceId(appContext);
        if ("" != userId && "" != deviceId) {
            DatabaseReference dbRef = FirebaseDatabase.getInstance()
                    .getReference("devices/" + userId + "/" + deviceId + "/id");
            dbRef.updateChildren(volumes);
        }
    }
    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }
}
