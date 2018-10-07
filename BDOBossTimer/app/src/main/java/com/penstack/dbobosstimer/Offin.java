package com.penstack.dbobosstimer;

public class Offin extends Boss {
    String Image;

    static  final String Name="Offin";

    public Offin(String Day,String Hour,String Tmz){

        super(Day, Hour,Tmz,Name);


    }

    public String getBossName(){ return Name; }
}
