package com.penstack.dbobosstimer;

public class Offin extends Boss {

    private static final int bossImage = R.drawable.offin;
    private static  final String Name="Offin";

    public Offin(int Day,int Hour,int Minute,String Tmz,long TimeLeft){

        super(Day, Hour,Minute,Tmz,Name,TimeLeft,bossImage);


    }

    public String getBossName(){ return Name; }
    public int getBossImage(){return bossImage;}
}
