package com.dallinwilcox.turnitdown.services;

import android.app.Service;
import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

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
            int musicVol = audioMgr.getStreamVolume(AudioManager.STREAM_MUSIC);
            int alarmVol = audioMgr.getStreamVolume(AudioManager.STREAM_ALARM);
            int ringVol = audioMgr.getStreamVolume(AudioManager.STREAM_RING);
            int notificationVol = audioMgr.getStreamVolume(AudioManager.STREAM_NOTIFICATION);

        }
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }
}
