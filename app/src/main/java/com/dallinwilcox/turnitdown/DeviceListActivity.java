package com.dallinwilcox.turnitdown;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dallinwilcox.turnitdown.data.Device;
import com.dallinwilcox.turnitdown.inf.DeviceAttributes;
import com.dallinwilcox.turnitdown.inf.OnItemClick;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An activity representing a list of Devices. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link DeviceDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 *
 * Referenced:
 * https://github.com/firebase/FirebaseUI-Android/blob/master/auth/README.md
 * https://www.androidtutorialpoint.com/firebase/firebase-database-tutorial/
 */

public class DeviceListActivity extends AppCompatActivity implements OnItemClick {
    public static final String IDP_RESPONSE = "IdpResponse";
    @BindView(R.id.device_list) RecyclerView deviceList;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.toolbar) Toolbar toolbar;
    private boolean isTablet;
    private final String TAG = "DeviceListActivity";
    private DeviceListAdapter deviceListAdapter;
    DatabaseReference dbRef;
    DatabaseReference deviceRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        ButterKnife.bind(this);

        dbRef = FirebaseDatabase.getInstance().getReference();
        deviceRef = dbRef.child("device");
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent enrollDeviceIntent = DevicePropertiesActivity.createIntent(DeviceListActivity.this.getApplicationContext(), new Device());
               DeviceListActivity.this.startActivity(enrollDeviceIntent);
            }
        });

        assert deviceList != null;
        deviceListAdapter = new DeviceListAdapter();
        deviceList.setAdapter(deviceListAdapter);
        deviceListAdapter.setItemClick(this);
        isTablet = DeviceAttributes.isTablet(getApplicationContext());
    }

    @Override
        protected void onStart() {
            super.onStart();
            FirebaseAuth auth = FirebaseAuth.getInstance();
            if (null != auth.getCurrentUser()) {
                // already signed in
            }
            else {
                startActivity( new Intent (getApplicationContext(), AuthActivity.class));
            }
    }

    public static Intent createIntent (Context context, IdpResponse response)
    {
        return new Intent (context, DeviceListActivity.class).putExtra(IDP_RESPONSE, response);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_auth, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_signOut) {
            FirebaseAuth.getInstance().signOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(int position) {
        if (isTablet) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(Device.DEVICE_EXTRA, deviceListAdapter.get(position));
            DeviceDetailFragment fragment = new DeviceDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.device_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(getApplicationContext(), DeviceDetailActivity.class);
            intent.putExtra(Device.DEVICE_EXTRA, deviceListAdapter.get(position));
            startActivity(intent);
        }
    }

}
