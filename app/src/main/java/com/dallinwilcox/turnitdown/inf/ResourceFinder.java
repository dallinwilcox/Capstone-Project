package com.dallinwilcox.turnitdown.inf;

import android.content.Context;

import com.dallinwilcox.turnitdown.R;
import com.dallinwilcox.turnitdown.data.Device;
import com.dallinwilcox.turnitdown.data.DeviceDescription;

/**
 * Created by dcwilcox on 3/30/2017.
 */

public class ResourceFinder {
    public static DeviceDescription findResources(Context context, @Device.DeviceType int deviceType)
    {
        //since context is only used to retrieve String array, I might consider passing in
        //device_types_array instead
        String[] deviceTypes = context.getResources().getStringArray(R.array.device_types_array);
        int iconResourceId = R.drawable.ic_smartphone_black_24dp;
        String contentDescription = deviceTypes[Device.PHONE];
        switch (deviceType) {
            case Device.WATCH:
                iconResourceId = R.drawable.ic_watch_black_24dp;
                contentDescription = deviceTypes[Device.WATCH];
                break;
            case Device.TV:
                iconResourceId = R.drawable.ic_tv_black_24dp;
                contentDescription = deviceTypes[Device.TV];
                break;
            case Device.TABLET:
                iconResourceId = R.drawable.ic_tablet_black_24dp;
                contentDescription = deviceTypes[Device.TABLET];
                break;
            case Device.PHONE:
            default:
                iconResourceId = R.drawable.ic_smartphone_black_24dp;
                contentDescription = deviceTypes[Device.PHONE];
        }
        return new DeviceDescription(iconResourceId, contentDescription);
    }
}
