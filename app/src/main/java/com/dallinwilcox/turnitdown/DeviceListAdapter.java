package com.dallinwilcox.turnitdown;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dallinwilcox.turnitdown.data.Device;
import com.dallinwilcox.turnitdown.inf.OnItemClick;

import java.util.ArrayList;

/**
 * Created by dcwilcox on 2/4/2017.
 */

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.DeviceListViewHolder> {
    private ArrayList<Device> adapterDeviceList;
    private OnItemClick itemClick;

    public DeviceListAdapter() {
        adapterDeviceList = new ArrayList<>();
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
