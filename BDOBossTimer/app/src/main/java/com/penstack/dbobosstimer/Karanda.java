package com.penstack.dbobosstimer;

public class Karanda extends Boss {



        String Image;
        static  final String Name="Karanda";;
        public Karanda(int Day,int Hour,int Minute,String Tmz,long TimeLeft){

            super(Day, Hour,Minute,Tmz,Name,TimeLeft);


        }

     public String getBossName(){ return Name; }


    }



