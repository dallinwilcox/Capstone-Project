package com.dallinwilcox.turnitdown;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.dallinwilcox.turnitdown.data.Device;
import com.dallinwilcox.turnitdown.databinding.ActivityDevicePropertiesBinding;
import com.dallinwilcox.turnitdown.inf.DeviceCache;
import com.dallinwilcox.turnitdown.inf.NotificationSender;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

//referenced https://developer.android.com/topic/libraries/data-binding/
//referenced http://www.vogella.com/tutorials/AndroidDatabinding/article.html

public class DevicePropertiesActivity extends AppCompatActivity {
    public static final String DEVICE_PROPERTIES_ACTIVITY = "DevicePropActivity";
    public static final String DELETE_DEVICE = "DeleteDevice";

    private Device device;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(DEVICE_PROPERTIES_ACTIVITY, "starting");
        ActivityDevicePropertiesBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_device_properties);
        device = getIntent().getParcelableExtra(Device.DEVICE_EXTRA);
        binding.setDevice(device);
        if (getIntent().getBooleanExtra(DELETE_DEVICE, false)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton(R.string.action_delete, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    deleteDevice();
                    finish();
                }
            });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //no-op, potentially could choose to finish the activity at this point.
                    return;
                }
            });
            builder.setMessage(getString(R.string.delete_dialog_message, device.getName()));
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    @Override
    protected void onPause() {
        updateDB();
        super.onPause();
    }

    public static Intent getEditIntent(Context context, Device device)
    {
        Log.d(DEVICE_PROPERTIES_ACTIVITY, "creatingIntent");
        return new Intent(context, DevicePropertiesActivity.class).putExtra(Device.DEVICE_EXTRA, device);
    }

    private void updateDB()
    {
        if (null == device) {
            return;
        }
        Context appContext = getApplicationContext();
        //guard clause to handle first write which pushes to create new device id
        if (TextUtils.isEmpty(device.getId())) {
            //use string formatter w/ string resource for consistent database reference
            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(
                    getString(R.string.fbdb_device_path, device.getUser()));
            //have the DB generate a new DeviceId
            DatabaseReference newDeviceRef = dbRef.push();
            String deviceId= newDeviceRef.getKey();
            //set the id on the object and write it to the DB
            device.setId(deviceId);
            newDeviceRef.setValue(device);
            //Cache a reference so we know which device this is
            DeviceCache.writeUserId(appContext, device.getUser());
            DeviceCache.writeDeviceId(appContext, deviceId);
            return;
        }
        //otherwise just write it to the db
        //use string formatter w/ string resource for consistent database reference
        FirebaseDatabase.getInstance()
                .getReference(getString(R.string.fbdb_device_path, device.getUser()) + device.getId())
                .setValue(device);
    }
    private void deleteDevice()
    {
        //Remove the device from the db
        FirebaseDatabase.getInstance()
                .getReference(
                        getString(R.string.fbdb_device_path, device.getUser()) + device.getId())
        .removeValue();

        //Send notification to the affected device to delete its cached deviceId
        Map<String, Object> deletePayload = new HashMap<String, Object>();
        deletePayload.put(NotificationSender.DEVICE_DELETED, "");
        NotificationSender.sendNotification(device, deletePayload);
        //prevent re-writing to the db by nulling device
        device = null;
    }
    public static Intent getDeleteIntent(Context appContext, Device device) {
        return getEditIntent(appContext, device).putExtra(DELETE_DEVICE, true);
    }
}
