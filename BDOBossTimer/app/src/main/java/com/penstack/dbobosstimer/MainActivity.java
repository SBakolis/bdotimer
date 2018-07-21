package com.penstack.dbobosstimer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

     public Timestamp BossTimestamp;
    public int hour;
    public TextView BossTime;
    public final ArrayList<Boss> BossDayList = new ArrayList<>();
    private ListView listView;
    private BossAdapter BossAdapter;
    private Calendar bCalendar;
    public String dayLongName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=(ListView) findViewById(R.id.listView0);

        final ArrayList<Boss> MondayList=new ArrayList<>();
        final ArrayList<Boss> TuesdayList=new ArrayList<>();
        final ArrayList<Boss> WednesdayList=new ArrayList<>();
        final ArrayList<Boss> ThursdayList=new ArrayList<>();
        final ArrayList<Boss> FridayList=new ArrayList<>();
        final ArrayList<Boss> SaturdayList=new ArrayList<>();
        final ArrayList<Boss> SundayList=new ArrayList<>();

        MondayList.add(new Boss("Kzarka"," 01:00 "));
        TuesdayList.add(new Boss("Kzarka"," 02:00:00 "));
        ThursdayList.add(new Boss("Kzarka"," 01:00:00 "));
        WednesdayList.add(new Boss("Kzarka"," 01:00:00 "));
        FridayList.add(new Boss("KARANDA"," 01:00:00 "));
        SaturdayList.add(new Boss("Kzar","  16:00:00 "));
        SaturdayList.add(new Boss("Kzarka"," 01:00:00 "));
        SaturdayList.add(new Boss("Kzarka"," 03:00:00 "));
        SaturdayList.add(new Boss("KARANDA","  04:00:00 "));
        SaturdayList.add(new Boss("Kzarka","  01:00:00 "));



                bCalendar=Calendar.getInstance();
                hour=bCalendar.get(Calendar.HOUR_OF_DAY);
        dayLongName = bCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);

        switch (dayLongName) {
            case "Monday":
                BossDayList.addAll(MondayList);
                break;

            case "Tuesday":
                BossDayList.addAll(TuesdayList);
                break;

            case "Wednesday":
                BossDayList.addAll(WednesdayList);
                break;

            case "Thursday":
                BossDayList.addAll(ThursdayList);
                break;

            case "Friday":
                BossDayList.addAll(FridayList);
                break;

            case "Saturday":
                BossDayList.addAll(SaturdayList);

            case "Sunday":
                BossDayList.addAll(SundayList);

                break;

            default:
                break;
        }
        for(int i=0;i<BossDayList.size();i++) {

            String n=Time(BossDayList.get(i).getBossTime());//pairnw kathe object ths listas kai to metatrepw apo gmt+2 sto default tou xrhsth,edw xrhsimopoioume mono gia EU
            BossDayList.get(i).setBossTime(n);
         }

        BossAdapter = new BossAdapter(this,BossDayList);
        listView.setAdapter(BossAdapter);



    }
    public  String Time(String s){
        Date k;
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");//tou lew pws na ta emfanizei
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+2"));//tou lew to timezone tou string pou tou dinw

        ParsePosition pos=new ParsePosition(0);//tou lew apo pou na arxisei na diavazei,dld apo thn arxh
        k=sdf.parse(s,pos);//parsarei to string sto Date k


        sdf.setTimeZone(TimeZone.getDefault());//allazoume apo gmt+2 se auto tou xrhsth

        String time=sdf.format(k);//to emfanizw me to format p to dwsa sto sdf
        return  time;
    }
}
