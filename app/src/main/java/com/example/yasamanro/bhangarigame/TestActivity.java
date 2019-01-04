package com.example.yasamanro.bhangarigame;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    ArrayList<String> brokenItems;
    private ShakeListener mShaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        final Button shakeButton = findViewById(R.id.shakeButton);
        Button touchButton = findViewById(R.id.touchButton);
        Button plugButton = findViewById(R.id.plugButton);
        Button basketButton = findViewById(R.id.basket);
        Button decideButton = findViewById(R.id.decideButton);

        ImageView currentObject = null;
        final MediaPlayer shakeSound = MediaPlayer.create(this,R.raw.shake);

        final TextView basketCount = findViewById(R.id.badge_notification_text);
        final TextView score = findViewById(R.id.score);

        //Make the appropriate part visible on the bench
        Bundle b = getIntent().getExtras();
        if(b != null){
            brokenItems = b.getStringArrayList("brokens");
            basketCount.setText(Integer.toString(brokenItems.size()));
            score.setText(Integer.toString(b.getInt("score")));
        }
        else {
            Toast.makeText(getApplicationContext(), "No content from previous activity!", Toast.LENGTH_SHORT).show();
        }

        final Vibrator vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        Toast.makeText(getApplicationContext(), "Shake it!", Toast.LENGTH_SHORT).show();

        mShaker = new ShakeListener(this);
        mShaker.setOnShakeListener(new ShakeListener.OnShakeListener () {
            public void onShake() {
                vibe.vibrate(500);
                Toast.makeText(getApplicationContext(), "Shooken!", Toast.LENGTH_SHORT).show();
                shakeSound.start();
            }
        });

        shakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Shake it!", Toast.LENGTH_SHORT).show();
                }
            });

        basketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(TestActivity.this);
                builder.setTitle("Basket Content");

                // Make an array of broken items to show
                String[] arr = brokenItems.toArray(new String[brokenItems.size()]);
                builder.setItems(arr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: // horse
                            case 1: // cow
                            case 2: // camel
                            case 3: // sheep
                            case 4: // goat
                        }
                    }
                });
                // create and show the alert dialog
                android.app.AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        decideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity.this, DecideActivity.class);
                Bundle b = new Bundle();
                b.putString("part","fan");                   // Part
                intent.putExtras(b);                         //Put part id to next Intent
                startActivity(intent);
            }
        });
    }
}
