package com.dallinwilcox.turnitdown;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hello, World!");
        adapterDeviceList = new ArrayList<>();
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
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
        holder.mIdView.setText(adapterDeviceList.get(position).getId());
        holder.mContentView.setText(adapterDeviceList.get(position).getName());
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
        public final TextView mIdView;
        public final TextView mContentView;
        public Device device;

        public DeviceListViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
