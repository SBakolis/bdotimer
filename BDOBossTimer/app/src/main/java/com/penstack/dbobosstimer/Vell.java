package com.penstack.dbobosstimer;

public class Vell extends Boss {
    String Image;

    static  final String Name="Vell";

    public Vell(String Day,String Hour,String Tmz){

        super(Day, Hour,Tmz,Name);


    }

    public String getBossName(){ return Name; }
}
