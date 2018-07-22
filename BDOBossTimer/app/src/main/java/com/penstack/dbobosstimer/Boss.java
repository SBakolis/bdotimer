package com.penstack.dbobosstimer;

import java.sql.Time;
import java.lang.String;
import java.sql.Timestamp;

public class Boss   {

    String BossName;
    String BossTime;
            public Boss(String BossName,String BossTime){
                this.BossName=BossName;
                this.BossTime=BossTime;
            }
            public String getBossName(){ return BossName; }

            public String getBossTime(){return  BossTime;}

            public void setBossName(String BossDay){this.BossName=BossName;}

            public void setBossTime(String BossTime){ this.BossTime=BossTime;}

    @Override
      public  String toString(){
            return "BossDay"+this.BossName+",BossTime"+this.BossTime+"";
            }
}