package com.dallinwilcox.turnitdown;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by dcwilcox on 1/31/2017.
 */

public class TurnItDownApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}

