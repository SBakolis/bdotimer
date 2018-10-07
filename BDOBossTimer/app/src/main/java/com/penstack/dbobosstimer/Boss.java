package com.penstack.dbobosstimer;

import java.sql.Time;
import java.lang.String;
import java.sql.Timestamp;

//import static com.penstack.dbobosstimer.Karanda.Name;

public class Boss   {


    String Name;
    String Day;
    String Hour;
    String Tmz;
    public Boss(String Day,String Hour,String Tmz,String Name){

        //this.Image=Image;
        this.Day=Day;
        this.Hour=Hour;
        this.Tmz=Tmz;
        this.Name=Name;
    }


    public String getBossDay(){return  Day;}



    public void setBossDay(String Day){ this.Day=Day;}

    public String getBossHour(){return  Hour;}

    public void setBossHour(String Hour){this.Hour=Hour;}
    public String getBossTmz(){return  Tmz;}

    public void setBossTmz(String Tmz){this.Tmz=Tmz;}
     public String getBossName(){ return Name; }
    public void setName(String Name){this.Name=Name;}
    @Override
    public  String toString(){
        return "BossDay"+this.Day+",BossHour"+this.Hour+""+this.Tmz;
    }
}