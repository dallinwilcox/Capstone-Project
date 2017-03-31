package com.dallinwilcox.turnitdown.services;

import android.app.Service;
import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by dcwilcox on 3/30/2017.
 */

public class DeviceListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return null;
    }
}
