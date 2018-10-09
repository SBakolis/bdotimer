package com.penstack.dbobosstimer;


    import android.content.Context;
    import android.os.CountDownTimer;
    import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
    import android.widget.ImageView;
    import android.widget.TextView;

    import java.text.ParsePosition;
    import java.text.SimpleDateFormat;
    import java.util.ArrayList;
    import java.util.Date;
    import java.util.List;
    import java.util.TimeZone;

public class BossAdapter extends ArrayAdapter<Boss>
    {
        private Context Bosscontext;
        private List<Boss> BossDayList = new ArrayList<>();

        public BossAdapter(@NonNull Context context, ArrayList<Boss> list)
        {
            super(context, 0, list);
            Bosscontext = context;
            BossDayList = list;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
        {
            View listItem = convertView;
            if(listItem == null)
                listItem = LayoutInflater.from( Bosscontext).inflate(R.layout.bosses,parent,false);

            Boss currentBoss =BossDayList.get(position);

            TextView BossName = (TextView) listItem.findViewById(R.id.BossName);
            BossName.setText(currentBoss.getBossName());

            final TextView BossTime = (TextView) listItem.findViewById(R.id.timeLeft);

            new CountDownTimer(currentBoss.TimeLeft, 1000) {//edw kanw to countdown timer,otan teleiwnei vgainei done

                public void onTick(long millisUntilFinished) {

                    BossTime.setText(""+FinalTime(millisUntilFinished));

                }

                public void onFinish() {
                        BossTime.setText("done");

                }
            }.start();

            ImageView BossImage = (ImageView) listItem.findViewById(R.id.bossImage);


            return listItem;
        }

        public String FinalTime(long m){

          return(new SimpleDateFormat("HH:mm:ss").format(new Date(m)));//to kanw ths morfhs wres:lepta:secs
        }
    }

