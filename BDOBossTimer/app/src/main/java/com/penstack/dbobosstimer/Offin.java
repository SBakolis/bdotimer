package com.penstack.dbobosstimer;

public class Offin extends Boss {
    String Image;

    private static  final String Name="Offin";

    public Offin(int Day,int Hour,int Minute,String Tmz,long TimeLeft){

        super(Day, Hour,Minute,Tmz,Name,TimeLeft);


    }

    public String getBossName(){ return Name; }
}
