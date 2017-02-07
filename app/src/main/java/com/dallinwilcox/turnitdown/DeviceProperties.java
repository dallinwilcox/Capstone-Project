package com.dallinwilcox.turnitdown;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dallinwilcox.turnitdown.data.Device;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceProperties extends AppCompatActivity {
    private Device device;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_properties);
    }

}
