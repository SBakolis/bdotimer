package com.penstack.dbobosstimer;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import static android.app.AlarmManager.RTC_WAKEUP;
import static android.content.Context.ALARM_SERVICE;

public class AlarmReceiver extends BroadcastReceiver
{
    String textTitle;
    String textContent;
    int notification_icon;

    @Override
    public void onReceive(Context context, Intent intent) {
        // todo
        // Toast.makeText(arg0, "I'm running", Toast.LENGTH_SHORT).show();
        if (intent.getAction() != null && context != null) {
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
                // Set the alarm here.


                Settings.startAlarm(context,AlarmReceiver.class,intent.getIntExtra("id",0),intent.getIntExtra("day",0),intent.getIntExtra("hour",0),intent.getIntExtra("minute",0),intent.getStringExtra("name"),intent.getIntExtra("offset",0),intent.getIntExtra("image",0),intent.getLongExtra("time",0));

            }



        }

        Settings.notificationSetup(context, ""+intent.getStringExtra("name"), ""+intent.getStringExtra("name")+" is about to spawn", intent.getIntExtra("id", 0),intent.getIntExtra("image",0));
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

               long TimeinMillies =intent.getLongExtra("FirsTime", 0)+ 604800000;

              Log.d("hey",""+TimeinMillies);
            Settings.startAlarm(context,AlarmReceiver.class,intent.getIntExtra("id",0),intent.getIntExtra("day",0),intent.getIntExtra("hour",0),intent.getIntExtra("minute",0),intent.getStringExtra("name"),intent.getIntExtra("offset",0),intent.getIntExtra("image",0),TimeinMillies);
            }
        }


}
