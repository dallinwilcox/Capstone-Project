package com.dallinwilcox.turnitdown;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.dallinwilcox.turnitdown.data.Device;
import com.dallinwilcox.turnitdown.databinding.ActivityDevicePropertiesBinding;
import com.dallinwilcox.turnitdown.inf.DeviceCache;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//referenced https://developer.android.com/topic/libraries/data-binding/
//referenced http://www.vogella.com/tutorials/AndroidDatabinding/article.html

public class DevicePropertiesActivity extends AppCompatActivity {
    public static final String DEVICE_PROPERTIES_ACTIVITY = "DevicePropActivity";
    private Device device;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(DEVICE_PROPERTIES_ACTIVITY, "starting");
        ActivityDevicePropertiesBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_device_properties);
        device = getIntent().getParcelableExtra(Device.DEVICE_EXTRA);
        binding.setDevice(device);
    }

    @Override
    protected void onPause() {
        updateDB();
        super.onPause();
    }

    public static Intent createIntent (Context context, Device device)
    {
        Log.d(DEVICE_PROPERTIES_ACTIVITY, "creatingIntent");
        return new Intent(context, DevicePropertiesActivity.class).putExtra(Device.DEVICE_EXTRA, device);
    }

    private void updateDB()
    {
        Context appContext = getApplicationContext();
        String deviceId = DeviceCache.getDeviceId(appContext);
        //guard clause to handle first write
        if ( deviceId == "") {
            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("devices/" + device.getUser());

            DatabaseReference newDeviceRef = dbRef.push();
            deviceId= newDeviceRef.getKey();
            newDeviceRef.setValue(device);
            DeviceCache.writeUserId(appContext, device.getUser());
            DeviceCache.writeDeviceId(appContext, deviceId);
            return;
        }
        FirebaseDatabase.getInstance().getReference("devices/" + device.getUser() + "/" + deviceId).setValue(device);
    }
}
