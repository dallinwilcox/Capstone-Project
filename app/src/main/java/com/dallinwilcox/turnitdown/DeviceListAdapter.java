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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
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
 *  https://github.com/firebase/quickstart-android/tree/master/database
 */

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.DeviceListViewHolder> {
    private static final String TAG = "DeviceListAdapter";
    private ArrayList<Device> adapterDeviceList;
    private ArrayList<String> deviceIndexList;
    private ChildEventListener adapterChildEventListener;
    private OnItemClick itemClick;
    private DatabaseReference databaseReference;

    public DeviceListAdapter() {
        //Init DB and reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //TODO Consider passing in userId
        //TODO make path reference a constant for re-use and better maintenance
        databaseReference = database.getReference("devices/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());
        adapterDeviceList = new ArrayList<>();
        deviceIndexList = new ArrayList<>();
        // Read from the database with a listener
        adapterChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Device device = dataSnapshot.getValue(Device.class);
                deviceIndexList.add(dataSnapshot.getKey());
                adapterDeviceList.add(device);
                notifyItemInserted(adapterDeviceList.size() - 1);
                Log.d(TAG, "Device is: " + device);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                //update device that changed
                Device changedDevice = dataSnapshot.getValue(Device.class);
                String deviceKey = dataSnapshot.getKey();
                int deviceIndex = deviceIndexList.indexOf(deviceKey);
                if (deviceIndex > -1){
                    adapterDeviceList.set(deviceIndex, changedDevice);
                    notifyItemChanged(deviceIndex);
                }
                else{
                    Log.w(TAG, "onChildChanged:unknown_device:" + deviceKey);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
                String deviceKey = dataSnapshot.getKey();
                int deviceIndex = deviceIndexList.indexOf(deviceKey);
                if (deviceIndex > -1) {
                    // Remove data from the list
                    deviceIndexList.remove(deviceIndex);
                    adapterDeviceList.remove(deviceIndex);
                    notifyItemRemoved(deviceIndex);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());
                Device movedDevice = dataSnapshot.getValue(Device.class);
                String deviceKey = dataSnapshot.getKey();
                int fromPosition = deviceIndexList.indexOf(deviceKey);
                //default -1 for when previousChildName is null which means moving to the front
                int predecessorIndex = -1;

                //guard clauses allow us to bail out as soon as we know it's not viable
                //without doing unnecessary work
                if (fromPosition < 0)
                {
                    Log.w(TAG, "onChildMoved:unknown_movedDevice:" + deviceKey);
                    //TODO handle user error
                    return;
                }
                //if previousChildName is null, don't bother searching for it, predecessorIndex = -1;
                if (null != previousChildName){
                    predecessorIndex = deviceIndexList.indexOf(previousChildName);
                    //if predecessorIndex is still negative one at this point, but it is defined,
                    //we have an error, it should be in the list, but it's not.
                    if (predecessorIndex < 0)
                    {
                        Log.w(TAG, "onChildMoved:unknown_predecessorDevice:" + previousChildName);
                        //TODO handle user error
                        return;
                    }
                }
                //we're moving to the spot behind the previousChild
                //if previousChild is null, it means we go to 0
                int toPosition = predecessorIndex + 1;

                //remove first to avoid allocation from growing the arrays
                //handle shifting indices caused by modifying the lists
                //only shift if we're moving to a higher index to account for removal.
                int shift = ((toPosition > fromPosition) ? 1 : 0);
                adapterDeviceList.remove(fromPosition);
                deviceIndexList.remove(fromPosition);
                adapterDeviceList.add(toPosition - shift, movedDevice);
                deviceIndexList.add(toPosition - shift, deviceKey);
                //after re-inserting, the indices are back to where we would expect
                notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
                //TODO make human readable error visible to the user with error.getMessage()
            }
        };
        databaseReference.addChildEventListener(adapterChildEventListener);
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

    public class DeviceListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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

        @Override
        public void onClick(View v) {
            if (itemClick != null) {
                itemClick.onItemClicked(getAdapterPosition());
                //http://stackoverflow.com/questions/32323548/passing-data-from-on-click-function-of-my-recycler-adaptor
                //http://stackoverflow.com/a/27886776
            }
        }
    }
    public void removeListener() {
        if (adapterChildEventListener != null) {
            databaseReference.removeEventListener(adapterChildEventListener);
        }
    }
}
