package com.penstack.dbobosstimer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class Settings extends AppCompatActivity {

    final String PREFS_NAME = "BDO_TIMER_PREFS";
    final int EUSERVER_CONSTANT = 1;
    final int NASERVER_CONSTANT = 2;
    final int SEASERVER_CONSTANT = 3;
    final String PREF_SERVER_CONSTANT = "0";
    final ArrayList<Boss> NOTIFY_BOSS=new ArrayList<>();

    ArrayList<Boss>  BossEU;
    ArrayList<Boss>  BossNA;
    ArrayList<Boss>  BossSEA;
    SharedPreferences prefs;
    RadioButton rbEU,rbNA,rbSEA;
    CheckBox CheckKutum,CheckKzarka,CheckKaranda,CheckNouver,CheckQuint,CheckVell,CheckOffin;
    int BossSize,Soffset;
    public ImageView backButton;
    Intent intentMain ;
    Button policyButton;
    String GDPRCONSENT = "-1";
    final int NOCONSENTGIVEN = 0;
    final int CONSENTGIVEN = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
      final GdprHelper gdprHelper = new GdprHelper(Settings.this);



       prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
       String jsonEU=prefs.getString("EuList",null);
       String jsonNA=prefs.getString("NaList",null);
       String jsonSEA=prefs.getString("SeaList",null);
       Type type=new TypeToken<ArrayList<Boss>>(){}.getType();
       Gson gson=new Gson();
       BossEU=gson.fromJson(jsonEU,type);
       BossNA=gson.fromJson(jsonNA,type);
       BossSEA=gson.fromJson(jsonSEA,type);
       policyButton = (Button)findViewById(R.id.conButton);

       policyButton.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
              gdprHelper.resetConsent();
              gdprHelper.initialise();
          }
        });

        rbEU=(RadioButton)findViewById(R.id.rbEU);
        rbNA=(RadioButton) findViewById(R.id.rbNA);
        rbSEA=(RadioButton) findViewById(R.id.rbSEA);
       intentMain = new Intent(Settings.this, MainActivity.class);

        backButton = (ImageView)findViewById(R.id.settButton);
        backButton.setOnClickListener(new View.OnClickListener(){
           public void onClick(View v) {
               //startActivity(intentMain);
               int  conPass = gdprHelper.getCon();

               switch (conPass){
                   case 0:
                       prefs.edit().putInt(GDPRCONSENT, NOCONSENTGIVEN).apply();
                       Log.d("TAG1", conPass + "");
                   case 1:
                       prefs.edit().putInt(GDPRCONSENT, CONSENTGIVEN).apply();
                       Log.d("TAG1", conPass + "");
               }
               Settings.this.finish();
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
       rbNA.setChecked(prefs.getBoolean("NA",false));
       rbSEA.setChecked(prefs.getBoolean("SEA",false));
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
                    prefs.edit().putBoolean("SEA", false).apply();
                    FillNotifyList(BossEU,1);
                }
                else
                    prefs.edit().putBoolean("EU", checked).apply();// na to ksetickarei
                break;
            case R.id.rbNA:
                if (checked){

                    prefs.edit().putInt(PREF_SERVER_CONSTANT, NASERVER_CONSTANT).apply();
                    prefs.edit().putBoolean("NA", checked).apply();
                    prefs.edit().putBoolean("EU", false).apply();
                    prefs.edit().putBoolean("SEA", false).apply();
                    FillNotifyList(BossNA,-8);
                }
            case R.id.rbSEA:
                if (checked) {

                    prefs.edit().putInt(PREF_SERVER_CONSTANT, SEASERVER_CONSTANT).apply();
                    prefs.edit().putBoolean("SEA", checked).apply();
                    prefs.edit().putBoolean("EU", false).apply();
                    prefs.edit().putBoolean("NA", false).apply();
                    FillNotifyList(BossNA, 8);
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

        switch (view.getId())
        {
            case R.id.checkKaranda:
                if (checked2)
                {
                    prefs.edit().putBoolean("Karanda", checked2).apply();
                }
                else
                {
                    prefs.edit().putBoolean("Karanda", checked2).apply();
                }
                    break;
            case R.id.checkKutum:
                if (checked2)
                {
                    prefs.edit().putBoolean("Kutum", checked2).apply();
                }
                else
                {
                    prefs.edit().putBoolean("Kutum", checked2).apply();
                }
                break;
            case R.id.checkKzarka:
                if (checked2)
                {
                    prefs.edit().putBoolean("Kzarka", checked2).apply();
                }
                else
                {
                    prefs.edit().putBoolean("Kzarka", checked2).apply();
                }
                break;
            case R.id.checkNouver:
                if (checked2)
                {
                    prefs.edit().putBoolean("Nouver", checked2).apply();
                }
                else
                {
                    prefs.edit().putBoolean("Nouver", checked2).apply();
                }
                break;
            case R.id.checkQuint:
                if (checked2)
                {
                    prefs.edit().putBoolean("Quint", checked2).apply();
                }
                else
                {
                    prefs.edit().putBoolean("Quint", checked2).apply();
                }
                break;
            case R.id.checkVell:
                if (checked2)
                {
                    prefs.edit().putBoolean("Vell", checked2).apply();
                }
                else
                {
                    prefs.edit().putBoolean("Vell", checked2).apply();
                }
                break;
            case R.id.checkOffin:
                if (checked2)
                {
                    prefs.edit().putBoolean("Offin", checked2).apply();
                }
                else
                {
                    prefs.edit().putBoolean("Offin", checked2).apply();
                }
                break;
        }
        if (rbEU.isChecked())
        {
            Soffset=1;
            FillNotifyList(BossEU,Soffset);
        }
        else if(rbSEA.isChecked())
        {
            Soffset=7;
            FillNotifyList(BossEU,Soffset);
        }
        else
        {
            Soffset=-8;
            FillNotifyList(BossNA,Soffset);
        }
   }




    public static void startAlarm(Context context,Class<?> cls,int request_code, int dayOfTheWeek, int hourOfTheDay, int minutes,String bossname,int Soffset,int BossImage,long time) {

        Calendar calendar;
       if(Soffset<0)
       {
             calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"+Soffset));
       }
       else
       {
           calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+"+Soffset));
       }

        Calendar MyCalendar=Calendar.getInstance();
        // Enable a receiver

        ComponentName receiver = new ComponentName(context, cls);

        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

        int interval = 1000 * 60 * 60 * 24 * 7;
        if(dayOfTheWeek==7) {
            dayOfTheWeek = 1;
        }
        else
        {
            dayOfTheWeek++;
        }

        calendar.set(Calendar.DAY_OF_WEEK, dayOfTheWeek  );

        calendar.set(Calendar.HOUR_OF_DAY, hourOfTheDay);
        calendar.set(Calendar.MINUTE, minutes );
        long calendarTimeInMillis=calendar.getTimeInMillis();
        if(calendar.before(MyCalendar))
        {
              calendarTimeInMillis+=608400000 ;
        }

        calendarTimeInMillis-=600000;
        if(time!=0)
        {
            calendarTimeInMillis=time;
        }
        Log.d("timeinmillis",""+calendarTimeInMillis);
        Intent intent1 = new Intent(context, cls);
        intent1.putExtra("id",request_code);
        intent1.putExtra("day",dayOfTheWeek);
        intent1.putExtra("hour",hourOfTheDay);
        intent1.putExtra("minute",minutes);
        intent1.putExtra("name", bossname);
        intent1.putExtra("image",BossImage);
        intent1.putExtra("offset", Soffset);
        intent1.putExtra("FirsTime",calendarTimeInMillis);
        intent1.putExtra("time",time);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, request_code, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager manager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendarTimeInMillis,pendingIntent);
        }
        else
        {
            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendarTimeInMillis, interval, pendingIntent);
        }
    }

    static public void cancelAlarm(Context context,Class<?> cls,int request_code)
    {

        ComponentName receiver = new ComponentName(context, cls);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

        Intent intent1 = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, request_code, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.cancel(pendingIntent);
        pendingIntent.cancel();
    }
    static public void notificationSetup(Context context, String title, String SContext,int i,int BossIm)
    {
        Intent main=new Intent(context,SplashActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(main);

        PendingIntent notify=stackBuilder.getPendingIntent(100, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "Boss")
                .setSmallIcon(R.drawable.smallicon)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.bdotimericon))
                .setContentTitle(title)
                .setContentText(SContext)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(notify);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(i, mBuilder.build());
    }
    public void FillNotifyList(ArrayList<Boss> B,int soffset){

        for (int q = 0; q <B.size();q++)
        {

            if (prefs.getBoolean("Kutum", false) && B.get(q).getBossName().equals("Kutum"))
            {

                NOTIFY_BOSS.add(B.get(q));
                //startAlarm(this, AlarmReceiver.class, q, B.get(q).getBossDay(), B.get(q).getBossHour(), B.get(q).getBossMin(), B.get(q).getBossName());
            }
            else if (prefs.getBoolean("Karanda", false) && B.get(q).getBossName().equals("Karanda"))
            {
                NOTIFY_BOSS.add(B.get(q));
            }
            else if (prefs.getBoolean("Nouver", false) && B.get(q).getBossName().equals("Nouver"))
            {
                NOTIFY_BOSS.add(B.get(q));
            }
            else if (prefs.getBoolean("Kzarka", false) && B.get(q).getBossName().equals("Kzarka"))
            {
                NOTIFY_BOSS.add(B.get(q));
            }
            else if (prefs.getBoolean("Quint", false) && B.get(q).getBossName().equals("Quint"))
            {
                NOTIFY_BOSS.add(B.get(q));
            }
            else if (prefs.getBoolean("Vell", false) && B.get(q).getBossName().equals("Vell"))
            {
                NOTIFY_BOSS.add(B.get(q));
            }
            else if (prefs.getBoolean("Offin", false) && B.get(q).getBossName().equals("Offin"))
            {
                NOTIFY_BOSS.add(B.get(q));
            }

        }
             BossSize=NOTIFY_BOSS.size();
            for (int q = 1; q <= 60; q++)
            {
                cancelAlarm(this, AlarmReceiver.class, q);
            }

            if(!NOTIFY_BOSS.isEmpty())
            {
                for (int p = 0; p < NOTIFY_BOSS.size(); p++)
                {

                startAlarm(this, AlarmReceiver.class, p+1, NOTIFY_BOSS.get(p).getBossDay(), NOTIFY_BOSS.get(p).getBossHour(), NOTIFY_BOSS.get(p).getBossMin(),NOTIFY_BOSS.get(p).getBossName(),soffset,NOTIFY_BOSS.get(p).getBossImage(),0);
                }

            }


    }
}



