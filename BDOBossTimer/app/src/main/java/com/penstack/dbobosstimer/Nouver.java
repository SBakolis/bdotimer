package com.penstack.dbobosstimer;

public class Nouver extends  Boss {

    String Image;
    static  final String Name="Nouver";
    public Nouver(int Day,int Hour,int Minute,String Tmz,long TimeLeft){

        super(Day, Hour,Minute,Tmz,Name,TimeLeft);


    }

    public String getBossName(){ return Name; }
}
