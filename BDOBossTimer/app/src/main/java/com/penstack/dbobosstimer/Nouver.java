package com.penstack.dbobosstimer;

public class Nouver extends  Boss {

    String Image;
    static  final String Name="Nouver";
    public Nouver(String Day,String Hour,String Tmz){

        super(Day, Hour,Tmz,Name);


    }

    public String getBossName(){ return Name; }
}
