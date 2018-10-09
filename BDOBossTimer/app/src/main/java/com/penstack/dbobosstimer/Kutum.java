package com.penstack.dbobosstimer;

public class Kutum extends  Boss {

    String Image;
    static  final String Name="Kutum";
    public Kutum(int Day,int Hour,int Minute,String Tmz,long TimeLeft){

        super(Day,Hour,Minute,Tmz,Name,TimeLeft);


    }

    public String getBossName(){ return Name; }
}
