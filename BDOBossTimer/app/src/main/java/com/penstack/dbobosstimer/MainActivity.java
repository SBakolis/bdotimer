package com.penstack.dbobosstimer;


import android.app.NotificationChannel;
import android.app.NotificationManager;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


import android.widget.TextView;

import java.lang.reflect.Type;
import java.sql.Timestamp;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;


import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import static java.util.TimeZone.getTimeZone;


public class MainActivity extends AppCompatActivity {

    public long countdown, day;
    public Date k;
    public Timestamp BossTimestamp;
    public int i;
    public int hour;
    public int MDay;
    public int offset;
    public TextView text1;
    public final ArrayList<Boss> BossDayList = new ArrayList<>();
    public  ArrayList<Boss> BossDayEUList = new ArrayList<>();
    public  ArrayList<Boss> BossDayNAList = new ArrayList<>();
    public  ArrayList<Boss> BossDaySEAList = new ArrayList<>();
    private RecyclerView listView;
    private BossAdapter BossAdapter;
    private Calendar bCalendar;
    public int Wday;
    public int minute;
    public String s, server;
    public int nextDay;
    public long n, diff;
    public ImageView settButton;
    Intent intentSettings;
    Intent intentFirstTime;
    final String PREFS_NAME = "BDO_TIMER_PREFS";
    final String PREF_SERVER_CONSTANT = "0";
    final int DOESNT_EXIST = -1;
    final String PREF_NOTIFY = "NotificationList";
    SharedPreferences prefs;
    public String CHANNEL_ID;
    Intent notifications;
    public String RealDAY;

    final String GDPRCONSENT = "-1";
    int getUserGDPRConsent;
    private InterstitialAd mInterstitialAd;
    boolean adAlreadyAppeared = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentSettings = new Intent(MainActivity.this, Settings.class);
        intentFirstTime = new Intent(MainActivity.this, firstTimeUseActivity.class);
        settButton = (ImageView) findViewById(R.id.settButton);
        settButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intentSettings);
            }
        });
        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        checkFirstRun();
        String jsonEU=prefs.getString("EuList",null);
        String jsonNA=prefs.getString("NaList",null);
        String jsonSEA=prefs.getString("SeaList",null);
        Type type=new TypeToken<ArrayList<Boss>>(){}.getType();
        Gson gson=new Gson();
        BossDayEUList=gson.fromJson(jsonEU,type);
        BossDayNAList=gson.fromJson(jsonNA,type);
        BossDaySEAList=gson.fromJson(jsonSEA,type);
        Preferences();
        createNotificationChannel();
    }

    public void ServerSelection(ArrayList<Boss> Slist, String Soffset)
    {
        for (i = 0; i < Slist.size() ; i++)
        {
            String v = Hours(Integer.toString(hour), offset, Soffset);//briskei thn wra tou xrhsth gia na th sugkrinei me twn bosses
            int RealHour = Integer.parseInt(v);
            String d = +bCalendar.get(Calendar.YEAR) + "-" + (bCalendar.get(Calendar.MONTH) + 1) + "-" + MDay + " " + hour + ":" + minute;
            int Realwday = UserDay(d, offset, Soffset);// h evdomadiaia mera me thn opoia tha sugkrinoume th boss lista ,an px o xrhsths einai mia mera mprosta h to antitheto

            // ta ekana int ola sto Boss object  gia na kanw pio grhgora prakseis,vriskei ta boss auths ths hmeras kai ths epomenhs p prolavainei o xrhsths
            if (Realwday == (Slist.get(i).getBossDay()) && (RealHour < Slist.get(i).getBossHour() || (RealHour == Slist.get(i).getBossHour() && minute < Slist.get(i).getBossMin())))
            {

                s = +bCalendar.get(Calendar.YEAR) + "-" + (bCalendar.get(Calendar.MONTH) + 1) + "-" + RealDAY + " " + Slist.get(i).getBossHour() + ":" + Slist.get(i).getBossMin() + ":00";
                countdown = Time(s, offset, Soffset);
                Slist.get(i).setTimeLeft(countdown);
                Boss nextBoss = Slist.get(i);
                BossDayList.add(nextBoss);
            }
            else if ((Realwday == Slist.get(i).getBossDay() - 1) || (Realwday == 7 && Slist.get(i).getBossDay() == 1))
            {
                int rd = Integer.parseInt(RealDAY);// to idio me thn realwDay
                s = bCalendar.get(Calendar.YEAR) + "-" + (bCalendar.get(Calendar.MONTH) + 1) + "-" + (rd + 1) + " " + Slist.get(i).getBossHour() + ":" + Slist.get(i).getBossMin() + ":00";
                countdown = Time(s, offset, Soffset);
                Slist.get(i).setTimeLeft(countdown);
                Boss nextBoss = Slist.get(i);
                BossDayList.add(nextBoss);
            }

        }
        for (int j = 0; j < BossDayList.size(); j++)
        {//sortarei me auksousa seira
            for (int l = j + 1; l < BossDayList.size(); l++)
            {
                if (BossDayList.get(l).getTimeLeft() <= BossDayList.get(j).getTimeLeft())
                {
                    Boss newBoss = BossDayList.get(j);
                    BossDayList.set(j, BossDayList.get(l));
                    BossDayList.set(l, newBoss);
                }
            }
        }
        //ftiaxnw ton bossadapter kai pernaw to recycle view sth lista,ton layoutmanager ton evala giati alliws den emfanizontan
        BossAdapter = new BossAdapter(this, BossDayList);
        listView = (RecyclerView) findViewById(R.id.listView0);
        listView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(BossAdapter);

    }

    public String Hours(String v, int Uoffset, String serverOff)
    { //vriskei thn  wra tou xrhsth se sxesh me tou server gia na th sugkrinei me auth twn bosses

        SimpleDateFormat hour = new SimpleDateFormat("H");
        if (Uoffset > 0)
        {
            hour.setTimeZone(getTimeZone("GMT+" + Uoffset));//"GMT+"+offset.getOffset(new Date().getTime())));
        }
        else {
            hour.setTimeZone(getTimeZone("GMT" + Uoffset));
        }

        ParsePosition pos = new ParsePosition(0);
        k = hour.parse(v, pos);
        hour.setTimeZone(getTimeZone("GMT" + serverOff));
        String time = hour.format(k);

        return time;
    }

    public int UserDay(String d, int Uoffset, String serverOff)
    {
        SimpleDateFormat Day2 = new SimpleDateFormat("yyyy-M-d H:mm", Locale.ENGLISH);
        if (Uoffset > 0)
        {
            Day2.setTimeZone(getTimeZone("GMT+" + Uoffset));
        }
        else
        {
            Day2.setTimeZone(getTimeZone("GMT" + Uoffset));
        }

        ParsePosition pos = new ParsePosition(0);
        Date newk = Day2.parse(d, pos);
        Day2.setTimeZone(getTimeZone("GMT" + serverOff));
        Day2.applyPattern("E");
        String Uday = Day2.format(newk);
        int IntDay = 1;
        switch (Uday)
        {
            case "Mon":
                IntDay = 1;
                break;
            case "Tue":
                IntDay = 2;
                break;
            case "Wed":
                IntDay = 3;
                break;
            case "Thu":
                IntDay = 4;
                break;
            case "Fri":
                IntDay = 5;
                break;
            case "Sat":
                IntDay = 6;
                break;
            case "Sun":
                IntDay = 7;
                break;
        }
        Day2.applyPattern("d");
        RealDAY = Day2.format(newk);
        return IntDay;
    }

    public long Time(String s, int Uoffset, String serverOff)
    {
        String tim = "GMT" + serverOff;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d H:mm:ss");//tou lew pws na ta emfanizei
        sdf.setTimeZone(getTimeZone("GMT" + serverOff));//tou lew to timezone tou string pou tou dinw
        ParsePosition pos = new ParsePosition(0);//tou lew apo pou na arxisei na diavazei,dld apo thn arxh
        k = sdf.parse(s, pos);//parsarei to string sto Date k

        if (Uoffset > 0)
        {
            sdf.setTimeZone(getTimeZone("GMT+" + Uoffset)); //allazoume apo gmt+UTC+2(edw ==CEST,otan tha ginei CET tha allaksoume apla thn wra stis listes tou EU) se auto tou xrhsth
        }
        else
        {
            sdf.setTimeZone(getTimeZone("GMT" + Uoffset));
        }

        String time = sdf.format(k);//to emfanizw me to format p to dwsa sto sdf
        long n = k.getTime();
        return n;
    }

    private void checkFirstRun()
    {

        final String PREFS_NAME = "BDO_TIMER_PREFS";


        final String PREF_VERSION_CODE_KEY = "12";


        final int DOESNT_EXIST = -1;

        // Get current version code
        int currentVersionCode = BuildConfig.VERSION_CODE;

        // Get saved version code
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode)
        {
            // This is just a normal run
            setAdvListener();
            return;
        }
        else if (savedVersionCode == DOESNT_EXIST)
        {
            //first time
             ArrayList<Boss> BossDayEUList = new ArrayList<>();
             ArrayList<Boss> BossDayNAList = new ArrayList<>();
             ArrayList<Boss> BossDaySEAList= new ArrayList<>();

            //----------------------------------EU BOSSES-----------------------------------------
            //Monday
            BossDayEUList.add(new Kutum(1, 0, 15, "EU", 0));
            BossDayEUList.add(new Karanda(1, 0, 15, "EU", 0));
            BossDayEUList.add(new Karanda(1, 2, 0, "EU", 0));
            BossDayEUList.add(new Kzarka(1, 5, 0, "EU", 0));
            BossDayEUList.add(new Kzarka(1, 9, 0, "EU", 0));
            BossDayEUList.add(new Offin(1, 12, 0, "EU", 0));
            BossDayEUList.add(new Kutum(1, 16, 0, "EU", 0));
            BossDayEUList.add(new Nouver(1, 19, 0, "EU", 0));
            BossDayEUList.add(new Kzarka(1, 22, 15, "EU", 0));
            //Tuesday
            BossDayEUList.add(new Karanda(2, 0, 15, "EU", 0));
            BossDayEUList.add(new Kutum(2, 2, 0, "EU", 0));
            BossDayEUList.add(new Kzarka(2, 5, 0, "EU", 0));
            BossDayEUList.add(new Nouver(2, 9, 0, "EU", 0));
            BossDayEUList.add(new Kutum(2, 12, 0, "EU", 0));
            BossDayEUList.add(new Nouver(2, 16, 0, "EU", 0));
            BossDayEUList.add(new Karanda(2, 19, 0, "EU", 0));
            BossDayEUList.add(new Garmoth(2, 22, 15, "EU", 0));

            //Wednesday
            BossDayEUList.add(new Kutum(3, 0, 15, "EU", 0));
            BossDayEUList.add(new Kzarka(3, 0, 15, "EU", 0));
            BossDayEUList.add(new Karanda(3, 2, 0, "EU", 0));
            BossDayEUList.add(new Kzarka(3, 5, 0, "EU", 0));
            BossDayEUList.add(new Karanda(3, 9, 0, "EU", 0));
            BossDayEUList.add(new Kutum(3, 16, 0, "EU", 0));
            BossDayEUList.add(new Offin(3, 19, 0, "EU", 0));
            BossDayEUList.add(new Karanda(3, 22, 15, "EU", 0));
            BossDayEUList.add(new Kzarka(3, 22, 15, "EU", 0));
            BossDayEUList.add(new Quint(3, 23, 15, "EU", 0));
            //Thursday
            BossDayEUList.add(new Nouver(4, 0, 15, "EU", 0));
            BossDayEUList.add(new Kutum(4, 2, 0, "EU", 0));
            BossDayEUList.add(new Nouver(4, 5, 0, "EU", 0));
            BossDayEUList.add(new Kutum(4, 9, 0, "EU", 0));
            BossDayEUList.add(new Nouver(4, 12, 0, "EU", 0));
            BossDayEUList.add(new Kzarka(4, 16, 0, "EU", 0));
            BossDayEUList.add(new Kutum(4, 19, 0, "EU", 0));
            BossDayEUList.add(new Garmoth(4, 22, 15, "EU", 0));
            //Friday
            BossDayEUList.add(new Kzarka(5, 0, 15, "EU", 0));
            BossDayEUList.add(new Karanda(5, 0, 15, "EU", 0));
            BossDayEUList.add(new Nouver(5, 2, 0, "EU", 0));
            BossDayEUList.add(new Karanda(5, 5, 0, "EU", 0));
            BossDayEUList.add(new Kutum(5, 9, 0, "EU", 0));
            BossDayEUList.add(new Karanda(5, 12, 0, "EU", 0));
            BossDayEUList.add(new Nouver(5, 16, 0, "EU", 0));
            BossDayEUList.add(new Kzarka(5, 19, 0, "EU", 0));
            BossDayEUList.add(new Kzarka(5, 22, 15, "EU", 0));
            BossDayEUList.add(new Kutum(5, 22, 15, "EU", 0));
            //Saturday
            BossDayEUList.add(new Karanda(6, 0, 15, "EU", 0));
            BossDayEUList.add(new Offin(6, 2, 0, "EU", 0));
            BossDayEUList.add(new Nouver(6, 5, 0, "EU", 0));
            BossDayEUList.add(new Kutum(6, 9, 0, "EU", 0));
            BossDayEUList.add(new Nouver(6, 12, 0, "EU", 0));
            BossDayEUList.add(new Quint(6, 16, 0, "EU", 0));
            BossDayEUList.add(new Kzarka(6, 19, 0, "EU", 0));
            BossDayEUList.add(new Karanda(6, 19, 0, "EU", 0));
            //Sunday
            BossDayEUList.add(new Nouver(7, 0, 15, "EU", 0));
            BossDayEUList.add(new Kutum(7, 0, 15, "EU", 0));
            BossDayEUList.add(new Kzarka(7, 2, 0, "EU", 0));
            BossDayEUList.add(new Kutum(7, 5, 0, "EU", 0));
            BossDayEUList.add(new Nouver(7, 9, 0, "EU", 0));
            BossDayEUList.add(new Kzarka(7, 12, 0, "EU", 0));
            BossDayEUList.add(new Vell(7, 16, 0, "EU", 0));
            BossDayEUList.add(new Garmoth(7, 19, 0, "EU", 0));
            BossDayEUList.add(new Kzarka(7, 22, 15, "EU", 0));
            BossDayEUList.add(new Nouver(7, 22, 15, "EU", 0));

            //----------------------------------NA BOSSES-----------------------------------------
            //Monday
            BossDayNAList.add(new Karanda(1, 0, 0, "NA", 0));
            BossDayNAList.add(new Kzarka(1, 3, 0, "NA", 0));
            BossDayNAList.add(new Kzarka(1, 7, 0, "NA", 0));
            BossDayNAList.add(new Offin(1, 10, 0, "NA", 0));
            BossDayNAList.add(new Kutum(1, 14, 0, "NA", 0));
            BossDayNAList.add(new Nouver(1, 17, 0, "NA", 0));
            BossDayNAList.add(new Kzarka(1, 20, 15, "NA", 0));
            BossDayNAList.add(new Karanda(1, 22, 15, "NA", 0));
            //Tuesday
            BossDayNAList.add(new Kutum(2, 0, 0, "NA", 0));
            BossDayNAList.add(new Kzarka(2, 3, 0, "NA", 0));
            BossDayNAList.add(new Nouver(2, 7, 0, "NA", 0));
            BossDayNAList.add(new Kutum(2, 10, 0, "NA", 0));
            BossDayNAList.add(new Nouver(2, 14, 0, "NA", 0));
            BossDayNAList.add(new Karanda(2, 17, 0, "NA", 0));
            BossDayNAList.add(new Garmoth(2, 20, 15, "NA", 0));
            BossDayNAList.add(new Kutum(2, 22, 15, "NA", 0));
            BossDayNAList.add(new Kzarka(2, 22, 15, "NA", 0));
            //Wednesday
            BossDayNAList.add(new Karanda(3, 0, 0, "NA", 0));
            BossDayNAList.add(new Karanda(3, 7, 0, "NA", 0));
            BossDayNAList.add(new Nouver(3, 10, 0, "NA", 0));
            BossDayNAList.add(new Kutum(3, 14, 0, "NA", 0));
            BossDayNAList.add(new Offin(3, 17, 0, "NA", 0));
            BossDayNAList.add(new Kzarka(3, 20, 15, "NA", 0));
            BossDayNAList.add(new Karanda(3, 20, 15, "NA", 0));
            BossDayNAList.add(new Quint(3, 21, 15, "NA", 0));
            BossDayNAList.add(new Nouver(3, 22, 15, "NA", 0));
            //Thursday
            BossDayNAList.add(new Kutum(4, 0, 0, "NA", 0));
            BossDayNAList.add(new Kzarka(4, 3, 0, "NA", 0));
            BossDayNAList.add(new Kutum(4, 7, 0, "NA", 0));
            BossDayNAList.add(new Nouver(4, 10, 0, "NA", 0));
            BossDayNAList.add(new Kzarka(4, 14, 0, "NA", 0));
            BossDayNAList.add(new Kutum(4, 17, 0, "NA", 0));
            BossDayNAList.add(new Garmoth(4, 20, 15, "NA", 0));
            BossDayNAList.add(new Kzarka(4, 22, 15, "NA", 0));
            BossDayNAList.add(new Karanda(4, 22, 15, "NA", 0));
            //Friday
            BossDayNAList.add(new Nouver(5, 0, 0, "NA", 0));
            BossDayNAList.add(new Karanda(5, 3, 0, "NA", 0));
            BossDayNAList.add(new Kutum(5, 7, 0, "NA", 0));
            BossDayNAList.add(new Karanda(5, 10, 0, "NA", 0));
            BossDayNAList.add(new Nouver(5, 14, 0, "NA", 0));
            BossDayNAList.add(new Kzarka(5, 17, 0, "NA", 0));
            BossDayNAList.add(new Kutum(5, 20, 15, "NA", 0));
            BossDayNAList.add(new Kzarka(5, 20, 15, "NA", 0));
            BossDayNAList.add(new Karanda(5, 22, 15, "NA", 0));
            //Saturday
            BossDayNAList.add(new Offin(6, 0, 0, "NA", 0));
            BossDayNAList.add(new Nouver(6, 3, 0, "NA", 0));
            BossDayNAList.add(new Kutum(6, 7, 0, "NA", 0));
            BossDayNAList.add(new Nouver(6, 10, 0, "NA", 0));
            BossDayNAList.add(new Quint(6, 14, 0, "NA", 0));
            BossDayNAList.add(new Kzarka(6, 17, 0, "NA", 0));
            BossDayNAList.add(new Karanda(6, 17, 0, "NA", 0));
            BossDayNAList.add(new Nouver(6, 22, 15, "NA", 0));
            BossDayNAList.add(new Kutum(6, 22, 15, "NA", 0));
            //Sunday
            BossDayNAList.add(new Kzarka(7, 0, 0, "NA", 0));
            BossDayNAList.add(new Kutum(7, 3, 0, "NA", 0));
            BossDayNAList.add(new Nouver(7, 7, 0, "NA", 0));
            BossDayNAList.add(new Kzarka(7, 10, 0, "NA", 0));
            BossDayNAList.add(new Vell(7, 14, 0, "NA", 0));
            BossDayNAList.add(new Garmoth(7, 17, 0, "NA", 0));
            BossDayNAList.add(new Kzarka(7, 20, 15, "NA", 0));
            BossDayNAList.add(new Nouver(7, 20, 15, "NA", 0));
            BossDayNAList.add(new Kutum(7, 22, 15, "NA", 0));
            BossDayNAList.add(new Karanda(7, 22, 15, "NA", 0));

            //----------------------------------SEA BOSSES-----------------------------------------
            //Monday
            BossDaySEAList.add(new Nouver(1, 0, 0, "SEA", 0));
            BossDaySEAList.add(new Kutum(1, 0, 0, "SEA", 0));
            BossDaySEAList.add(new Kzarka(1, 1, 30, "SEA", 0));
            BossDaySEAList.add(new Karanda(1, 7, 0, "SEA", 0));
            BossDaySEAList.add(new Nouver(1, 15, 0, "SEA", 0));
            BossDaySEAList.add(new Kzarka(1, 20, 0, "SEA", 0));
            //Tuesday
            BossDaySEAList.add(new Offin(2, 0, 0, "SEA", 0));
            BossDaySEAList.add(new Nouver(2, 1 , 30, "SEA", 0));
            BossDaySEAList.add(new Kutum(2, 7, 0, "SEA", 0));
            BossDaySEAList.add(new Karanda(2, 11, 0, "SEA", 0));
            BossDaySEAList.add(new Kzarka(2, 11, 0, "SEA", 0));
            BossDaySEAList.add(new Kutum(2, 15, 0, "SEA", 0));
            BossDaySEAList.add(new Nouver(2, 20, 0, "SEA", 0));
            //Wednesday
            BossDaySEAList.add(new Nouver(3, 0, 0, "SEA", 0));
            BossDaySEAList.add(new Kutum(3, 0, 0, "SEA", 0));
            BossDaySEAList.add(new Kzarka(3, 1, 30, "SEA", 0));
            BossDaySEAList.add(new Kzarka(3, 7, 0, "SEA", 0));
            BossDaySEAList.add(new Karanda(3, 15, 0, "SEA", 0));
            BossDaySEAList.add(new Kutum(3, 20, 0, "SEA", 0));
            //Thursday
            BossDaySEAList.add(new Offin(4, 0, 0, "SEA", 0));
            BossDaySEAList.add(new Kutum(4, 1, 30, "SEA", 0));
            BossDaySEAList.add(new Nouver(4, 7, 0, "SEA", 0));
            BossDaySEAList.add(new Kzarka(4, 11, 0, "SEA", 0));
            BossDaySEAList.add(new Kutum(4, 15, 0, "SEA", 0));
            BossDaySEAList.add(new Karanda(4, 20, 0, "SEA", 0));
            BossDaySEAList.add(new Kzarka(4, 20, 0, "SEA", 0));
            //Friday
            BossDaySEAList.add(new Kzarka(5, 0, 0, "SEA", 0));
            BossDaySEAList.add(new Nouver(5, 0, 0, "SEA", 0));
            BossDaySEAList.add(new Kzarka(5, 1, 30, "SEA", 0));
            BossDaySEAList.add(new Kutum(5, 7, 0, "SEA", 0));
            BossDaySEAList.add(new Kzarka(5, 15, 0, "SEA", 0));
            BossDaySEAList.add(new Nouver(5, 20, 0, "SEA", 0));
            //Saturday
            BossDaySEAList.add(new Offin(6, 0, 0, "SEA", 0));
            BossDaySEAList.add(new Karanda(6, 1, 30, "SEA", 0));
            BossDaySEAList.add(new Nouver(6, 7, 0, "SEA", 0));
            BossDaySEAList.add(new Kzarka(6, 11, 0, "SEA", 0));
            BossDaySEAList.add(new Kutum(6,  11,0, "SEA", 0));
            BossDaySEAList.add(new Karanda(6, 15, 0, "SEA", 0));
            BossDaySEAList.add(new Nouver(6, 15, 0, "SEA", 0));
            BossDaySEAList.add(new Quint(6, 20, 0, "SEA", 0));
            // Sunday
            BossDaySEAList.add(new Karanda(7, 1, 30, "SEA", 0));
            BossDaySEAList.add(new Kutum(7, 7, 0, "SEA", 0));
            BossDaySEAList.add(new Karanda(7, 11, 0, "SEA", 0));
            BossDaySEAList.add(new Kzarka(7, 11, 0, "SEA", 0));
            BossDaySEAList.add(new Kutum(7, 15, 0, "SEA", 0));
            BossDaySEAList.add(new Nouver(7, 15, 0, "SEA", 0));
            BossDaySEAList.add(new Karanda(7, 20, 0, "SEA", 0));

            Gson gson=new Gson();
            String jsonEu=gson.toJson(BossDayEUList);
            String jsonNa=gson.toJson(BossDayNAList);
            String jsonSea=gson.toJson(BossDaySEAList);
            prefs.edit().putString("EuList",jsonEu).apply();
            prefs.edit().putString("NaList",jsonNa).apply();
            prefs.edit().putString("SeaList",jsonSea).apply();
            startActivity(intentFirstTime);
            prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();

            return;
        }
        else if (currentVersionCode > savedVersionCode)
        {
            // update
            ArrayList<Boss> BossDayEUList = new ArrayList<>();
            ArrayList<Boss> BossDayNAList = new ArrayList<>();
            ArrayList<Boss> BossDaySEAList= new ArrayList<>();

            //----------------------------------EU BOSSES-----------------------------------------
            //Monday
            BossDayEUList.add(new Kutum(1, 0, 15, "EU", 0));
            BossDayEUList.add(new Karanda(1, 0, 15, "EU", 0));
            BossDayEUList.add(new Karanda(1, 2, 0, "EU", 0));
            BossDayEUList.add(new Kzarka(1, 5, 0, "EU", 0));
            BossDayEUList.add(new Kzarka(1, 9, 0, "EU", 0));
            BossDayEUList.add(new Offin(1, 12, 0, "EU", 0));
            BossDayEUList.add(new Kutum(1, 16, 0, "EU", 0));
            BossDayEUList.add(new Nouver(1, 19, 0, "EU", 0));
            BossDayEUList.add(new Kzarka(1, 22, 15, "EU", 0));
            //Tuesday
            BossDayEUList.add(new Karanda(2, 0, 15, "EU", 0));
            BossDayEUList.add(new Kutum(2, 2, 0, "EU", 0));
            BossDayEUList.add(new Kzarka(2, 5, 0, "EU", 0));
            BossDayEUList.add(new Nouver(2, 9, 0, "EU", 0));
            BossDayEUList.add(new Kutum(2, 12, 0, "EU", 0));
            BossDayEUList.add(new Nouver(2, 16, 0, "EU", 0));
            BossDayEUList.add(new Karanda(2, 19, 0, "EU", 0));
            BossDayEUList.add(new Garmoth(2, 22, 15, "EU", 0));

            //Wednesday
            BossDayEUList.add(new Kutum(3, 0, 15, "EU", 0));
            BossDayEUList.add(new Kzarka(3, 0, 15, "EU", 0));
            BossDayEUList.add(new Karanda(3, 2, 0, "EU", 0));
            BossDayEUList.add(new Kzarka(3, 5, 0, "EU", 0));
            BossDayEUList.add(new Karanda(3, 9, 0, "EU", 0));
            BossDayEUList.add(new Kutum(3, 16, 0, "EU", 0));
            BossDayEUList.add(new Offin(3, 19, 0, "EU", 0));
            BossDayEUList.add(new Karanda(3, 22, 15, "EU", 0));
            BossDayEUList.add(new Kzarka(3, 22, 15, "EU", 0));
            BossDayEUList.add(new Quint(3, 23, 15, "EU", 0));
            //Thursday
            BossDayEUList.add(new Nouver(4, 0, 15, "EU", 0));
            BossDayEUList.add(new Kutum(4, 2, 0, "EU", 0));
            BossDayEUList.add(new Nouver(4, 5, 0, "EU", 0));
            BossDayEUList.add(new Kutum(4, 9, 0, "EU", 0));
            BossDayEUList.add(new Nouver(4, 12, 0, "EU", 0));
            BossDayEUList.add(new Kzarka(4, 16, 0, "EU", 0));
            BossDayEUList.add(new Kutum(4, 19, 0, "EU", 0));
            BossDayEUList.add(new Garmoth(4, 22, 15, "EU", 0));
            //Friday
            BossDayEUList.add(new Kzarka(5, 0, 15, "EU", 0));
            BossDayEUList.add(new Karanda(5, 0, 15, "EU", 0));
            BossDayEUList.add(new Nouver(5, 2, 0, "EU", 0));
            BossDayEUList.add(new Karanda(5, 5, 0, "EU", 0));
            BossDayEUList.add(new Kutum(5, 9, 0, "EU", 0));
            BossDayEUList.add(new Karanda(5, 12, 0, "EU", 0));
            BossDayEUList.add(new Nouver(5, 16, 0, "EU", 0));
            BossDayEUList.add(new Kzarka(5, 19, 0, "EU", 0));
            BossDayEUList.add(new Kzarka(5, 22, 15, "EU", 0));
            BossDayEUList.add(new Kutum(5, 22, 15, "EU", 0));
            //Saturday
            BossDayEUList.add(new Karanda(6, 0, 15, "EU", 0));
            BossDayEUList.add(new Offin(6, 2, 0, "EU", 0));
            BossDayEUList.add(new Nouver(6, 5, 0, "EU", 0));
            BossDayEUList.add(new Kutum(6, 9, 0, "EU", 0));
            BossDayEUList.add(new Nouver(6, 12, 0, "EU", 0));
            BossDayEUList.add(new Quint(6, 16, 0, "EU", 0));
            BossDayEUList.add(new Kzarka(6, 19, 0, "EU", 0));
            BossDayEUList.add(new Karanda(6, 19, 0, "EU", 0));
            //Sunday
            BossDayEUList.add(new Nouver(7, 0, 15, "EU", 0));
            BossDayEUList.add(new Kutum(7, 0, 15, "EU", 0));
            BossDayEUList.add(new Kzarka(7, 2, 0, "EU", 0));
            BossDayEUList.add(new Kutum(7, 5, 0, "EU", 0));
            BossDayEUList.add(new Nouver(7, 9, 0, "EU", 0));
            BossDayEUList.add(new Kzarka(7, 12, 0, "EU", 0));
            BossDayEUList.add(new Vell(7, 16, 0, "EU", 0));
            BossDayEUList.add(new Garmoth(7, 19, 0, "EU", 0));
            BossDayEUList.add(new Kzarka(7, 22, 15, "EU", 0));
            BossDayEUList.add(new Nouver(7, 22, 15, "EU", 0));

            //----------------------------------NA BOSSES-----------------------------------------
            //Monday
            BossDayNAList.add(new Karanda(1, 0, 0, "NA", 0));
            BossDayNAList.add(new Kzarka(1, 3, 0, "NA", 0));
            BossDayNAList.add(new Kzarka(1, 7, 0, "NA", 0));
            BossDayNAList.add(new Offin(1, 10, 0, "NA", 0));
            BossDayNAList.add(new Kutum(1, 14, 0, "NA", 0));
            BossDayNAList.add(new Nouver(1, 17, 0, "NA", 0));
            BossDayNAList.add(new Kzarka(1, 20, 15, "NA", 0));
            BossDayNAList.add(new Karanda(1, 22, 15, "NA", 0));
            //Tuesday
            BossDayNAList.add(new Kutum(2, 0, 0, "NA", 0));
            BossDayNAList.add(new Kzarka(2, 3, 0, "NA", 0));
            BossDayNAList.add(new Nouver(2, 7, 0, "NA", 0));
            BossDayNAList.add(new Kutum(2, 10, 0, "NA", 0));
            BossDayNAList.add(new Nouver(2, 14, 0, "NA", 0));
            BossDayNAList.add(new Karanda(2, 17, 0, "NA", 0));
            BossDayNAList.add(new Garmoth(2, 20, 15, "NA", 0));
            BossDayNAList.add(new Kutum(2, 22, 15, "NA", 0));
            BossDayNAList.add(new Kzarka(2, 22, 15, "NA", 0));
            //Wednesday
            BossDayNAList.add(new Karanda(3, 0, 0, "NA", 0));
            BossDayNAList.add(new Karanda(3, 7, 0, "NA", 0));
            BossDayNAList.add(new Nouver(3, 10, 0, "NA", 0));
            BossDayNAList.add(new Kutum(3, 14, 0, "NA", 0));
            BossDayNAList.add(new Offin(3, 17, 0, "NA", 0));
            BossDayNAList.add(new Kzarka(3, 20, 15, "NA", 0));
            BossDayNAList.add(new Karanda(3, 20, 15, "NA", 0));
            BossDayNAList.add(new Quint(3, 21, 15, "NA", 0));
            BossDayNAList.add(new Nouver(3, 22, 15, "NA", 0));
            //Thursday
            BossDayNAList.add(new Kutum(4, 0, 0, "NA", 0));
            BossDayNAList.add(new Kzarka(4, 3, 0, "NA", 0));
            BossDayNAList.add(new Kutum(4, 7, 0, "NA", 0));
            BossDayNAList.add(new Nouver(4, 10, 0, "NA", 0));
            BossDayNAList.add(new Kzarka(4, 14, 0, "NA", 0));
            BossDayNAList.add(new Kutum(4, 17, 0, "NA", 0));
            BossDayNAList.add(new Garmoth(4, 20, 15, "NA", 0));
            BossDayNAList.add(new Kzarka(4, 22, 15, "NA", 0));
            BossDayNAList.add(new Karanda(4, 22, 15, "NA", 0));
            //Friday
            BossDayNAList.add(new Nouver(5, 0, 0, "NA", 0));
            BossDayNAList.add(new Karanda(5, 3, 0, "NA", 0));
            BossDayNAList.add(new Kutum(5, 7, 0, "NA", 0));
            BossDayNAList.add(new Karanda(5, 10, 0, "NA", 0));
            BossDayNAList.add(new Nouver(5, 14, 0, "NA", 0));
            BossDayNAList.add(new Kzarka(5, 17, 0, "NA", 0));
            BossDayNAList.add(new Kutum(5, 20, 15, "NA", 0));
            BossDayNAList.add(new Kzarka(5, 20, 15, "NA", 0));
            BossDayNAList.add(new Karanda(5, 22, 15, "NA", 0));
            //Saturday
            BossDayNAList.add(new Offin(6, 0, 0, "NA", 0));
            BossDayNAList.add(new Nouver(6, 3, 0, "NA", 0));
            BossDayNAList.add(new Kutum(6, 7, 0, "NA", 0));
            BossDayNAList.add(new Nouver(6, 10, 0, "NA", 0));
            BossDayNAList.add(new Quint(6, 14, 0, "NA", 0));
            BossDayNAList.add(new Kzarka(6, 17, 0, "NA", 0));
            BossDayNAList.add(new Karanda(6, 17, 0, "NA", 0));
            BossDayNAList.add(new Nouver(6, 22, 15, "NA", 0));
            BossDayNAList.add(new Kutum(6, 22, 15, "NA", 0));
            //Sunday
            BossDayNAList.add(new Kzarka(7, 0, 0, "NA", 0));
            BossDayNAList.add(new Kutum(7, 3, 0, "NA", 0));
            BossDayNAList.add(new Nouver(7, 7, 0, "NA", 0));
            BossDayNAList.add(new Kzarka(7, 10, 0, "NA", 0));
            BossDayNAList.add(new Vell(7, 14, 0, "NA", 0));
            BossDayNAList.add(new Garmoth(7, 17, 0, "NA", 0));
            BossDayNAList.add(new Kzarka(7, 20, 15, "NA", 0));
            BossDayNAList.add(new Nouver(7, 20, 15, "NA", 0));
            BossDayNAList.add(new Kutum(7, 22, 15, "NA", 0));
            BossDayNAList.add(new Karanda(7, 22, 15, "NA", 0));
           
            //----------------------------------SEA BOSSES-----------------------------------------
            //Monday
            BossDaySEAList.add(new Nouver(1, 0, 0, "SEA", 0));
            BossDaySEAList.add(new Kutum(1, 0, 0, "SEA", 0));
            BossDaySEAList.add(new Kzarka(1, 1, 30, "SEA", 0));
            BossDaySEAList.add(new Karanda(1, 7, 0, "SEA", 0));
            BossDaySEAList.add(new Nouver(1, 15, 0, "SEA", 0));
            BossDaySEAList.add(new Kzarka(1, 20, 0, "SEA", 0));
            //Tuesday
            BossDaySEAList.add(new Offin(2, 0, 0, "SEA", 0));
            BossDaySEAList.add(new Nouver(2, 1 , 30, "SEA", 0));
            BossDaySEAList.add(new Kutum(2, 7, 0, "SEA", 0));
            BossDaySEAList.add(new Karanda(2, 11, 0, "SEA", 0));
            BossDaySEAList.add(new Kzarka(2, 11, 0, "SEA", 0));
            BossDaySEAList.add(new Kutum(2, 15, 0, "SEA", 0));
            BossDaySEAList.add(new Nouver(2, 20, 0, "SEA", 0));
            //Wednesday
            BossDaySEAList.add(new Nouver(3, 0, 0, "SEA", 0));
            BossDaySEAList.add(new Kutum(3, 0, 0, "SEA", 0));
            BossDaySEAList.add(new Kzarka(3, 1, 30, "SEA", 0));
            BossDaySEAList.add(new Kzarka(3, 7, 0, "SEA", 0));
            BossDaySEAList.add(new Karanda(3, 15, 0, "SEA", 0));
            BossDaySEAList.add(new Kutum(3, 20, 0, "SEA", 0));
            //Thursday
            BossDaySEAList.add(new Offin(4, 0, 0, "SEA", 0));
            BossDaySEAList.add(new Kutum(4, 1, 30, "SEA", 0));
            BossDaySEAList.add(new Nouver(4, 7, 0, "SEA", 0));
            BossDaySEAList.add(new Kzarka(4, 11, 0, "SEA", 0));
            BossDaySEAList.add(new Kutum(4, 15, 0, "SEA", 0));
            BossDaySEAList.add(new Karanda(4, 20, 0, "SEA", 0));
            BossDaySEAList.add(new Kzarka(4, 20, 0, "SEA", 0));
            //Friday
            BossDaySEAList.add(new Kzarka(5, 0, 0, "SEA", 0));
            BossDaySEAList.add(new Nouver(5, 0, 0, "SEA", 0));
            BossDaySEAList.add(new Kzarka(5, 1, 30, "SEA", 0));
            BossDaySEAList.add(new Kutum(5, 7, 0, "SEA", 0));
            BossDaySEAList.add(new Kzarka(5, 15, 0, "SEA", 0));
            BossDaySEAList.add(new Nouver(5, 20, 0, "SEA", 0));
            //Saturday
             BossDaySEAList.add(new Offin(6, 0, 0, "SEA", 0));
            BossDaySEAList.add(new Karanda(6, 1, 30, "SEA", 0));
            BossDaySEAList.add(new Nouver(6, 7, 0, "SEA", 0));
            BossDaySEAList.add(new Kzarka(6, 11, 0, "SEA", 0));
            BossDaySEAList.add(new Kutum(6, 11,0, "SEA", 0));
            BossDaySEAList.add(new Karanda(6, 15, 0, "SEA", 0));
            BossDaySEAList.add(new Nouver(6, 15, 0, "SEA", 0));
            BossDaySEAList.add(new Quint(6, 20, 0, "SEA", 0));
            // Sunday
            BossDaySEAList.add(new Karanda(7, 1, 30, "SEA", 0));
            BossDaySEAList.add(new Kutum(7, 7, 0, "SEA", 0));
            BossDaySEAList.add(new Karanda(7, 11, 0, "SEA", 0));
            BossDaySEAList.add(new Kzarka(7, 11, 0, "SEA", 0));
            BossDaySEAList.add(new Kutum(7, 15, 0, "SEA", 0));
            BossDaySEAList.add(new Nouver(7, 15, 0, "SEA", 0));
            BossDaySEAList.add(new Karanda(7, 20, 0, "SEA", 0));
          
            Gson gson=new Gson();
            String jsonEu=gson.toJson(BossDayEUList);
            String jsonNa=gson.toJson(BossDayNAList);
            String jsonSea=gson.toJson(BossDaySEAList);
            prefs.edit().putString("EuList",jsonEu).apply();
            prefs.edit().putString("NaList",jsonNa).apply();
            prefs.edit().putString("SeaList",jsonSea).apply();
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
    }

    public void createNotificationChannel()
    {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
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



    private void Preferences()
    {
        bCalendar = Calendar.getInstance();
        hour = bCalendar.get(Calendar.HOUR_OF_DAY);
        minute = bCalendar.get(Calendar.MINUTE);
        MDay = bCalendar.get(Calendar.DAY_OF_MONTH);
        Wday = bCalendar.get(Calendar.DAY_OF_WEEK);
        offset = (bCalendar.get(Calendar.DST_OFFSET) + bCalendar.get(Calendar.ZONE_OFFSET)) / 3600000;
        BossDayList.clear();
        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int currentServerSelection = prefs.getInt(PREF_SERVER_CONSTANT, DOESNT_EXIST);
        // Log.d("server",""+(currentServerSelection==3));
        if (currentServerSelection == 1)
        {
            ServerSelection(BossDayEUList, "+1");
        }
        else if (currentServerSelection == 2)
        {
            ServerSelection(BossDayNAList, "-8");
        }
        else if (currentServerSelection == 3)
        {
                ServerSelection(BossDaySEAList, "+8");
        }
    }


    public void setAdvListener() {
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-6028798031014902~9858159713");
        mInterstitialAd = new InterstitialAd(getApplicationContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-6028798031014902/3127307828");
        //test:ca-app-pub-3940256099942544/1033173712      official:  ca-app-pub-6028798031014902/3127307828
        getUserGDPRConsent = prefs.getInt(GDPRCONSENT , DOESNT_EXIST);
        if(getUserGDPRConsent != 1) {
            Bundle extras = new Bundle();
            extras.putString("npa", "1");

            mInterstitialAd.loadAd(new AdRequest.Builder()
                    .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                    .build());
        }else {
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
        }
        if (mInterstitialAd.isLoaded()) {
            // mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }

        final CountDownTimer Adtimer = new CountDownTimer(7000, 1000) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                adAlreadyAppeared = true;
                mInterstitialAd.show();

            }
        };

        if (!adAlreadyAppeared) {
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    Adtimer.start();
                    //mInterstitialAd.show();
                }
                @Override
                public void onAdClosed() {
                    mInterstitialAd.setAdListener(null);
                   //mInterstitialAd.show();
                }

            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mInterstitialAd.setAdListener(null);

    }
}