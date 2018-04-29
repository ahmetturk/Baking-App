package com.example.ahmet.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.ahmet.bakingapp.R;
import com.example.ahmet.bakingapp.ui.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int [] appWidgetIds, String ingredients) {
        for (int appWidgetId : appWidgetIds) {
            // Create an Intent to launch MainActivity when clicked
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
            views.setTextViewText(R.id.widget_textview_ingredients, ingredients);

            // Widgets allow click handlers to only launch pending intents
            views.setOnClickPendingIntent(R.id.widget_textview_ingredients, pendingIntent);

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        UpdateWidgetService.startActionUpdateWidgets(context, context.getString(R.string.widget_text));
    }
}

