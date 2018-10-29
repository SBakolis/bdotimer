package com.penstack.dbobosstimer;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

public class Settings extends AppCompatActivity {

    final String PREFS_NAME = "BDO_TIMER_PREFS";
    //final String EUSERVER = "EU";
    //final String NASERVER = "NA";
    final int EUSERVER_CONSTANT = 1;
    final int NASERVER_CONSTANT = 2;
    final String PREF_SERVER_CONSTANT = "0";
    final ArrayList<Boss> NOTIFY_BOSS=new ArrayList<>();
    final Set<String> BossNotify=new HashSet<String>();
    final String PREF_NOTIFY="NotificationList";
    ArrayList<Boss>  BossEU;
    ArrayList<Boss>  BossNA;
    SharedPreferences prefs;
    RadioButton rbEU,rbNa;
    CheckBox CheckKutum,CheckKzarka,CheckKaranda,CheckNouver,CheckQuint,CheckVell,CheckOffin;
    int BossSize,Soffset;
    public ImageView backButton;
    Intent intentMain ;


    static ArrayList<Long> armlist=new ArrayList<>();
    ArrayList<PendingIntent> pi=new ArrayList<>();
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

       BossNA=(ArrayList<Boss>) getIntent().getSerializableExtra("BossDayNAList");
       BossEU=(ArrayList<Boss>) getIntent().getSerializableExtra("BossDayEUList");

         prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        rbEU=(RadioButton)findViewById(R.id.rbEU);
        rbNa=(RadioButton) findViewById(R.id.rbNA);

       intentMain = new Intent(Settings.this, MainActivity.class);

        backButton = (ImageView)findViewById(R.id.settButton);
        backButton.setOnClickListener(new View.OnClickListener(){
           public void onClick(View v) {
               startActivity(intentMain);
           }
        });

        CheckKaranda=(CheckBox) findViewById(R.id.checkKaranda);
        CheckKutum=(CheckBox) findViewById(R.id.checkKutum);
        CheckKzarka=(CheckBox) findViewById(R.id.checkKzarka);
        CheckNouver=(CheckBox) findViewById(R.id.checkNouver);
        CheckQuint=(CheckBox) findViewById(R.id.checkQuint);
        CheckVell=(CheckBox) findViewById(R.id.checkVell);
        CheckOffin=(CheckBox) findViewById(R.id.checkOffin);

       rbEU.setChecked(prefs.getBoolean("EU",false));
       rbNa.setChecked(prefs.getBoolean("NA",false));
       CheckKaranda.setChecked(prefs.getBoolean("Karanda",false));
       CheckKzarka.setChecked(prefs.getBoolean("Kzarka",false));
       CheckKutum.setChecked(prefs.getBoolean("Kutum",false));
       CheckNouver.setChecked(prefs.getBoolean("Nouver",false));
       CheckOffin.setChecked(prefs.getBoolean("Offin",false));
       CheckVell.setChecked(prefs.getBoolean("Vell",false));
       CheckQuint.setChecked(prefs.getBoolean("Quint",false));


     }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rbEU:
                if (checked) {
                    prefs.edit().putInt(PREF_SERVER_CONSTANT, EUSERVER_CONSTANT).apply();
                    prefs.edit().putBoolean("EU", checked).apply();// na apothikeuei kai to oti einai checkarismeno
                    prefs.edit().putBoolean("NA",false).apply();
                }
                else

                    prefs.edit().putBoolean("EU", checked).apply();// na to ksetickarei

                break;
            case R.id.rbNA:
                if (checked){

                    prefs.edit().putInt(PREF_SERVER_CONSTANT, NASERVER_CONSTANT).apply();
                    prefs.edit().putBoolean("NA", checked).apply();
                    prefs.edit().putBoolean("EU", false).apply();
                }
                else
                    prefs.edit().putBoolean("NA",checked).apply();

                break;
        }
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked2 = ((CheckBox) view).isChecked();
        NOTIFY_BOSS.clear();

        switch (view.getId()) {
            case R.id.checkKaranda:
                if (checked2) {
                    //NOTIFY_BOSS.add("Karanda");//prefs.edit().putStringSet("Karanda",BossNotify).apply();
                    prefs.edit().putBoolean("Karanda", checked2).apply();


                } else
                    //NOTIFY_BOSS.add("");
                    prefs.edit().putBoolean("Karanda", checked2).apply();
                break;
            case R.id.checkKutum:
                if (checked2) {

                    //NOTIFY_BOSS.add("Kutum");// prefs.edit().putStringSet("Kutum",BossNotify).apply();
                    prefs.edit().putBoolean("Kutum", checked2).apply();
                } else {

                    //NOTIFY_BOSS.add("");
                    prefs.edit().putBoolean("Kutum", checked2).apply();
                }
                break;
            case R.id.checkKzarka:
                if (checked2) {

                    //NOTIFY_BOSS.add("Kzarka");
                    prefs.edit().putBoolean("Kzarka", checked2).apply();


                } else {

                    //NOTIFY_BOSS.add("");
                    prefs.edit().putBoolean("Kzarka", checked2).apply();
                }
                break;
            case R.id.checkNouver:
                if (checked2) {

                    //NOTIFY_BOSS.add("Nouver");
                    prefs.edit().putBoolean("Nouver", checked2).apply();

                } else {
                    // NOTIFY_BOSS.add("");
                    prefs.edit().putBoolean("Nouver", checked2).apply();
                }
                break;
            case R.id.checkQuint:
                if (checked2) {

                    //NOTIFY_BOSS.add("Quint");//prefs.edit().putStringSet("Quint",BossNotify).apply();
                    prefs.edit().putBoolean("Quint", checked2).apply();

                } else {

                    //NOTIFY_BOSS.add("");
                    prefs.edit().putBoolean("Quint", checked2).apply();
                }
                break;
            case R.id.checkVell:
                if (checked2) {

                    //NOTIFY_BOSS.add("Vell");// prefs.edit().putString(NOTIFY_BOSS.get(5),"Vell").apply();
                    prefs.edit().putBoolean("Vell", checked2).apply();

                } else {

                    //NOTIFY_BOSS.add("");
                    prefs.edit().putBoolean("Vell", checked2).apply();
                }
                break;
            case R.id.checkOffin:
                if (checked2) {

                    //NOTIFY_BOSS.add("Offin");// prefs.edit().putString(NOTIFY_BOSS.get(6),"Offin").apply();
                    prefs.edit().putBoolean("Offin", checked2).apply();

                } else {

                    // NOTIFY_BOSS.add("");
                    prefs.edit().putBoolean("Offin", checked2).apply();
                }
                break;
        }
        armlist.clear();
        if (rbEU.isChecked()) {
            FillNotifyList(BossEU);
             Soffset=1;
        } else{
            FillNotifyList(BossNA);
            Soffset=-7;
            }

            if(!NOTIFY_BOSS.isEmpty()) {

               for (int z = 1; z < BossSize; z++) {
                    cancelAlarm(this, AlarmReceiver.class, z+BossSize);
                }
                for (int p = 0; p < NOTIFY_BOSS.size(); p++) {

                    startAlarm(this, AlarmReceiver.class, p+1, NOTIFY_BOSS.get(p).getBossDay(), NOTIFY_BOSS.get(p).getBossHour(), NOTIFY_BOSS.get(p).getBossMin(),NOTIFY_BOSS.get(p).getBossName(),Soffset);
                    }


                }
             else {
                for (int q = 1; q <= 60; q++) {
                    cancelAlarm(this, AlarmReceiver.class, q);
                }
            }



   }




    public static void startAlarm(Context context,Class<?> cls,int request_code, int dayOfTheWeek, int hourOfTheDay, int minutes,String bossname,int Soffset) {

        Calendar calendar;
       if(Soffset<0){
             calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"+Soffset));
        }
         else{
           calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+"+Soffset));
       }

        Calendar MyCalendar=Calendar.getInstance();
        // Enable a receiver

        ComponentName receiver = new ComponentName(context, cls);

        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,

                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,

                PackageManager.DONT_KILL_APP);




        Intent intent1 = new Intent(context, cls);
            intent1.putExtra("id",request_code);
            intent1.putExtra("day",dayOfTheWeek);
            intent1.putExtra("hour",hourOfTheDay);
            intent1.putExtra("minute",minutes);
            intent1.putExtra("name", bossname);
        intent1.putExtra("offset", Soffset);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,

               request_code, intent1,

                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager manager = (AlarmManager) context.getSystemService(ALARM_SERVICE);


        int interval = 1000 * 60 * 60 * 24 * 7;
        if(dayOfTheWeek==7)
            dayOfTheWeek=1;
        else
            dayOfTheWeek++;

        calendar.set(Calendar.DAY_OF_WEEK, dayOfTheWeek  );

        calendar.set(Calendar.HOUR_OF_DAY, hourOfTheDay);
        calendar.set(Calendar.MINUTE, minutes );
        long calendarTimeInMillis=calendar.getTimeInMillis();
         //int pwd=calendar.get(Calendar.DAY_OF_MONTH)+7;
           if(calendar.before(MyCalendar)) {
              calendarTimeInMillis+=608400000 ; //calendar.setTimeInMillis(calendar.getTimeInMillis() + interval);
           }

            armlist.add(calendarTimeInMillis);
           //calendar.setTimeInMillis(System.currentTimeMillis());
            Log.d("intent",""+intent1.getIntExtra("id",0));
        manager.setRepeating(AlarmManager.RTC_WAKEUP,calendarTimeInMillis, interval, pendingIntent);
        //manager.setAndAllowWhileIdle();

    }

    public void cancelAlarm(Context context,Class<?> cls,int request_code) {

        ComponentName receiver = new ComponentName(context, cls);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);

        Intent intent1 = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, request_code, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.cancel(pendingIntent);
        pendingIntent.cancel();
    }
    static public void notificationSetup(Context context, String title, String Context,int i)
    {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "Boss")
                .setSmallIcon(R.drawable.karanda)
                .setContentTitle(title)
                .setContentText(Context)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(i, mBuilder.build());
    }
    public void FillNotifyList(ArrayList<Boss> B){

        for (int q = 0; q <B.size();q++) {

            if (prefs.getBoolean("Kutum", false) && B.get(q).getBossName().equals("Kutum")) {

                NOTIFY_BOSS.add(B.get(q));
                //startAlarm(this, AlarmReceiver.class, q, B.get(q).getBossDay(), B.get(q).getBossHour(), B.get(q).getBossMin(), B.get(q).getBossName());
            } else if (prefs.getBoolean("Karanda", false) && B.get(q).getBossName().equals("Karanda"))

            {

                NOTIFY_BOSS.add(B.get(q));
                //startAlarm(this, AlarmReceiver.class, q, B.get(q).getBossDay(), B.get(q).getBossHour(), B.get(q).getBossMin(), B.get(q).getBossName());
            } else if (prefs.getBoolean("Nouver", false) && B.get(q).getBossName().equals("Nouver"))

            {

                NOTIFY_BOSS.add(B.get(q));
                //startAlarm(this, AlarmReceiver.class, q, B.get(q).getBossDay(), B.get(q).getBossHour(), B.get(q).getBossMin(), B.get(q).getBossName());
            } else if (prefs.getBoolean("Kzarka", false) && B.get(q).getBossName().equals("Kzarka"))

            {

                NOTIFY_BOSS.add(B.get(q));
                //startAlarm(this, AlarmReceiver.class, q, B.get(q).getBossDay(), B.get(q).getBossHour(), B.get(q).getBossMin(), B.get(q).getBossName());
            } else if (prefs.getBoolean("Quint", false) && B.get(q).getBossName().equals("Quint"))

            {

                NOTIFY_BOSS.add(B.get(q));
                //startAlarm(this, AlarmReceiver.class, q, NOTIFY_BOSS.get(q).getBossDay(), NOTIFY_BOSS.get(q).getBossHour(), NOTIFY_BOSS.get(q).getBossMin(), NOTIFY_BOSS.get(q).getBossName());
            } else if (prefs.getBoolean("Vell", false) && B.get(q).getBossName().equals("Vell"))

            {

                NOTIFY_BOSS.add(B.get(q));
                // startAlarm(this, AlarmReceiver.class, q, NOTIFY_BOSS.get(q).getBossDay(), NOTIFY_BOSS.get(q).getBossHour(), NOTIFY_BOSS.get(q).getBossMin(), NOTIFY_BOSS.get(q).getBossName());
            } else if (prefs.getBoolean("Offin", false) && B.get(q).getBossName().equals("Offin"))

            {

                NOTIFY_BOSS.add(B.get(q));
                //startAlarm(this, AlarmReceiver.class, q, NOTIFY_BOSS.get(q).getBossDay(), NOTIFY_BOSS.get(q).getBossHour(), NOTIFY_BOSS.get(q).getBossMin(), NOTIFY_BOSS.get(q).getBossName());
            }

        }
             BossSize=NOTIFY_BOSS.size();
    }
}



