package com.penstack.dbobosstimer;

public class Kzarka extends  Boss {

    String Image;
    static  final String Name="Kzarka";;
    public Kzarka(String Day,String Hour,String Tmz){

        super(Day, Hour,Tmz,Name);


    }

     public String getBossName(){ return Name; }
}
