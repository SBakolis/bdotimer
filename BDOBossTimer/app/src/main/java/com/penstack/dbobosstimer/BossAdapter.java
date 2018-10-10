package com.penstack.dbobosstimer;


    import android.content.Context;
    import android.os.CountDownTimer;
    import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
    import android.support.v7.widget.RecyclerView;
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

public class BossAdapter extends RecyclerView.Adapter<BossHolder> {

    private Context Bosscontext;
    private List<Boss> BossDayList = new ArrayList<>();
    private int itemResource;

    public BossAdapter(@NonNull Context context, ArrayList<Boss> list) {

        this.Bosscontext = context;
        this.BossDayList = list;

    }

    @NonNull
    @Override
         public  BossHolder onCreateViewHolder(ViewGroup parent,int viewType){

            View view =LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.bosses, parent, false);
        return new BossHolder(this.Bosscontext, view);
        }


    public void onBindViewHolder(BossHolder holder, int position) {

        // 5. Use position to access the correct Boss object
        Boss currentBoss = this.BossDayList.get(position);

        // 6. Bind the boss object to the holder
        holder.bindBoss(currentBoss);


    }

    public int getItemCount() {

        return this.BossDayList.size();
    }

}


