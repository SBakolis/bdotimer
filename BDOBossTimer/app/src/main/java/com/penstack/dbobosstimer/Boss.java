package com.penstack.dbobosstimer;

import android.media.Image;

import java.sql.Time;
import java.lang.String;
import java.sql.Timestamp;

public class Boss   {

    String Name;
     //Image;
    String Day;
    String Hour;
    String Tmz;
            public Boss(String Name,String Day,String Hour,String Tmz){
                this.Name=Name;
                //this.Image=Image;
                this.Day=Day;
                this.Hour=Hour;
                this.Tmz=Tmz;

            }
            public String getBossName(){ return Name; }

            public String getBossDay(){return  Day;}

            public void setBossName(String Name){this.Name=Name;}

            public void setBossDay(String Day){ this.Day=Day;}

             public String getBossHour(){return  Hour;}

             public void setBossHour(String Hour){this.Hour=Hour;}
            public String getBossTmz(){return  Tmz;}

            public void setBossTmz(String Tmz){this.Tmz=Tmz;}

    @Override
      public  String toString(){
            return "BossName"+this.Name+",BossDay"+this.Day+",BossHour"+this.Hour+""+this.Tmz;
            }
}