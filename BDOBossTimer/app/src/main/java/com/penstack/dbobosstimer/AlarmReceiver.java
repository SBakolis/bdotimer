package com.penstack.dbobosstimer;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver
{
    String textTitle;
    String textContent;
    int notification_icon;

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        // todo
        // Toast.makeText(arg0, "I'm running", Toast.LENGTH_SHORT).show();

    }
}
