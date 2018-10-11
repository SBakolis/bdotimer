package com.penstack.dbobosstimer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Timestamp;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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
    public final ArrayList<Boss> BossDayMList = new ArrayList<>();
    private RecyclerView listView;
    private BossAdapter BossAdapter;
    private Calendar bCalendar;
    public int Wday;
    public int minute;
    public String s;
    public int nextDay;
    public long n,diff;
    public ImageView settButton;
    Intent intentSettings ;
    Intent intentFirstTime;

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


        //Monday
        BossDayMList.add(new Kutum(2,00,15,"UTC+2",0)) ;
        BossDayMList.add(new Karanda(2,02,00,"UTC+2",0)) ;
        BossDayMList.add(new Kzarka(2,05,00,"UTC+2",0));
         BossDayMList.add(new Kzarka(2,9, 00, "UTC+2",0));
        BossDayMList.add(new Nouver(2,12,00,"UTC+2",0)) ;
        BossDayMList.add(new Kutum( 2,16,00,"UTC+2",0)) ;
        BossDayMList.add(new Nouver(2,19,00,"UTC+2",0)) ;
        BossDayMList.add(new Kzarka(2,22,15,"UTC+2",0)) ;
        //Tuesday
        BossDayMList.add(new Karanda(3,00,15,"UTC+2",0)) ;
        BossDayMList.add(new Kutum(3,02,00,"UTC+2",0)) ;
        BossDayMList.add(new Kzarka(3,05,00,"UTC+2",0)) ;
        BossDayMList.add(new Kutum(3,9,00,"UTC+2",0)) ;
        BossDayMList.add(new  Offin(3,12,00,"UTC+2",0)) ;
        BossDayMList.add(new Nouver(3,16,00,"UTC+2",0)) ;
        BossDayMList.add(new Karanda(3,19,00,"UTC+2",0)) ;
        BossDayMList.add(new Kzarka(3,22,15,"UTC+2",0)) ;
        BossDayMList.add(new Nouver(3,22,15,"UTC+2",0)) ;
        //Wednesday
        BossDayMList.add(new Kutum(4,00,15,"UTC+2",0)) ;
        BossDayMList.add(new Karanda(4,02,00,"UTC+2",0)) ;
        BossDayMList.add(new Kzarka(4,05,00,"UTC+2",0)) ;
        BossDayMList.add(new Karanda(4,9,00,"UTC+2",0)) ;
        BossDayMList.add(new Kzarka(4,16,00,"UTC+2",0)) ;
        BossDayMList.add(new Kutum(4,19,00,"UTC+2",0)) ;
        BossDayMList.add(new Karanda(4,22,15,"UTC+2",0)) ;
        BossDayMList.add(new Kzarka(4,22,15,"UTC+2",0)) ;
        //Thursday
        BossDayMList.add(new Nouver(5,00,15,"UTC+2",0)) ;
        BossDayMList.add(new Kutum(5,02,00,"UTC+2",0)) ;
        BossDayMList.add(new Nouver(5,05,00,"UTC+2",0)) ;
        BossDayMList.add(new Kutum(5,9,00,"UTC+2",0)) ;
        BossDayMList.add(new Nouver(5,12,00,"UTC+2",0)) ;
        BossDayMList.add(new Kutum(5,16,00,"UTC+2",0)) ;
        BossDayMList.add(new Offin(5,19,00,"UTC+2",0)) ;
        BossDayMList.add(new Karanda(5,22,15,"UTC+2",0)) ;

        //Friday
        BossDayMList.add(new Kzarka(6,00,15,"UTC+2",0));
        BossDayMList.add(new Nouver(6,02,00,"UTC+2",0));
        BossDayMList.add(new Karanda(6,05,00,"UTC+2",0));
        BossDayMList.add(new Kutum(6,9,00,"UTC+2",0));
        BossDayMList.add(new Karanda(6,12,00,"UTC+2",0));
        BossDayMList.add(new Nouver(6,16,00,"UTC+2",0));
        BossDayMList.add(new Kzarka(6,19,00,"UTC+2",0));
        BossDayMList.add(new Kzarka(6,22,15,"UTC+2",0));
        BossDayMList.add(new Kutum(6,22,15,"UTC+2",0));
        //Saturday
        BossDayMList.add(new Karanda(7,00,15,"UTC+2",0));
        BossDayMList.add(new Offin(7,02,00,"UTC+2",0));
        BossDayMList.add(new Nouver(7,05,00,"UTC+2",0));
        BossDayMList.add(new Kutum(7,9,00,"UTC+2",0));
        BossDayMList.add(new Nouver(7,12,00,"UTC+2",0));
        BossDayMList.add(new Quint(7,16,00,"UTC+2",0));
        BossDayMList.add(new Kzarka(7,19,00,"UTC+2",0));
        BossDayMList.add(new Karanda(7,19,00,"UTC+2",0));
        //Sunday
        BossDayMList.add(new Nouver(1,00,15,"UTC+2",0));
        BossDayMList.add(new Kutum(1,00,15,"UTC+2",0));
        BossDayMList.add(new Kzarka(1,02,00,"UTC+2",0));
        BossDayMList.add(new Kutum(1,05,00,"UTC+2",0));
        BossDayMList.add(new Nouver(1,9,00,"UTC+2",0));
        BossDayMList.add(new Kzarka(1,12,00,"UTC+2",0));
        BossDayMList.add(new Vell(1,16,00,"UTC+2",0));
        BossDayMList.add(new Karanda(1,19,00,"UTC+2",0));
        BossDayMList.add(new Kzarka(1,22,15,"UTC+2",0));
        BossDayMList.add(new Nouver(1,22,15,"UTC+2",0));


        bCalendar = Calendar.getInstance();
        hour = bCalendar.get(Calendar.HOUR_OF_DAY);
        minute=bCalendar.get(Calendar.MINUTE);
        MDay = bCalendar.get(Calendar.DAY_OF_MONTH);
        Wday = bCalendar.get(Calendar.DAY_OF_WEEK);
        offset =(bCalendar.get(Calendar.DST_OFFSET)+bCalendar.get(Calendar.ZONE_OFFSET))/3600000;
        int month=bCalendar.get(Calendar.MONTH);
        TimeZone tz=TimeZone.getDefault();
        TimeZone tz2=TimeZone.getTimeZone("GMT+2");
        for( i=0;i<BossDayMList.size();i++){



             String v=Hours(Integer.toString(hour),offset);//briskei thn wra tou xrhsth gia na th sugkrinei me twn bosses
             int RealHour=Integer.parseInt(v);
           // ta ekana int ola sto Boss object  gia na kanw pio grhgora prakseis,vriskei ta boss auths ths hmeras kai ths epomenhs p prolavainei o xrhsths
            if((Wday==BossDayMList.get(i).getBossDay() && (RealHour<BossDayMList.get(i).getBossHour()) || RealHour==BossDayMList.get(i).getBossHour()&& minute<BossDayMList.get(i).getBossMin())){

                s=+bCalendar.get(Calendar.YEAR)+"-"+(bCalendar.get(Calendar.MONTH)+1)+"-"+MDay+" "+BossDayMList.get(i).getBossHour()+":"+BossDayMList.get(i).getBossMin()+":00";
                countdown = Time(s,offset);

                day = bCalendar.getTimeInMillis();
                diff=(countdown-day);
                BossDayMList.get(i).setTimeLeft(diff);
                Boss nextBoss=BossDayMList.get(i);
                BossDayList.add(nextBoss);
            }
            else if(Wday==(BossDayMList.get(i).getBossDay()-1)){
                s=bCalendar.get(Calendar.YEAR)+"-"+(bCalendar.get(Calendar.MONTH)+1)+"-"+(MDay+1)+" "+BossDayMList.get(i).getBossHour()+":"+BossDayMList.get(i).getBossMin()+":00";
                countdown=Time(s,offset);
                day = bCalendar.getTimeInMillis();
                diff=(countdown-day);
                BossDayMList.get(i).setTimeLeft(diff);
                Boss nextBoss=BossDayMList.get(i);
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
    public String  Hours(String v,int offset){ //vriskei thn  wra tou xrhsth se sxesh me tou server gia na th sugkrinei me auth twn bosses
        SimpleDateFormat hour = new SimpleDateFormat("H");
        //String test=timeZone.getID();
        hour.setTimeZone(getTimeZone("GMT+"+offset));//"GMT+"+offset.getOffset(new Date().getTime())));
        ParsePosition pos = new ParsePosition(0);
        k = hour.parse(v, pos);
        hour.setTimeZone(getTimeZone("GMT+2"));
        String time=hour.format(k);

        return time;
    }
    public  long Time(String s,int offset){

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d H:mm:ss");//tou lew pws na ta emfanizei
            sdf.setTimeZone(getTimeZone("GMT+2"));//tou lew to timezone tou string pou tou dinw
            //sdf.setTimeZone(TimeZone.getTimeZone("GMT+" + bCalendar.get(Calendar.DST_OFFSET) + ""));
            ParsePosition pos = new ParsePosition(0);//tou lew apo pou na arxisei na diavazei,dld apo thn arxh
            k = sdf.parse(s, pos);//parsarei to string sto Date k

            long n = k.getTime();


                sdf.setTimeZone(getTimeZone("GMT+"+offset));//.getOffset(new Date().getTime())));//allazoume apo gmt+UTC+2(edw ==CEST,otan tha ginei CET tha allaksoume apla thn wra stis listes tou EU) se auto tou xrhsth

            String time = sdf.format(k);//to emfanizw me to format p to dwsa sto sdf
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

        } else if (currentVersionCode > savedVersionCode) {

            // update
            return;
        }

        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
    }

}
