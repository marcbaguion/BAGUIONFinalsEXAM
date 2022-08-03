package com.example.midterms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import static com.example.midterms.MainActivity.PREF_NIGHT;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Switch;
import android.widget.TextView;

public class PipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pipe);
        btnConfirmListenerMethod();

        // TODO Milestone A: get SharedPreference on night mode and change false to variable
        SharedPreferences preferences = getSharedPreferences("vincepref", MODE_PRIVATE);
        boolean night = preferences.getBoolean(PREF_NIGHT, false);
        initializeNightMode(night);
    }

    // TODO Milestone A: implement night mode
    private void initializeNightMode(boolean night) {
        Switch swNight = findViewById(R.id.swNight);
        int[] textviews = {R.id.swNight, R.id.tvTitle, R.id.rbRegular, R.id.rbBasic, R.id.rbPremium, R.id.tvLblPackage, R.id.tvLblPipe, R.id.tvPipe, R.id.tvLblPrev, R.id.tvLblNew, R.id.tvLblHistory, R.id.tvLblBill, R.id.etResult, R.id.etPrev, R.id.etNew};
        ConstraintLayout clMain = findViewById(R.id.clMain);
        if (night) {
            clMain.setBackgroundColor(Color.BLACK);
            swNight.setChecked(true);
            for (int res : textviews) {
                TextView view1 = findViewById(res);
                view1.setTextColor(Color.WHITE);
            }
        } else {
            swNight.setChecked(false);
            clMain.setBackgroundColor(Color.WHITE);
            for (int res : textviews) {
                TextView view1 = findViewById(res);
                view1.setTextColor(Color.BLACK);
            }
        }
    }

    private void btnConfirmListenerMethod() {
        Button btnConfirm = findViewById(R.id.btnConfirm);
        Intent outIntent = new Intent();

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Milestone B-1: use the outIntent and store the chosen brand and the chosen diameter as extras
                //  and after which, use setResult with ACTION_OK and the intent as arguments
                setResult(RESULT_OK, outIntent);
                finish();
            }
        });

    }
}