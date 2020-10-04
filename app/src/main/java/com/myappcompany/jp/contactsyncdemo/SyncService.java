package com.myappcompany.jp.contactsyncdemo;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class SyncService extends Service {

    private static final Object sSyncAdapterLock = new Object();
    private static SyncAdapter mSyncAdapter = null;

    @Override
    public void onCreate() {
        Log.i("SYNC SERVICE", "Created");
        synchronized (sSyncAdapterLock) {
            if(mSyncAdapter == null) {
                mSyncAdapter = new SyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
       Log.i("SYNC SERVICE", "Binded");
       return mSyncAdapter.getSyncAdapterBinder();
    }
}