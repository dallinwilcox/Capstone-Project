package com.dallinwilcox.turnitdown.inf;

import android.content.Context;

import com.dallinwilcox.turnitdown.R;

/**
 * Created by dcwilcox on 2/17/2017.
 */

public class DeviceAttributes {

    public static boolean isTablet(Context context)
    {
        //referenced http://stackoverflow.com/questions/9279111/determine-if-the-device-is-a-smartphone-or-tablet
        return context.getResources().getBoolean(R.bool.isTablet);
    }
}
