package com.ahmetroid.ahmet.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

public class UpdateWidgetService extends IntentService {

    private static final String ACTION_UPDATE_WIDGETS = "com.example.ahmet.bakingapp.widget.action.update_widgets";
    private static final String EXTRA_INGREDIENTS = "com.example.ahmet.bakingapp.widget.extra.RECIPE_ID";

    public UpdateWidgetService() {
        super("UpdateWidgetService");
    }

    public static void startActionUpdateWidgets(Context context, String ingredients) {
        Intent intent = new Intent(context, UpdateWidgetService.class);
        intent.putExtra(EXTRA_INGREDIENTS, ingredients);
        intent.setAction(ACTION_UPDATE_WIDGETS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_WIDGETS.equals(action)) {
                final String ingredients = intent.getStringExtra(EXTRA_INGREDIENTS);
                handleActionUpdateWidgets(ingredients);
            }
        }
    }

    private void handleActionUpdateWidgets(String ingredients) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidget.class));

        BakingAppWidget.updateAppWidget(this, appWidgetManager, appWidgetIds, ingredients);
    }
}
