package com.penstack.dbobosstimer;

public class Kutum extends  Boss {

    String Image;
    static  final String Name="Kutum";
    public Kutum(String Day,String Hour,String Tmz){

        super(Day, Hour,Tmz,Name);


    }

    public String getBossName(){ return Name; }
}
