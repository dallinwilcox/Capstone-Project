package com.dallinwilcox.turnitdown.services;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.dallinwilcox.turnitdown.R;
import com.dallinwilcox.turnitdown.data.Device;
import com.dallinwilcox.turnitdown.data.DeviceDescription;
import com.dallinwilcox.turnitdown.inf.DeviceCache;
import com.dallinwilcox.turnitdown.inf.ResourceFinder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
        private DatabaseReference dbRef;
        private  ValueEventListener deviceListener;
        private int appWidgetId;
        private ArrayList<Device> deviceList;

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
            dbRef = FirebaseDatabase.getInstance().getReference("devices/" + DeviceCache.getUserId(context));
            Query query = dbRef.orderByChild("id").limitToFirst(5);
            deviceListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    deviceList = new ArrayList<>();
                    // Get List of device objects to use the values to update the UI
                    //referenced http://stackoverflow.com/a/40402958/2169923
                    for (DataSnapshot child: dataSnapshot.getChildren()) {
                        deviceList.add(child.getValue(Device.class));
                    }
                    //notify the widget of the change
                    //referenced //http://stackoverflow.com/a/12907825/2169923
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_device_list);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w(TAG, "onCancelled", databaseError.toException());
                    //TODO consider displaying error to the user somehow, by returning an error view
                }
            };
            query.addValueEventListener(deviceListener);
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {
            //Disconnect Firebase Listener
            dbRef.removeEventListener(deviceListener);
        }

        @Override
        public int getCount() {
            if (null == deviceList){
                return 0;
            }
            return deviceList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            Log.d(TAG, "getViewAt: " + position);
            Device viewDevice = deviceList.get(position);
            DeviceDescription deviceDescription =
                    ResourceFinder.findResources(context, viewDevice.getDeviceType());
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.device_item);
            remoteViews.setImageViewResource(R.id.device_icon, deviceDescription.getIconResourceId());
            //set content description for accessibility
            remoteViews.setContentDescription(R.id.device_icon, deviceDescription.getDescription());
            remoteViews.setTextViewText(R.id.device_name, viewDevice.getName());
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
