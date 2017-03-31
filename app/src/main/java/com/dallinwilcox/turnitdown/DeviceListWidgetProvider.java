package com.dallinwilcox.turnitdown;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.dallinwilcox.turnitdown.services.DeviceListWidgetService;

/**
 * Created by dcwilcox on 3/30/2017.
 * Referenced
 * https://github.com/dallinwilcox/SuperDuo/blob/master/Football_Scores-master/app/src/main/java/barqsoft/footballscores/FootbalScoresAppWidgetProvider.java
 * http://developer.android.com/guide/topics/appwidgets/index.html
 * and android StackWidget Sample at
 * https://github.com/android/platform_development/tree/master/samples/StackWidget
 */

public class DeviceListWidgetProvider extends AppWidgetProvider {
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;


        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];
            //invoke Widget Service
            Intent serviceIntent =  new Intent(context, DeviceListWidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

            // Create an Intent to launch ExampleActivity
            Intent clickIntent = new Intent(context, DeviceListActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, clickIntent, 0);

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            views.setOnClickPendingIntent(R.id.wrapper, pendingIntent);
            views.setRemoteAdapter(R.id.widget_device_list, serviceIntent);
            views.setEmptyView(R.id.widget_device_list, R.id.empty_view);
            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

}

}
