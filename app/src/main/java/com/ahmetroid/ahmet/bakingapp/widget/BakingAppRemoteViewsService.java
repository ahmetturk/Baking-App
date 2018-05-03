package com.ahmetroid.ahmet.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViewsService;

public class BakingAppRemoteViewsService extends RemoteViewsService {

    public static Intent getIntent(Context context) {
        return new Intent(context, BakingAppRemoteViewsService.class);
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BakingAppRemoteViewsFactory(getApplicationContext());
    }
}
