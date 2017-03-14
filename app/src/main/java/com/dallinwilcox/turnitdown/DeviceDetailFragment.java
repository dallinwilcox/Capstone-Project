package com.dallinwilcox.turnitdown;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dallinwilcox.turnitdown.data.Device;
import com.dallinwilcox.turnitdown.databinding.DeviceDetailBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

/**
 * A fragment representing a single Device detail screen.
 * This fragment is either contained in a {@link DeviceListActivity}
 * in two-pane mode (on tablets) or a {@link DeviceDetailActivity}
 * on handsets.
 */
public class DeviceDetailFragment extends Fragment {

    /**
     * The dummy content this fragment is presenting.
     */
    private Device device;
    private DatabaseReference dbRef;
    private static final String TAG = "DeviceDetailFragment";
    private DeviceDetailBinding binding;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DeviceDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getArguments().containsKey(Device.DEVICE_EXTRA) || getArguments().getParcelable(Device.DEVICE_EXTRA) == null) {
            throw new IllegalArgumentException("Must pass Device object with key DEVICE_EXTRA");
        }
        device = getArguments().getParcelable(Device.DEVICE_EXTRA);
        dbRef = FirebaseDatabase.getInstance().getReference("devices/" + device.getUser() + "/" + device.getId());
        ValueEventListener volumeListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                device = dataSnapshot.getValue(Device.class);
                //not sure if need to call binding.setDevice(device) or binding.notifyChange()
            }

            //TODO clean up listener w/ fragment lifecycle
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                //TODO consider displaying error to the user.
            }
        };
        dbRef.addValueEventListener(volumeListener);

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(device.getName());
        }
    }

    //referenced http://stackoverflow.com/a/34719627/2169923
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.device_detail, container, false);
        View rootView = binding.getRoot();
        binding.setDevice(device);
        return rootView;
    }

    @Override
    public void onPause() {
        sendNotification(device.getVolumes().toMap());
        super.onPause();
    }

    private void sendNotification(Map<String, Object> data) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance()
                .getReference("/notify/" + device.getId());
        dbRef.setValue(data);
        //TODO ensure permissions are set correctly
    }

}
