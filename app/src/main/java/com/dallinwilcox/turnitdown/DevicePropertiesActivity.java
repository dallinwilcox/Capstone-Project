package com.dallinwilcox.turnitdown;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dallinwilcox.turnitdown.data.Device;
import com.dallinwilcox.turnitdown.databinding.ActivityDevicePropertiesBinding;
public class DevicePropertiesActivity extends AppCompatActivity {
    private Device device;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDevicePropertiesBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_device_properties);
        device = getIntent().getParcelableExtra(Device.DEVICE_EXTRA);
    }

    public static Intent createIntent (Context context, Device device)
    {
        return new Intent(context, DeviceListActivity.class).putExtra(Device.DEVICE_EXTRA, device);
    }
}
