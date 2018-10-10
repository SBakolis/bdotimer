package com.penstack.dbobosstimer;

public class Karanda extends Boss {



        private static final int bossImage = R.drawable.karanda ;
        private static  final String Name="Karanda";
        public Karanda(int Day,int Hour,int Minute,String Tmz,long TimeLeft){

            super(Day, Hour,Minute,Tmz,Name,TimeLeft,bossImage);


        }

     public String getBossName(){ return Name; }
     public int getBossImage(){return bossImage;}


    }



