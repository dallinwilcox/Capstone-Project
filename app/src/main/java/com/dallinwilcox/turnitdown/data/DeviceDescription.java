package com.dallinwilcox.turnitdown.data;

import android.support.annotation.DrawableRes;

/**
 * Created by dcwilcox on 3/30/2017.
 */

public class DeviceDescription {
    int iconResourceId;
    String description;

    public DeviceDescription(@DrawableRes int iconResourceId, String description) {
        this.iconResourceId = iconResourceId;
        this.description = description;
    }

    public int getIconResourceId() {
        return iconResourceId;
    }

    public String getDescription() {
        return description;
    }
}
