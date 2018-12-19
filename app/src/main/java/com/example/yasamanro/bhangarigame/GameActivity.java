package com.example.yasamanro.bhangarigame;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        // Tools
        final ToggleButton hammer = findViewById(R.id.hammerButton);
        final ToggleButton plier = findViewById(R.id.plierButton);
        final ToggleButton screwdriver = findViewById(R.id.screwdriverButton);
        final ToggleButton hand = findViewById(R.id.handButton);


        //Parts
        final Button fan = findViewById(R.id.fanButton);

        //Logos
        final ToggleButton fanlogoButton = findViewById(R.id.fanLogoButton);

        //Test Button
        Button test = findViewById(R.id.testButton);


        hammer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    plier.setChecked(false);
                    screwdriver.setChecked(false);
                    hand.setChecked(false);
                }
            }
        });

        plier.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hammer.setChecked(false);
                    screwdriver.setChecked(false);
                    hand.setChecked(false);
                }
            }
        });

        screwdriver.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hammer.setChecked(false);
                    plier.setChecked(false);
                    hand.setChecked(false);
                }
            }
        });

        hand.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hammer.setChecked(false);
                    plier.setChecked(false);
                    screwdriver.setChecked(false);
                }
            }
        });

        fan.setOnClickListener(new View.OnClickListener() {

            int count = 0;

            @Override
            public void onClick(View v) {
                if (!hammer.isChecked()){
                    Toast.makeText(getApplicationContext(), "Select The Hammer first!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (count < 5) { // Fan not broken yet!
                        Toast.makeText(getApplicationContext(), "Click " + (5 - count) + " more times!", Toast.LENGTH_SHORT).show();
                        count++;
                    }
                    else { // You broke the fan!
                        fan.setVisibility(View.GONE);
                        fanlogoButton.setVisibility(View.VISIBLE);
                    }
                }
            }
    });

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fanlogoButton.getVisibility() == View.INVISIBLE){
                    Toast.makeText(getApplicationContext(), "You haven't dismantled anything!\nBreak something first!", Toast.LENGTH_SHORT).show();
                }
                else if (!fanlogoButton.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Select the fan first!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(GameActivity.this, TestActivity.class);
                    Bundle b = new Bundle();
                    b.putString("part","fan");                   // Part
                    intent.putExtras(b);                         //Put part id to next Intent
                    startActivity(intent);
                    startActivity(intent);
                }
            }
        });


    }
}
