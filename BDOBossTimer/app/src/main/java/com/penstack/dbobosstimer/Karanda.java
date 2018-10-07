package com.penstack.dbobosstimer;

public class Karanda extends Boss {



        String Image;
        static  final String Name="Kara";;
        public Karanda(String Day,String Hour,String Tmz){

            super(Day, Hour,Tmz,Name);


        }

     public String getBossName(){ return Name; }


    }



