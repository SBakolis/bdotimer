package com.penstack.dbobosstimer;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BossHolder extends RecyclerView.ViewHolder {

    //private final ImageView BossImage;
    private final TextView BossName;
    private  final TextView BossTime;
    private final ImageView BossImage;
    private  Boss boss;
    private Context context;
    private CountDownTimer timer;
        public BossHolder(Context context,View view){

            super(view);
            this.context=context;
            this.BossName=(TextView) view.findViewById(R.id.BossName);
            this.BossTime=(TextView) view.findViewById(R.id.timeLeft);
            this.BossImage = (ImageView) view.findViewById(R.id.bossImage);
        }
        public void bindBoss(Boss boss){
            this.boss=boss;
            this.BossName.setText(boss.getBossName());
            //this.BossTime.setText(Long.toString(boss.getTimeLeft()));
            if(timer!=null){
                timer.cancel();
            }
             timer= new CountDownTimer(boss.TimeLeft, 1000) {//edw kanw to countdown timer,otan teleiwnei vgainei done

                public void onTick(long millisUntilFinished) {

                    BossTime.setText("" + FinalTime(millisUntilFinished));

                }

                public void onFinish() {
                    BossTime.setText("done");

                }
            }.start();

            this.BossImage.setImageResource(boss.getBossImage());

        }
    public String FinalTime(long m) { //methodos gia na metatrepsw ta milliseconds se days:hours:mins:secs

            long day,hour,min,secs;
            String last;
            Date change;
            SimpleDateFormat f;
     secs = m / 1000;
       min= secs / 60;
        hour = min/ 60;
       day =hour / 24;
        if(day>0){
             last = day + ":" + hour % 24 + ":" + min % 60 + ":" + secs % 60;//f.format(new Date(m));//to kanw ths morfhs wres:lepta:secs
            ParsePosition pos2 = new ParsePosition(0);
             f=new SimpleDateFormat("D:HH:mm:ss");
            change=f.parse(last,pos2);
        }
        else {
            last = +hour % 24 + ":" + min % 60 + ":" + secs % 60;

            ParsePosition pos2 = new ParsePosition(0);
             f = new SimpleDateFormat("HH:mm:ss");
            change = f.parse(last, pos2);
        }
        return  f.format(change);
    }
}
