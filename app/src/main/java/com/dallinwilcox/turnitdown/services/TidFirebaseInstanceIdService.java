package com.dallinwilcox.turnitdown.services;

import android.content.Context;
import android.util.Log;

import com.dallinwilcox.turnitdown.R;
import com.dallinwilcox.turnitdown.inf.DeviceCache;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by dcwilcox on 2/25/2017.
 */
//referenced https://github.com/firebase/quickstart-android
public class TidFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static final String TAG = "TidFbInstanceIdService";
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        updateDeviceId(refreshedToken);
    }

    private void updateDeviceId(String refreshedToken) {
        Context appContext = getApplicationContext();
        String userId = DeviceCache.getUserId(appContext);
        String deviceId = DeviceCache.getDeviceId(appContext);
        if ("" != userId && "" != deviceId)
        {
            //use string formatter w/ string resource for consistent database reference
            DatabaseReference dbRef = FirebaseDatabase.getInstance()
                    .getReference(getString(R.string.fbdb_device_path, userId) + deviceId + "/id");
            dbRef.setValue(refreshedToken);
        }
    }
}
