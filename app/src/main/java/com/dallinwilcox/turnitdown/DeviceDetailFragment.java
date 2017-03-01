package com.dallinwilcox.turnitdown;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dallinwilcox.turnitdown.data.Device;

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

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(device.getName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.device_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (null != device) {
            ((TextView) rootView.findViewById(R.id.device_detail)).setText(device.getName());
        }

        return rootView;
    }
}
