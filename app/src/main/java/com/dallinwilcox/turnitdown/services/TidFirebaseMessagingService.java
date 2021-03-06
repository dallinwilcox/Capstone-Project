package com.dallinwilcox.turnitdown.services;

import android.content.Context;
import android.media.AudioManager;
import android.text.TextUtils;
import android.util.Log;

import com.dallinwilcox.turnitdown.R;
import com.dallinwilcox.turnitdown.data.DeviceVolumes;
import com.dallinwilcox.turnitdown.inf.DeviceCache;
import com.dallinwilcox.turnitdown.inf.NotificationSender;
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
    private static final String TAG = "TIDFBMessagingService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() == 0) {
            Log.e(TAG, "onMessageReceived: message without data");
            return;
        }
        Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        Map<String, String> data = remoteMessage.getData();

        if (data.containsKey(NotificationSender.DEVICE_DELETED)) {
            Log.d(TAG, "deleting Device");
            DeviceCache.removeDevice(getApplicationContext());
            return;
        }
        AudioManager audioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //remote is sending volumes to set for this device
        if (data.containsKey(DeviceVolumes.MEDIA_VOLUME)) {
            Log.d(TAG, "setting volumes");
            DeviceVolumes volumes = new DeviceVolumes(data);
            VolumeHelper.setVolumes(audioMgr, volumes);
            //write updated volumes back to db
            updateDeviceVolumesInDatabase(VolumeHelper.getVolumes(audioMgr).toMap());
        }
        //remote is requesting current volumes for this device
        else if (data.containsKey(NotificationSender.VOLUME_REQUEST)) {
            Log.d(TAG, "retrieving volumes");
            DeviceVolumes volumes = VolumeHelper.getVolumes(audioMgr);
            updateDeviceVolumesInDatabase(volumes.toMap());
        }
        else
        {
            Log.d(TAG, "doing nothing");
        }
    }

    private void updateDeviceVolumesInDatabase(Map<String, Object> volumes) {
        Context appContext = getApplicationContext();
        String userId = DeviceCache.getUserId(appContext);
        String deviceId = DeviceCache.getDeviceId(appContext);
        //write the current volume values to the DB location for this device
        if (!TextUtils.isEmpty(userId) && !TextUtils.isEmpty(deviceId)) {
            //use string formatter w/ string resource for consistent database reference
            DatabaseReference dbRef = FirebaseDatabase.getInstance()
                    .getReference(getString(R.string.fbdb_device_path, userId) + deviceId);
            dbRef.updateChildren(volumes);
        }
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }
}
