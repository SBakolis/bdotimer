package com.penstack.dbobosstimer;

public class Quint extends Boss {
    String Image;

    static  final String Name="Quint/Muraka";

    public Quint(int Day,int Hour,int Minute,String Tmz,long TimeLeft){

        super(Day, Hour,Minute,Tmz,Name,TimeLeft);


    }

    public String getBossName(){ return Name; }
}
