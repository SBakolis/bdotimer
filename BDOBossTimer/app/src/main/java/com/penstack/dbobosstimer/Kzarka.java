package com.penstack.dbobosstimer;

public class Kzarka extends  Boss {

    private static final int bossImage = R.drawable.kzarka;
    private static  final String Name="Kzarka";;
    public Kzarka(int Day,int Hour,int Minute,String Tmz,long TimeLeft){

        super(Day, Hour,Minute,Tmz,Name,TimeLeft,bossImage);


    }

     public String getBossName(){ return Name; }

     public int getBossImage(){return bossImage;}
}
