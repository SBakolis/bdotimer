package com.penstack.dbobosstimer;

import android.os.Parcelable;

import java.io.Serializable;
import java.sql.Time;
import java.lang.String;
import java.sql.Timestamp;

//import static com.penstack.dbobosstimer.Karanda.Name;

public class Boss  implements Serializable {

    private int bossImage;
    private String Name;
    private int Day;
    private int Hour;
    private int Minute;
    String Tmz;// de kserw an xreiazetai en telei
    long TimeLeft;

    public Boss(int Day,int Hour,int Minute,String Tmz,String Name,long TimeLeft, int bossImage){

        this.bossImage = bossImage;
        this.Day = Day;
        this.Hour = Hour;
        this.Tmz = Tmz;
        this.Minute = Minute;
        this.Name = Name;
        this.TimeLeft = TimeLeft;
    }


    public int getBossDay(){return  Day;}

    public void setBossDay(int Day){ this.Day=Day;}

    public int getBossHour(){return  Hour;}

    public void setBossHour(int Hour){this.Hour=Hour;}

    public int getBossMin(){return  Minute;}

    public void setBossMin(int Minute){this.Minute=Minute;}

    public String getBossTmz(){return  Tmz;}

    public void setBossTmz(String Tmz){this.Tmz=Tmz;}

    public String getBossName(){ return Name; }

    public void setName(String Name){this.Name=Name;}

    public long getTimeLeft() {
        return TimeLeft;
    }

    public void setTimeLeft(long timeLeft) {
        this.TimeLeft = timeLeft;
    }

    public int getBossImage(){return bossImage;}

    @Override
    public  String toString(){
        return "BossDay"+this.Day+",BossHour"+this.Hour+""+this.Tmz;
    }
}