package com.penstack.dbobosstimer;

public class Kutum extends  Boss {

    private static int  bossImage = R.drawable.kutum ;
    private static  final String Name="Kutum";
    public Kutum(int Day,int Hour,int Minute,String Tmz,long TimeLeft){

        super(Day,Hour,Minute,Tmz,Name,TimeLeft,bossImage);


    }

    public String getBossName(){ return Name;}
    public int getBossImage(){return bossImage;}
}
