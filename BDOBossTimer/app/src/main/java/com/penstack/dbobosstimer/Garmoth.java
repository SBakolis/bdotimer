package com.penstack.dbobosstimer;

public class Garmoth extends Boss {

    private static final int bossImage = R.drawable.garmoth ;
    private static  final String Name="Garmoth";
    public Garmoth(int Day,int Hour,int Minute,String Tmz,long TimeLeft){

        super(Day, Hour,Minute,Tmz,Name,TimeLeft,bossImage);


    }

    public String getBossName(){ return Name; }
    public int getBossImage(){return bossImage;}
}
