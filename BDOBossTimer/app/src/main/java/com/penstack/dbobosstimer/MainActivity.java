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




        BossDayList.add(new Kutum("Monday","00:15","UTC+2")) ;
        BossDayList.add(new Karanda("Monday","02:00","UTC+2")) ;
        BossDayList.add(new Kzarka("Monday","05:00","UTC+2"));
        BossDayList.add(new Kzarka("Monday","09:00","UTC+2")) ;
        BossDayList.add(new Nouver("Monday","12:00","UTC+2")) ;
        BossDayList.add(new Kutum( "Monday","16:00","UTC+2")) ;
        BossDayList.add(new Nouver("Monday","19:00","UTC+2")) ;
        BossDayList.add(new Kzarka("Monday","22:15","UTC+2")) ;
        BossDayList.add(new Karanda("Tuesday","00:15","UTC+2")) ;
        BossDayList.add(new Kutum("Tuesday","02:00","UTC+2")) ;
        BossDayList.add(new Kzarka("Tuesday","05:00","UTC+2")) ;
        BossDayList.add(new Kutum("Tuesday","09:00","UTC+2")) ;
        BossDayList.add(new  Offin("Tuesday","12:00","UTC+2")) ;
        BossDayList.add(new Nouver("Tuesday","16:00","UTC+2")) ;
        BossDayList.add(new Karanda("Tuesday","19:00","UTC+2")) ;
        BossDayList.add(new Kzarka("Tuesday","22:15","UTC+2")) ;
        BossDayList.add(new Nouver("Tuesday","22:15","UTC+2")) ;
        BossDayList.add(new Kutum("Wednesday","00:15","UTC+2")) ;
        BossDayList.add(new Karanda("Wednesday","02:00","UTC+2")) ;
        BossDayList.add(new Kzarka("Wednesday","05:00","UTC+2")) ;
        BossDayList.add(new Karanda("Wednesday","09:00","UTC+2")) ;
        BossDayList.add(new Kzarka("Wednesday","16:00","UTC+2")) ;
        BossDayList.add(new Kutum("Wednesday","19:00","UTC+2")) ;
        BossDayList.add(new Karanda("Wednesday","22:15","UTC+2")) ;
        BossDayList.add(new Kzarka("Wednesday","22:15","UTC+2")) ;
        BossDayList.add(new Nouver("Thursday","00:15    ","UTC+2")) ;
        BossDayList.add(new Kutum("Thursday","02:00","UTC+2")) ;
        BossDayList.add(new Nouver("Thursday","05:00","UTC+2")) ;
        BossDayList.add(new Kutum("Thursday","09:00","UTC+2")) ;
        BossDayList.add(new Nouver("Thursday","12:00","UTC+2")) ;
        BossDayList.add(new Kutum("Thursday","16:00","UTC+2")) ;
        BossDayList.add(new Offin("Thursday","19:00","UTC+2")) ;
        BossDayList.add(new Karanda("Thursday","22:15","UTC+2")) ;


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



        //sdf.setTimeZone(TimeZone.getDefault());//offset.getTimeZone("GMT+"+bCalendar.get(Calendar.DST_OFFSET)+""));//allazoume apo gmt+UTC+2(edw ==CEST,otan tha ginei CET tha allaksoume apla thn wra stis listes tou EU) se auto tou xrhsth

        String time=sdf.format(k);//to emfanizw me to format p to dwsa sto sdf
        return n; //time;*/
    }
}
