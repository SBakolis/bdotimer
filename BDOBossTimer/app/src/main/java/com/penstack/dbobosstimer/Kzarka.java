package com.penstack.dbobosstimer;

public class Kzarka extends  Boss {

    String Image;
    static  final String Name="Kzarka";;
    public Kzarka(int Day,int Hour,int Minute,String Tmz,long TimeLeft){

        super(Day, Hour,Minute,Tmz,Name,TimeLeft);


    }

     public String getBossName(){ return Name; }
}
