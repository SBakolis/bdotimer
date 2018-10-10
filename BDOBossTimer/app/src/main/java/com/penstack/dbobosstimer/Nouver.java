package com.penstack.dbobosstimer;

public class Nouver extends  Boss {

    private static final int bossImage = R.drawable.nouver;
    private static  final String Name="Nouver";
    public Nouver(int Day,int Hour,int Minute,String Tmz,long TimeLeft){

        super(Day, Hour,Minute,Tmz,Name,TimeLeft,bossImage);


    }

    public String getBossName(){ return Name; }
    public int getBossImage(){return bossImage;}
}
