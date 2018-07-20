package com.twinforce.trashreminder;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link SimpleWidgetProviderConfigureActivity SimpleWidgetProviderConfigureActivity}
 */
public class SimpleWidgetProvider extends AppWidgetProvider {
    private static final String SYNC_CLICKED    = "automaticWidgetSyncButtonClick";
    /*static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = SimpleWidgetProviderConfigureActivity.loadTitlePref(context, appWidgetId);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.simple_widget_provider);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }*/

    private AppWidgetManager widgetManager;

    DateFormat df = new SimpleDateFormat("dd/MM/yy");
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        widgetManager = appWidgetManager;
       /* // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }*/


        final int count = appWidgetIds.length;
        Log.v("ON UPDATE", ""+count);

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);



        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];
            String date = df.format(new Date());

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.simple_widget);
            remoteViews.setTextViewText(R.id.date, date);

            switch (day){
                case Calendar.MONDAY:
                    remoteViews.setImageViewResource(R.id.imageView, R.drawable.plastic);
                    remoteViews.setTextViewText(R.id.textView2, context.getText(R.string.plastic));
                    remoteViews.setTextViewText(R.id.dayoftheweek, context.getText(R.string.monday));
                    changeTextColor(context, remoteViews, R.color.plastic);
                    break;
                case Calendar.TUESDAY:
                    remoteViews.setImageViewResource(R.id.imageView, R.drawable.rebuig);
                    remoteViews.setTextViewText(R.id.textView2, context.getString(R.string.rebuig));
                    remoteViews.setTextViewText(R.id.dayoftheweek, context.getText(R.string.tuesday));
                    changeTextColor(context, remoteViews, R.color.rebuig);
                    break;
                case Calendar.WEDNESDAY:
                    remoteViews.setImageViewResource(R.id.imageView, R.drawable.organic);
                    remoteViews.setTextViewText(R.id.textView2, context.getString(R.string.organic));
                    remoteViews.setTextViewText(R.id.dayoftheweek, context.getText(R.string.wednesday));
                    changeTextColor(context, remoteViews, R.color.organic);
                    break;
                case Calendar.THURSDAY:
                    remoteViews.setImageViewResource(R.id.imageView, R.drawable.paper);
                    remoteViews.setTextViewText(R.id.textView2, context.getString(R.string.papervidre));
                    remoteViews.setTextViewText(R.id.dayoftheweek, context.getText(R.string.thursday));
                    changeTextColor(context, remoteViews, R.color.paper);
                    break;
                case Calendar.FRIDAY:
                    remoteViews.setImageViewResource(R.id.imageView, R.drawable.organic);
                    remoteViews.setTextViewText(R.id.textView2, context.getString(R.string.organic));
                    remoteViews.setTextViewText(R.id.dayoftheweek, context.getText(R.string.friday));
                    changeTextColor(context, remoteViews, R.color.organic);
                    break;
                case Calendar.SATURDAY:
                    remoteViews.setImageViewResource(R.id.imageView, R.drawable.res);
                    remoteViews.setTextViewText(R.id.textView2, context.getString(R.string.res));
                    remoteViews.setTextViewText(R.id.dayoftheweek, context.getText(R.string.saturday));
                    changeTextColor(context, remoteViews, R.color.res);
                    break;
                case Calendar.SUNDAY:
                    remoteViews.setImageViewResource(R.id.imageView, R.drawable.organic);
                    remoteViews.setTextViewText(R.id.textView2, context.getString(R.string.organic));
                    remoteViews.setTextViewText(R.id.dayoftheweek, context.getText(R.string.sunday));
                    changeTextColor(context, remoteViews, R.color.organic);
                    break;
            }

            Intent intentSync = new Intent(context, SimpleWidgetProvider.class);
            intentSync.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            PendingIntent pendingSync = PendingIntent.getBroadcast(context,0, intentSync, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.b_Reload,pendingSync);

            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    @TargetApi(23)
    private void changeTextColor(Context context, RemoteViews rv, int resourceColor){
        rv.setTextColor(R.id.textView2, context.getColor(resourceColor) );
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        super.onReceive(context, intent);
        Log.v("ON RECIVE", "Button pressed");

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName thisAppWidget = new ComponentName(context.getPackageName(), SimpleWidgetProvider.class.getName());
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);

            onUpdate(context, appWidgetManager, appWidgetIds);

    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    /*@Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            SimpleWidgetProviderConfigureActivity.deleteTitlePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }*/
}

