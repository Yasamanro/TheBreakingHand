package com.example.yasamanro.bhangarigame;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class TestActivity extends AppCompatActivity {

    private ShakeListener mShaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        final Button shakeButton = findViewById(R.id.shakeButton);
        Button touchButton = findViewById(R.id.touchButton);
        Button plugButton = findViewById(R.id.plugButton);
        Button decideButton = findViewById(R.id.decideButton);

        ImageView currentObject = null;
        final MediaPlayer shakeSound = MediaPlayer.create(this,R.raw.shake);

        //Make the appropriate part visible on the bench
        Bundle b = getIntent().getExtras();
        String part = "";
        if(b != null)
            part = b.getString("part");
        if (part.equals("fan")){
            ImageView fan = findViewById(R.id.fan);
            fan.setVisibility(View.VISIBLE);
            currentObject = fan;
        }

        final Vibrator vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        Toast.makeText(getApplicationContext(), "Shake it!", Toast.LENGTH_SHORT).show();

        mShaker = new ShakeListener(this);
        mShaker.setOnShakeListener(new ShakeListener.OnShakeListener () {
            public void onShake()
            {
                vibe.vibrate(500);
                Toast.makeText(getApplicationContext(), "Shooken!", Toast.LENGTH_SHORT).show();
                shakeSound.start();
                // Dialog
//                new AlertDialog.Builder(TestActivity.this)
//                        .setPositiveButton(android.R.string.ok, null)
//                        .setMessage("Shooken!")
//                        .show();
            }
        });

        shakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Shake it!", Toast.LENGTH_SHORT).show();
                }
            });

        decideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity.this, DecideActivity.class);
                startActivity(intent);
            }
        });
    }
}
