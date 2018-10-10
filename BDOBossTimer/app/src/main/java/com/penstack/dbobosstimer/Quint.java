package com.penstack.dbobosstimer;

public class Quint extends Boss {

    public static final int bossImage = R.drawable.quint;
    private static  final String Name="Quint/Muraka";

    public Quint(int Day,int Hour,int Minute,String Tmz,long TimeLeft){

        super(Day, Hour,Minute,Tmz,Name,TimeLeft,bossImage);


    }

    public String getBossName(){ return Name; }
    public int getBossImage(){return bossImage;}
}
