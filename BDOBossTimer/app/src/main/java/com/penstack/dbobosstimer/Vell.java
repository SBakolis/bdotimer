package com.penstack.dbobosstimer;

public class Vell extends Boss {

    private static final int bossImage = R.drawable.vell;
    private static  final String Name="Vell";

    public Vell(int Day,int Hour,int Minute,String Tmz,long TimeLeft){

        super(Day, Hour,Minute,Tmz,Name,TimeLeft,bossImage);


    }

    public String getBossName(){ return Name; }
    public int getBossImage(){return bossImage;}
}
