package com.dallinwilcox.turnitdown.inf;

import android.content.Context;

import com.dallinwilcox.turnitdown.R;

/**
 * Created by dcwilcox on 2/17/2017.
 */

public class DeviceAttributes {

    public static boolean isTablet(Context context)
    {
        return context.getResources().getBoolean(R.bool.isTablet);
    }
}
