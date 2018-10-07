package com.penstack.dbobosstimer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import static java.lang.Long.valueOf;

public class MainActivity extends AppCompatActivity {

public long countdown,day;
    public Date k;
     public Timestamp BossTimestamp;
    public int i;
    public int hour;
    public TimeZone offset;
    public TextView text1;
    public final ArrayList<Boss> BossDayList = new ArrayList<>();
    private ListView listView;
    private BossAdapter BossAdapter;
    private Calendar bCalendar;
    public String dayLongName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView0);
        TextView text1=(TextView) findViewById(R.id.BossName);


         Boss testBoss = new Karanda(".","1","1");
        Log.d("test", testBoss.getBossName());
        BossDayList.add(testBoss) ;
        BossDayList.add(new Kzarka("2","1","1"));
        //Friday
        BossDayList.add(new Kzarka("Friday","00:15","UTC+2"));
        BossDayList.add(new Nouver("Friday","02:00","UTC+2"));
        BossDayList.add(new Karanda("Friday","05:00","UTC+2"));
        BossDayList.add(new Kutum("Friday","09:00","UTC+2"));
        BossDayList.add(new Karanda("Friday","12:00","UTC+2"));
        BossDayList.add(new Nouver("Friday","16:00","UTC+2"));
        BossDayList.add(new Kzarka("Friday","19:00","UTC+2"));
        BossDayList.add(new Kzarka("Friday","22:15","UTC+2"));
        BossDayList.add(new Kutum("Friday","22:15","UTC+2"));
        //Saturday
        BossDayList.add(new Karanda("Saturday","00:15","UTC+2"));
        BossDayList.add(new Offin("Saturday","02:00","UTC+2"));
        BossDayList.add(new Nouver("Saturday","05:00","UTC+2"));
        BossDayList.add(new Kutum("Saturday","09:00","UTC+2"));
        BossDayList.add(new Nouver("Saturday","12:00","UTC+2"));
        BossDayList.add(new Quint("Saturday","16:00","UTC+2"));
        BossDayList.add(new Kzarka("Saturday","19:00","UTC+2"));
        BossDayList.add(new Karanda("Saturday","19:00","UTC+2"));
        //Sunday
        BossDayList.add(new Nouver("Sunday","00:15","UTC+2"));
        BossDayList.add(new Kutum("Sunday","00:15","UTC+2"));
        BossDayList.add(new Kzarka("Sunday","02:00","UTC+2"));
        BossDayList.add(new Kutum("Sunday","05:00","UTC+2"));
        BossDayList.add(new Nouver("Sunday","09:00","UTC+2"));
        BossDayList.add(new Kzarka("Sunday","12:00","UTC+2"));
        BossDayList.add(new Vell("Sunday","16:00","UTC+2"));
        BossDayList.add(new Karanda("Sunday","19:00","UTC+2"));
        BossDayList.add(new Kzarka("Sunday","22:15","UTC+2"));
        BossDayList.add(new Nouver("Sunday","22:15","UTC+2"));

        bCalendar = Calendar.getInstance();
        hour = bCalendar.get(Calendar.HOUR_OF_DAY);
        dayLongName = bCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
        offset = bCalendar.getTimeZone();
        
        BossAdapter = new BossAdapter(this, BossDayList);
        listView.setAdapter(BossAdapter);
        /*for (i = 0; i < BossDayList.size(); i++) {

            countdown = Time(BossDayList.get(i).getBossTime());//pairnw kathe object ths listas kai to metatrepw apo gmt+2 sto default tou xrhsth,edw xrhsimopoioume mono gia EU
            day = bCalendar.getTimeInMillis();
            BossDayList.get(i).setBossTime("Time" + ((countdown - day)));

        }
        text1 = (TextView) findViewById(R.id.editText);

            new CountDownTimer((countdown - day), 1000) {

                public void onTick(long millisUntilFinished) {
                    text1.setText("Remaining" + (millisUntilFinished / 1000));// BossDayList.get(0).setBossTime( "Remaining"+(millisUntilFinished/1000));

                }
                public void onFinish() {
                    text1.setText("done!");

                }
            }.start();


        BossAdapter = new BossAdapter(this, BossDayList);
        listView.setAdapter(BossAdapter);

    }

    public  long Time(String s){

        SimpleDateFormat sdf=new SimpleDateFormat("yy-MM-dd HH:mm:ss z");//tou lew pws na ta emfanizei
            //sdf.setTimeZone(TimeZone.getTimeZone("GMT+2"));//tou lew to timezone tou string pou tou dinw
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+"+bCalendar.get(Calendar.DST_OFFSET)+""));
        ParsePosition pos=new ParsePosition(0);//tou lew apo pou na arxisei na diavazei,dld apo thn arxh
         k=sdf.parse(s,pos);//parsarei to string sto Date k

        long n=k.getTime();



        //sdf.setTimeZone(TimeZone.getDefault());//offset.getTimeZone("GMT+"+bCalendar.get(Calendar.DST_OFFSET)+""));//allazoume apo gmt+1(edw ==CEST,otan tha ginei CET tha allaksoume apla thn wra stis listes tou EU) se auto tou xrhsth

        String time=sdf.format(k);//to emfanizw me to format p to dwsa sto sdf
        return n; //time;*/


    }
}
