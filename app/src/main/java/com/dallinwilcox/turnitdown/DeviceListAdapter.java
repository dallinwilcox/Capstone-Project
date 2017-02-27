package com.dallinwilcox.turnitdown;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dallinwilcox.turnitdown.data.Device;
import com.dallinwilcox.turnitdown.inf.OnItemClick;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by dcwilcox on 2/4/2017.
 * referenced:
 *  https://firebase.google.com/docs/database/android/start/
 *  https://howtofirebase.com/firebase-data-modeling-939585ade7f4
 */

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.DeviceListViewHolder> {
    public static final String TAG = "DeviceListAdapter";
    private ArrayList<Device> adapterDeviceList;
    private OnItemClick itemClick;


    public DeviceListAdapter() {
        //Init DB
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //example DB Write
        DatabaseReference myRef = database.getReference("devices");
        adapterDeviceList = new ArrayList<>();
        // Read from the database with a listener
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Device device = dataSnapshot.getValue(Device.class);
                Log.d(TAG, "Device is: " + device);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    public DeviceListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device_list_content, parent, false);
        return new DeviceListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DeviceListViewHolder holder, int position) {
        holder.device = adapterDeviceList.get(position);
        holder.deviceName.setText(holder.device.getName());
        String[] deviceTypes = holder.deviceIcon.getContext().getResources().getStringArray(R.array.device_types_array);
        switch (holder.device.getDeviceType()) {
            case Device.WATCH:
                holder.deviceIcon.setImageResource(R.drawable.ic_watch_black_24dp);
                holder.deviceIcon.setContentDescription(deviceTypes[Device.WATCH]);
                break;
            case Device.TV:
                holder.deviceIcon.setImageResource(R.drawable.ic_tv_black_24dp);
                holder.deviceIcon.setContentDescription(deviceTypes[Device.TV]);
                break;
            case Device.TABLET:
                holder.deviceIcon.setImageResource(R.drawable.ic_tablet_black_24dp);
                holder.deviceIcon.setContentDescription(deviceTypes[Device.TABLET]);
                break;
            case Device.PHONE:
            default:
                holder.deviceIcon.setImageResource(R.drawable.ic_smartphone_black_24dp);
                holder.deviceIcon.setContentDescription(deviceTypes[Device.PHONE]);
        }
    }

    @Override
    public int getItemCount() {
        return adapterDeviceList.size();
    }

    public Device get(int position)
    {
        return adapterDeviceList.get(position);
    }
    public void setItemClick(OnItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public class DeviceListViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView deviceIcon;
        public final TextView deviceName;
        public Device device;

        public DeviceListViewHolder(View view) {
            super(view);
            mView = view;
            deviceIcon = (ImageView) view.findViewById(R.id.device_icon);
            deviceName = (TextView) view.findViewById(R.id.device_name);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + deviceName.getText() + "'";
        }
    }
}
