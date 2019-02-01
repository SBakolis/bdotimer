package com.penstack.dbobosstimer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;

public class BootReceiver extends BroadcastReceiver
{

    final String PREFS_NAME = "BDO_TIMER_PREFS";

    public void onReceive(Context context, Intent intent)
    {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            // Set the alarm here.
            int aOff;
            ArrayList<Boss> B = new ArrayList<Boss>();
            String jsonEU = preferences.getString("EuList", null);
            String jsonNA = preferences.getString("NaList", null);
            String jsonSEA = preferences.getString("SeaList", null);
            Type type = new TypeToken<ArrayList<Boss>>() {}.getType();
            Gson gson = new Gson();
            ArrayList<Boss> BossEU = gson.fromJson(jsonEU, type);
            ArrayList<Boss> BossNA = gson.fromJson(jsonNA, type);
            ArrayList<Boss> BossSEA = gson.fromJson(jsonSEA, type);
            if (preferences.getBoolean("EU", false))
            {
                aOff = 1;
                B.addAll(BossEU);
            }
            else if (preferences.getBoolean("SEA", false))
            {
                aOff = 8;
                B.addAll(BossSEA);
            }
            else
            {
                aOff = -8;
                B.addAll(BossNA);
            }
            ArrayList<Boss> ALARM_BOSS = new ArrayList<>();
            for (int q = 0; q < B.size(); q++)
            {

                if (preferences.getBoolean("Kutum", false) && B.get(q).getBossName().equals("Kutum"))
                {
                    ALARM_BOSS.add(B.get(q));
                }
                else if (preferences.getBoolean("Karanda", false) && B.get(q).getBossName().equals("Karanda"))
                {
                    ALARM_BOSS.add(B.get(q));
                }
                else if (preferences.getBoolean("Nouver", false) && B.get(q).getBossName().equals("Nouver"))
                {
                    ALARM_BOSS.add(B.get(q));
                }
                else if (preferences.getBoolean("Kzarka", false) && B.get(q).getBossName().equals("Kzarka"))
                {
                    ALARM_BOSS.add(B.get(q));
                }
                else if (preferences.getBoolean("Quint", false) && B.get(q).getBossName().equals("Quint"))
                {
                    ALARM_BOSS.add(B.get(q));
                }
                else if (preferences.getBoolean("Vell", false) && !(aOff==8) && B.get(q).getBossName().equals("Vell"))
                {
                    ALARM_BOSS.add(B.get(q));
                }
                else if (preferences.getBoolean("Offin", false) && B.get(q).getBossName().equals("Offin"))
                {
                    ALARM_BOSS.add(B.get(q));
                }
                else if (preferences.getBoolean("Garmoth", false) &&  !(aOff==8) && B.get(q).getBossName().equals("Garmoth"))
                {
                    ALARM_BOSS.add(B.get(q));
                }
            }

            Calendar calendar;
            if (aOff < 0)
            {
                calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT" + aOff));
            }
            else
            {
                calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+" + aOff));
            }
            int dayOfTheWeek, minutes, hourOfTheDay;
            String bossname;
            long timeA = 0;
            if (!ALARM_BOSS.isEmpty())
            {
                for (int p = 0; p < ALARM_BOSS.size(); p++)
                {
                    dayOfTheWeek = ALARM_BOSS.get(p).getBossDay();
                    minutes = ALARM_BOSS.get(p).getBossMin();
                    hourOfTheDay = ALARM_BOSS.get(p).getBossHour();
                    bossname = ALARM_BOSS.get(p).getBossName();
                    Calendar MyCalendar = Calendar.getInstance();
                    // Enable a receiver
                    ComponentName receiver = new ComponentName(context, BootReceiver.class);

                    PackageManager pm = context.getPackageManager();

                    pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

                    int interval = 1000 * 60 * 60 * 24 * 7;
                    if (dayOfTheWeek == 7)
                    {
                        dayOfTheWeek = 1;
                    }
                    else
                    {
                        dayOfTheWeek++;
                    }
                    calendar.set(Calendar.DAY_OF_WEEK, dayOfTheWeek);
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfTheDay);
                    calendar.set(Calendar.MINUTE, minutes);
                    long calendarTimeInMillis = calendar.getTimeInMillis();
                    if (calendar.before(MyCalendar))
                    {
                        calendarTimeInMillis += 608400000; //calendar.setTimeInMillis(calendar.getTimeInMillis() + interval);
                    }
                    calendarTimeInMillis -= 600000;
                    Log.d("timeinmillis", "" + calendarTimeInMillis);
                    Intent intent2 = new Intent(context, AlarmReceiver.class);
                    intent2.putExtra("id", p);
                    intent2.putExtra("day", dayOfTheWeek);
                    intent2.putExtra("hour", hourOfTheDay);
                    intent2.putExtra("minute", minutes);
                    intent2.putExtra("name", bossname);
                    intent2.putExtra("image", 0);
                    intent2.putExtra("offset", aOff);
                    intent2.putExtra("FirsTime", calendarTimeInMillis);
                    intent2.putExtra("time", timeA);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, p, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager manager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    {
                        manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendarTimeInMillis, pendingIntent);
                    }
                    else
                    {
                        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendarTimeInMillis, interval, pendingIntent);
                    }
                }
            }
        }
    }
}

