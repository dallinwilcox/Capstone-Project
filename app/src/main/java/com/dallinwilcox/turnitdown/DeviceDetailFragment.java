package com.dallinwilcox.turnitdown;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dallinwilcox.turnitdown.data.Device;
import com.dallinwilcox.turnitdown.databinding.DeviceDetailBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        DeviceDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.device_detail, container, false);
        View rootView = binding.getRoot();
        binding.setDevice(device);
        return rootView;
    }
}
