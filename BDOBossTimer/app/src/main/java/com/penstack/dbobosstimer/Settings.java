package com.penstack.dbobosstimer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.widget.RadioButton;
import android.view.View;

public class Settings extends AppCompatActivity {



   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rbEU:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.rbNA:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }
}



