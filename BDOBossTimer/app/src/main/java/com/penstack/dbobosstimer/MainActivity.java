package com.penstack.dbobosstimer;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.sql.Timestamp;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import static android.support.v4.os.LocaleListCompat.create;
import static java.util.TimeZone.getTimeZone;


public class MainActivity extends AppCompatActivity {

public long countdown,day;
    public Date k;
     public Timestamp BossTimestamp;
    public int i;
    public int hour;
    public int MDay;
    public int offset;
    public TextView text1;
    public final ArrayList<Boss> BossDayList = new ArrayList<>();
    public final ArrayList<Boss> BossDayEUList = new ArrayList<>();
    public final ArrayList<Boss> BossDayNAList = new ArrayList<>();
    private RecyclerView listView;
    private BossAdapter BossAdapter;
    private Calendar bCalendar;
    public int Wday;
    public int minute;
    public String s,server;
    public int nextDay;
    public long n,diff;
    public ImageView settButton;
    Intent intentSettings ;
    Intent intentFirstTime;
    final String PREFS_NAME = "BDO_TIMER_PREFS";
    final String PREF_SERVER_CONSTANT = "0";
    final int DOESNT_EXIST = -1;
    final ArrayList<String> NOTIFY_BOSS=new ArrayList<>();
     Set<String> BossNotify=new HashSet<String>();
    final String PREF_NOTIFY="NotificationList";
    SharedPreferences prefs ;
    public String CHANNEL_ID;
    Intent notifications;
    public String RealDAY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        intentSettings = new Intent(MainActivity.this, Settings.class);
        intentFirstTime = new Intent(MainActivity.this, firstTimeUseActivity.class);

        settButton = (ImageView)findViewById(R.id.settButton);
        settButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                startActivity(intentSettings);
            }
        });

        checkFirstRun();

        //----------------------------------EU BOSSES-----------------------------------------
        //Monday
        BossDayEUList.add(new Kutum(1,0,15,"EU",0)) ;
        BossDayEUList.add(new Karanda(1,2,0,"EU",0)) ;
        BossDayEUList.add(new Kzarka(1,5,0,"EU",0));
        BossDayEUList.add(new Kzarka(1,9, 0, "EU",0));
        BossDayEUList.add(new Nouver(1,12,0,"EU",0)) ;
        BossDayEUList.add(new Kutum( 1,16,0,"EU",0)) ;
        BossDayEUList.add(new Nouver(1,19,0,"EU",0)) ;
        BossDayEUList.add(new Kzarka(1,22,15,"EU",0)) ;
        //Tuesday
        BossDayEUList.add(new Karanda(2,0,15,"EU",0)) ;
        BossDayEUList.add(new Kutum(2,2,0,"EU",0)) ;
        BossDayEUList.add(new Kzarka(2,5,0,"EU",0)) ;
        BossDayEUList.add(new Kutum(2,9,0,"EU",0)) ;
        BossDayEUList.add(new  Offin(2,12,0,"EU",0)) ;
        BossDayEUList.add(new Nouver(2,16,0,"EU",0)) ;
        BossDayEUList.add(new Karanda(2,19,0,"EU",0)) ;
        BossDayEUList.add(new Kzarka(2,22,15,"EU",0)) ;
        BossDayEUList.add(new Nouver(2,22,15,"EU",0)) ;
        //Wednesday
        BossDayEUList.add(new Kutum(3,0,15,"EU",0)) ;
        BossDayEUList.add(new Karanda(3,2,0,"EU",0)) ;
        BossDayEUList.add(new Kzarka(3,5,0,"EU",0)) ;
        BossDayEUList.add(new Karanda(3,9,0,"EU",0)) ;
        BossDayEUList.add(new Kzarka(3,16,0,"EU",0)) ;
        BossDayEUList.add(new Kutum(3,19,0,"EU",0)) ;
        BossDayEUList.add(new Karanda(3,22,15,"EU",0)) ;
        BossDayEUList.add(new Kzarka(3,22,15,"EU",0)) ;
        //Thursday
        BossDayEUList.add(new Nouver(4,0,15,"EU",0)) ;
        BossDayEUList.add(new Kutum(4,2,0,"EU",0)) ;
        BossDayEUList.add(new Nouver(4,5,0,"EU",0)) ;
        BossDayEUList.add(new Kutum(4,9,0,"EU",0)) ;
        BossDayEUList.add(new Nouver(4,12,0,"EU",0)) ;
        BossDayEUList.add(new Kutum(4,16,0,"EU",0)) ;
        BossDayEUList.add(new Offin(4,19,0,"EU",0)) ;
        BossDayEUList.add(new Karanda(4,22,15,"EU",0)) ;
        //Friday
        BossDayEUList.add(new Kzarka(5,0,15,"EU",0));
        BossDayEUList.add(new Nouver(5,2,0,"EU",0));
        BossDayEUList.add(new Karanda(5,5,0,"EU",0));
        BossDayEUList.add(new Kutum(5,9,0,"EU",0));
        BossDayEUList.add(new Karanda(5,12,0,"EU",0));
        BossDayEUList.add(new Nouver(5,16,0,"EU",0));
        BossDayEUList.add(new Kzarka(5,19,0,"EU",0));
        BossDayEUList.add(new Kzarka(5,22,15,"EU",0));
        BossDayEUList.add(new Kutum(5,22,15,"EU",0));
        //Saturday
        BossDayEUList.add(new Karanda(6,0,15,"EU",0));
        BossDayEUList.add(new Offin(6,2,0,"EU",0));
        BossDayEUList.add(new Nouver(6,5,0,"EU",0));
        BossDayEUList.add(new Kutum(6,9,0,"EU",0));
        BossDayEUList.add(new Nouver(6,12,0,"EU",0));
        BossDayEUList.add(new Quint(6,16,0,"EU",0));
        BossDayEUList.add(new Kzarka(6,19,0,"EU",0));
        BossDayEUList.add(new Karanda(6,19,0,"EU",0));
        //Sunday
        BossDayEUList.add(new Nouver(7,0,15,"EU",0));
        BossDayEUList.add(new Kutum(7,0,15,"EU",0));
        BossDayEUList.add(new Kzarka(7,2,0,"EU",0));
        BossDayEUList.add(new Kutum(7,5,0,"EU",0));
        BossDayEUList.add(new Nouver(7,9,0,"EU",0));
        BossDayEUList.add(new Kzarka(7,12,0,"EU",0));
        BossDayEUList.add(new Vell(7,16,0,"EU",0));
        BossDayEUList.add(new Karanda(7,19,0,"EU",0));
        BossDayEUList.add(new Kzarka(7,22,15,"EU",0));
        BossDayEUList.add(new Nouver(7,22,15,"EU",0));

        //----------------------------------NA BOSSES-----------------------------------------
        //Monday
        BossDayNAList.add(new Karanda(1,0,0,"NA",0)) ;
        BossDayNAList.add(new Kzarka(1,3,0,"NA",0)) ;
        BossDayNAList.add(new Kzarka(1,7,0,"NA",0)) ;
        BossDayNAList.add(new Nouver(1,10,0,"NA",0)) ;
        BossDayNAList.add(new Kutum(1,14,0,"NA",0)) ;
        BossDayNAList.add(new Nouver(1,17,0,"NA",0)) ;
        BossDayNAList.add(new Kzarka(1,20,15,"NA",0)) ;
        BossDayNAList.add(new Karanda(1,22,15,"NA",0)) ;
        //Tuesday
        BossDayNAList.add(new Kutum(2,0,0,"NA",0)) ;
        BossDayNAList.add(new Kzarka(2,3,0,"NA",0)) ;
        BossDayNAList.add(new Kutum(2,7,0,"NA",0)) ;
        BossDayNAList.add(new Offin(2,10,0,"NA",0)) ;
        BossDayNAList.add(new Nouver(2,14,0,"NA",0)) ;
        BossDayNAList.add(new Karanda(2,17,0,"NA",0)) ;
        BossDayNAList.add(new Kzarka(2,20,15,"NA",0)) ;
        BossDayNAList.add(new Nouver(2,20,15,"NA",0)) ;
        BossDayNAList.add(new Kutum(2,22,15,"NA",0)) ;
        //Wednesday
        BossDayNAList.add(new Karanda(3,0,0,"NA",0)) ;
        BossDayNAList.add(new Karanda(3,7,0,"NA",0)) ;
        BossDayNAList.add(new Nouver(3,10,0,"NA",0)) ;
        BossDayNAList.add(new Kzarka(3,14,0,"NA",0)) ;
        BossDayNAList.add(new Kutum(3,17,0,"NA",0)) ;
        BossDayNAList.add(new Kzarka(3,20,15,"NA",0)) ;
        BossDayNAList.add(new Karanda(3,20,15,"NA",0)) ;
        BossDayNAList.add(new Nouver(3,22,15,"NA",0)) ;
        //Thursday
        BossDayNAList.add(new Kutum(4,0,0,"NA",0)) ;
        BossDayNAList.add(new Kzarka(4,3,0,"NA",0)) ;
        BossDayNAList.add(new Kutum(4,7,0,"NA",0)) ;
        BossDayNAList.add(new Nouver(4,10,0,"NA",0)) ;
        BossDayNAList.add(new Kutum(4,14,0,"NA",0)) ;
        BossDayNAList.add(new Offin(4,17,0,"NA",0)) ;
        BossDayNAList.add(new Karanda(4,20,15,"NA",0)) ;
        BossDayNAList.add(new Kzarka(4,22,15,"NA",0)) ;
        //Friday
        BossDayNAList.add(new Nouver(5,0,0,"NA",0)) ;
        BossDayNAList.add(new Karanda(5,3,0,"NA",0)) ;
        BossDayNAList.add(new Kutum(5,7,0,"NA",0)) ;
        BossDayNAList.add(new Karanda(5,10,0,"NA",0)) ;
        BossDayNAList.add(new Nouver(5,14,0,"NA",0)) ;
        BossDayNAList.add(new Kzarka(5,17,0,"NA",0)) ;
        BossDayNAList.add(new Kutum(5,20,15,"NA",0)) ;
        BossDayNAList.add(new Kzarka(5,20,15,"NA",0)) ;
        BossDayNAList.add(new Karanda(5,22,15,"NA",0)) ;
        //Saturday
        BossDayNAList.add(new Offin(6,0,0,"NA",0)) ;
        BossDayNAList.add(new Nouver(6,3,0,"NA",0)) ;
        BossDayNAList.add(new Kutum(6,7,0,"NA",0)) ;
        BossDayNAList.add(new Nouver(6,10,0,"NA",0)) ;
        BossDayNAList.add(new Quint(6,14,0,"NA",0)) ;
        BossDayNAList.add(new Kzarka(6,17,0,"NA",0)) ;
        BossDayNAList.add(new Karanda(6,17,0,"NA",0)) ;
        BossDayNAList.add(new Nouver(6,22,15,"NA",0)) ;
        BossDayNAList.add(new Kutum(6,22,15,"NA",0)) ;
        //Sunday
        BossDayNAList.add(new Kzarka(7,0,0,"NA",0)) ;
        BossDayNAList.add(new Kutum(7,3,0,"NA",0)) ;
        BossDayNAList.add(new Nouver(7,7,0,"NA",0)) ;
        BossDayNAList.add(new Kzarka(7,10,0,"NA",0)) ;
        BossDayNAList.add(new Vell(7,14,0,"NA",0)) ;
        BossDayNAList.add(new Karanda(7,17,0,"NA",0)) ;
        BossDayNAList.add(new Kzarka(7,20,15,"NA",0)) ;
        BossDayNAList.add(new Nouver(7,20,15,"NA",0)) ;
        BossDayNAList.add(new Kutum(7,22,15,"NA",0)) ;

        intentSettings.putExtra("BossDayEUList",BossDayEUList);
        intentSettings.putExtra("BossDayNAList",BossDayNAList);

        TimeZone tz=TimeZone.getDefault();
        TimeZone tz2=TimeZone.getTimeZone("GMT+2");

        Preferences();
        createNotificationChannel();



    }

        public void ServerSelection(ArrayList<Boss> Slist,String Soffset){for( i=0;i<Slist.size();i++){



             String v=Hours(Integer.toString(hour),offset,Soffset);//briskei thn wra tou xrhsth gia na th sugkrinei me twn bosses
             int RealHour=Integer.parseInt(v);
             String d=+bCalendar.get(Calendar.YEAR)+"-"+(bCalendar.get(Calendar.MONTH)+1)+"-"+MDay+" "+hour+":"+minute;
             int Realwday=UserDay(d,offset,Soffset);// h evdomadiaia mera me thn opoia tha sugkrinoume th boss lista ,an px o xrhsths einai mia mera mprosta h to antitheto

           // ta ekana int ola sto Boss object  gia na kanw pio grhgora prakseis,vriskei ta boss auths ths hmeras kai ths epomenhs p prolavainei o xrhsths
            if(Realwday==(Slist.get(i).getBossDay()) && (RealHour<Slist.get(i).getBossHour() || (RealHour==Slist.get(i).getBossHour()&& minute<Slist.get(i).getBossMin()))){

                s=+bCalendar.get(Calendar.YEAR)+"-"+(bCalendar.get(Calendar.MONTH)+1)+"-"+RealDAY+" "+Slist.get(i).getBossHour()+":"+Slist.get(i).getBossMin()+":00";
                countdown = Time(s,offset,Soffset);

                day = bCalendar.getTimeInMillis();
                diff=(countdown-day);
                Slist.get(i).setTimeLeft(diff);
                Boss nextBoss=Slist.get(i);
                BossDayList.add(nextBoss);
            }
            else if((Realwday==Slist.get(i).getBossDay()-1) || (Realwday==7 && Slist.get(i).getBossDay()==1)){

                    int rd=Integer.parseInt(RealDAY);// to idio me thn realwDay

                s=bCalendar.get(Calendar.YEAR)+"-"+(bCalendar.get(Calendar.MONTH)+1)+"-"+(rd+1)+" "+Slist.get(i).getBossHour()+":"+Slist.get(i).getBossMin()+":00";
                countdown=Time(s,offset,Soffset);
                day = bCalendar.getTimeInMillis();
                diff=(countdown-day);
                Slist.get(i).setTimeLeft(diff);
                Boss nextBoss=Slist.get(i);
                BossDayList.add(nextBoss);

            }

        }
            for(int j=0;j<BossDayList.size();j++){//sortarei me auksousa seira
                for(int l=j+1;l<BossDayList.size();l++) {
                    if (BossDayList.get(l).getTimeLeft() <= BossDayList.get(j).getTimeLeft()) {

                         Boss newBoss=BossDayList.get(j);
                         BossDayList.set(j,BossDayList.get(l));
                         BossDayList.set(l,newBoss);

                    }

                }
            }
            //ftiaxnw ton bossadapter kai pernaw to recycle view sth lista,ton layoutmanager ton evala giati alliws den emfanizontan
        BossAdapter = new BossAdapter(this,BossDayList);
        listView = (RecyclerView)findViewById(R.id.listView0);
        listView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(BossAdapter);

    }
    public String  Hours(String v,int Uoffset,String serverOff){ //vriskei thn  wra tou xrhsth se sxesh me tou server gia na th sugkrinei me auth twn bosses

        SimpleDateFormat hour = new SimpleDateFormat("H");


        if(Uoffset>0) {

            hour.setTimeZone(getTimeZone("GMT+" + Uoffset));//"GMT+"+offset.getOffset(new Date().getTime())));
        }
        else
            hour.setTimeZone(getTimeZone("GMT"+Uoffset));
        ParsePosition pos = new ParsePosition(0);
        k = hour.parse(v, pos);
        hour.setTimeZone(getTimeZone("GMT"+serverOff));

        String time=hour.format(k);

        return time;
    }
    public int UserDay(String d,int Uoffset,String serverOff){

        SimpleDateFormat Day2 = new SimpleDateFormat("yyyy-M-d H:mm");
        if(Uoffset>0) {

            Day2.setTimeZone(getTimeZone("GMT+" + Uoffset));//"GMT+"+offset.getOffset(new Date().getTime())));
        }
        else
            Day2.setTimeZone(getTimeZone("GMT"+Uoffset));
        ParsePosition pos = new ParsePosition(0);

        Date newk=Day2.parse(d,pos);

        Day2.setTimeZone(getTimeZone("GMT"+serverOff));


        Day2.applyPattern("E");
        String Uday=Day2.format(newk);
        int IntDay=1;
        switch (Uday) {
            case "Mon":
                IntDay=1;
                break;
            case "Tue":
                IntDay=2;
                break;
            case "Wed":
                IntDay=3;
                break;
            case "Thu":
                IntDay=4;
                break;
            case "Fri":
                IntDay=5;
                break;
            case "Sat":
                IntDay=6;
                break;
            case "Sun":
                IntDay=7;
                break;


        }
        Day2.applyPattern("d");
         RealDAY=Day2.format(newk);
        return  IntDay;

    }
    public  long Time(String s,int Uoffset,String serverOff){

        String tim="GMT"+serverOff;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d H:mm:ss");//tou lew pws na ta emfanizei
            sdf.setTimeZone(getTimeZone("GMT"+serverOff));//tou lew to timezone tou string pou tou dinw
            //sdf.setTimeZone(TimeZone.getTimeZone("GMT+" + bCalendar.get(Calendar.DST_OFFSET) + ""));
            ParsePosition pos = new ParsePosition(0);//tou lew apo pou na arxisei na diavazei,dld apo thn arxh
            k = sdf.parse(s, pos);//parsarei to string sto Date k


        if(Uoffset>0) {

            sdf.setTimeZone(getTimeZone("GMT+"+Uoffset)); //allazoume apo gmt+UTC+2(edw ==CEST,otan tha ginei CET tha allaksoume apla thn wra stis listes tou EU) se auto tou xrhsth
        }
        else
            sdf.setTimeZone(getTimeZone("GMT"+Uoffset));


            String time = sdf.format(k);//to emfanizw me to format p to dwsa sto sdf
        long n = k.getTime();
        return n;
        }

    private void checkFirstRun() {

        final String PREFS_NAME = "BDO_TIMER_PREFS";
        final String PREF_VERSION_CODE_KEY = "1";
        final int DOESNT_EXIST = -1;

        // Get current version code
        int currentVersionCode = BuildConfig.VERSION_CODE;

        // Get saved version code
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {

            // This is just a normal run
            return;

        } else if (savedVersionCode == DOESNT_EXIST) {

            //first time
            startActivity(intentFirstTime);
            prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
            return;

        } else if (currentVersionCode > savedVersionCode) {

            // update
            return;
        }

        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
    }

    @Override
    public void onResume()
    {

        super.onResume();
        Preferences();
        /*BossDayList.clear();
        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int currentServerSelection = prefs.getInt(PREF_SERVER_CONSTANT, DOESNT_EXIST);
        if(currentServerSelection == 1) {
            ServerSelection(BossDayEUList, "+2");
        }else if(currentServerSelection == 2){
            ServerSelection(BossDayNAList, "-7");
        }



           BossNotify=prefs.getStringSet(PREF_NOTIFY,null);*/

    }


    public void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "bossTimer";
            String description = "Notification for Boss spawning";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            String CHANNEL_ID = "Boss";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void notificationSetup(int Image, String title, String Context)
    {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(Image)
                .setContentTitle(title)
                .setContentText(Context)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setChannelId("Boss");

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, mBuilder.build());
    }

    private void Preferences(){


        bCalendar = Calendar.getInstance();
        hour = bCalendar.get(Calendar.HOUR_OF_DAY);
        minute=bCalendar.get(Calendar.MINUTE);
        MDay = bCalendar.get(Calendar.DAY_OF_MONTH);
        Wday = bCalendar.get(Calendar.DAY_OF_WEEK);
        offset =(bCalendar.get(Calendar.DST_OFFSET)+bCalendar.get(Calendar.ZONE_OFFSET))/3600000;
        int month=bCalendar.get(Calendar.MONTH);
        BossDayList.clear();
        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int currentServerSelection = prefs.getInt(PREF_SERVER_CONSTANT, DOESNT_EXIST);
        if(currentServerSelection == 1) {
            ServerSelection(BossDayEUList, "+1");
        }else if(currentServerSelection == 2){
            ServerSelection(BossDayNAList, "-7");
        }


       // createNotificationChannel();
        BossNotify=prefs.getStringSet(PREF_NOTIFY,null);
       /* if (prefs.getBoolean("Karanda",false)){
           // notifications = new Intent(this, AlertDialog.class);
            //notifications.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notifications, 0);

            CHANNEL_ID="Bosschannel";
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle("BOSS SPAWNING")
                    .setContentText("Boss will spawn in 15mins!!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);


            // Gets an instance of the NotificationManager service//

            NotificationManager mNotificationManager =

                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            // When you issue multiple notifications about the same type of event,
            // it’s best practice for your app to try to update an existing notification
            // with this new information, rather than immediately creating a new notification.
            // If you want to update this notification at a later date, you need to assign it an ID.
            // You can then use this ID whenever you issue a subsequent notification.
            // If the previous notification is still visible, the system will update this existing notification,
            // rather than create a new one. In this example, the notification’s ID is 001//



                    mNotificationManager.notify(001, mBuilder.build());




        }*/

    }
    //Alarm Tests
    /*public void startAlarm(View view) {
        manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        int interval = 10000;

        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
       // Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarm(View view) {
        if (manager != null) {
            manager.cancel(pendingIntent);
            //Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
        }
    } */
}