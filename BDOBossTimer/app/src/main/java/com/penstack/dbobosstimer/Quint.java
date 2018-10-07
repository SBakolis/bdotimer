package com.penstack.dbobosstimer;

public class Quint extends Boss {
    String Image;

    static  final String Name="Quint/Muraka";

    public Quint(String Day,String Hour,String Tmz){

        super(Day, Hour,Tmz,Name);


    }

    public String getBossName(){ return Name; }
}
