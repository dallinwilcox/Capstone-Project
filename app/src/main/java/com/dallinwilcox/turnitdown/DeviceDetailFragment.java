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
import com.google.firebase.database.Query;
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
     * The device this fragment is presenting.
     */
    private Device device;
    private DatabaseReference dbRef;
    private static final String TAG = "DeviceDetailFragment";
    private DeviceDetailBinding binding;
    private  ValueEventListener volumeListener;

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

        //use string formatter w/ string resource for consistent database reference
        dbRef = FirebaseDatabase.getInstance().getReference(getString(R.string.fbdb_device_path, device.getUser()));
        Query query = dbRef.orderByChild("id").equalTo(device.getId());
        volumeListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get device object and use the values to update the UI
                DataSnapshot childDevice = dataSnapshot.getChildren().iterator().next();
                device = childDevice.getValue(Device.class);
                Log.d(TAG, "Device updated in DB " + dataSnapshot.getKey() + device.toString() );
                binding.setDevice(device); //update the data binding to refer to the updated object
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                //TODO consider displaying error to the user.
            }
        };
        query.addValueEventListener(volumeListener);

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
        if(null != dbRef)
        {
            dbRef.removeEventListener(volumeListener);
        }
        super.onPause();
    }

    private void sendNotification(Map<String, Object> data) {
        Log.d(TAG, "deviceId = " + device.getId() + " data = " + data);
        DatabaseReference dbRef = FirebaseDatabase.getInstance()
                .getReference("/notify/" + device.getId() + "/data");
        dbRef.setValue(data);
    }

}
