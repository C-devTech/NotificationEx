package com.cdevtech.notificationex;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.GregorianCalendar;

// NotificationManager : Allows us to notify the user that something happened in the background
// AlarmManager : Allows you to schedule for your application to do something at a later date
// even if it is in the background
public class MainActivity extends AppCompatActivity {

    // Allows us to notify that something happened in the background
    NotificationManager notificationManager;

    // Used to track notifications
    int notifyId = 33;

    // Used to track if notification is active in the task bar
    boolean isNotifyActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // In the snippet below, clicking the notification opens a new activity that effectively
    // extends the behavior of the notification. In this case there is no need to create an
    // artificial back stack. If necessary to preserve navigation see:
    // http://developer.android.com/training/notify-user/navigation.html
    public void showNotification(View view) {
        // Define an Intent and an action to perform with it by another application
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                new Intent(this, MoreInfoNotification.class),
                PendingIntent.FLAG_UPDATE_CURRENT);

        // Build a notification
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.notify_content_title))
                .setContentText(getString(R.string.notify_content_text))
                .setTicker(getString(R.string.notify_content_ticker))
                .setSmallIcon(R.drawable.cdev24);

        // Puts the PendingIntent into the notification builder
        // Defines the Intent to fire when the notification is clicked
        notifyBuilder.setContentIntent(pendingIntent);

        // Defines the Intent to fire when the notification is clicked
        // Notifications are issued by sending them to the NotificationManager system service.
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Post the notification
        // Builds anonymous Notification object from the builder, and passes it to the
        // NotificationManager
        notificationManager.notify(notifyId, notifyBuilder.build());

        // Used so that we don't stop a notification that has already been stopped
        isNotifyActive = true;
    }

    public void stopNotification(View view) {
        // If the notification is still active, close it
        if (isNotifyActive) {
            notificationManager.cancel(notifyId);
            isNotifyActive = false;
        }
    }

    public void setAlarm(View view) {
        // Define a time value of 5 seconds in the future
        Long alertTime = new GregorianCalendar().getTimeInMillis()+5*1000;

        // Define our intention of executing AlertReceiver
        Intent alertIntent = new Intent(this, AlertReceiver.class);

        // Allows you to schedule for application to do something at a later time/date
        // even if it is in the background or isn't active
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // set() schedules an alarm to trigger
        // Trigger for alertIntent to fire in 5 seconds
        // FLAG_UPDATE_CURRENT : Update the Intent if active
        alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                alertTime,
                PendingIntent.getBroadcast(
                        this,
                        1, alertIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                )
        );

    }
}
