package com.penstack.dbobosstimer;

public class Vell extends Boss {
    String Image;

    static  final String Name="Vell";

    public Vell(int Day,int Hour,int Minute,String Tmz,long TimeLeft){

        super(Day, Hour,Minute,Tmz,Name,TimeLeft);


    }

    public String getBossName(){ return Name; }
}
