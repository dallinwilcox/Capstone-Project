package com.dallinwilcox.turnitdown.services;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.dallinwilcox.turnitdown.R;

/**
 * Created by dcwilcox on 3/30/2017.
 */

public class DeviceListWidgetService extends RemoteViewsService {
    private static final String TAG = "DeviceListWidgetService";
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new DeviceListRemoteViewsFactory(this.getApplicationContext(), intent);
    }
    class DeviceListRemoteViewsFactory implements RemoteViewsFactory {
        private Context context;
        private int appWidgetId;
        private int deviceCount;

        public DeviceListRemoteViewsFactory(Context context, Intent intent)
        {
            this.context = context;
            appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        @Override
        public void onCreate() {
            Log.d(TAG, "created");
            //Init Firebase Query
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {
            //Disconnect Firebase Listener
        }

        @Override
        public int getCount() {
            return deviceCount;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            Log.d(TAG, "getViewAt: " + position);
//            cursor.moveToPosition(position);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.device_item);
//            Log.d(TAG, "setting home_name to " + cursor.getString(scoresAdapter.COL_HOME));
//            remoteViews.setTextViewText(R.id.home_name, cursor.getString(scoresAdapter.COL_HOME));
//            remoteViews.setTextViewText(R.id.away_name, cursor.getString(scoresAdapter.COL_AWAY));
//            remoteViews.setTextViewText(R.id.score_textview, Utilies.getScores(cursor.getInt(scoresAdapter.COL_HOME_GOALS),cursor.getInt(scoresAdapter.COL_AWAY_GOALS)));
//            remoteViews.setTextViewText(R.id.data_textview, cursor.getString(scoresAdapter.COL_MATCHTIME));
            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
