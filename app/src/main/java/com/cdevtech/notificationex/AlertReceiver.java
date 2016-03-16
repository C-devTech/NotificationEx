package com.cdevtech.notificationex;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by bills on 3/16/2016.
 */
public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        createNotification(context, "Times Up", "5 Seconds Has Passed", "Alert");
    }

    public void createNotification(Context context, String msg, String msgText, String msgAlert) {

        // Define an Intent and an action to perform with it by another application
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                new Intent(context, MainActivity.class),
                0);

        // Build a notification
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(context)
                .setContentTitle(msg)
                .setContentText(msgText)
                .setTicker(msgAlert)
                .setSmallIcon(R.drawable.cdev24);

        // Defines the Intent to fire when the notification is clicked
        notifyBuilder.setContentIntent(pendingIntent);

        // Set the default notification option
        // DEFAULT_SOUND : Make sound
        // DEFAULT_VIBRATE : Vibrate
        // DEFAULT_LIGHTS : Use the default light notification
        notifyBuilder.setDefaults(Notification.DEFAULT_SOUND);

        // Auto cancels the notification when clicked on i nthe task bar
        notifyBuilder.setAutoCancel(true);

        // Gets a NotificationManager which is used to notify the user of the
        // background event
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Post the notification
        notificationManager.notify(1, notifyBuilder.build());
    }
}
