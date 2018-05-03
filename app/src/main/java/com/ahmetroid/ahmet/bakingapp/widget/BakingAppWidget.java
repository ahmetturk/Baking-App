package com.ahmetroid.ahmet.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.ahmetroid.ahmet.bakingapp.R;
import com.ahmetroid.ahmet.bakingapp.ui.MainActivity;
import com.ahmetroid.ahmet.bakingapp.utils.PrefsUtil;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    new Intent(context, MainActivity.class), 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);

            views.setTextViewText(R.id.widget_textview_title, PrefsUtil.getWidgetTitle(context));

            views.setRemoteAdapter(R.id.widget_listview_ingredients,
                    BakingAppRemoteViewsService.getIntent(context));

            views.setPendingIntentTemplate(R.id.widget_listview_ingredients, pendingIntent);

            views.setOnClickPendingIntent(R.id.widget_parent_layout, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        updateAppWidget(context, appWidgetManager, appWidgetIds);
    }
}

