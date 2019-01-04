package com.example.yasamanro.bhangarigame;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private BluetoothGattService mBluetoothGattService;
    final String SERVICE_UUID = "4fafc201-1fb5-459e-8fcc-c5c9c331914b";
    final String CHARACTERISTIC_UUID = "beb5483e-36e1-4688-b7f5-ea07361b26a8";
    ArrayList<String> brokenItems;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Tools in toolbar
        final ToggleButton hammer = findViewById(R.id.hammerButton);
        final ToggleButton plier = findViewById(R.id.plierButton);
        final ToggleButton screwdriver = findViewById(R.id.screwdriverButton);
        final ToggleButton hand = findViewById(R.id.handButton);

        // Tools in action
        final Button hammerTool = findViewById(R.id.hammerTool);
        final Button handTool = findViewById(R.id.handTool);

        //Hardware Parts
        final Button fan = findViewById(R.id.fanButton);
        final Button ic  = findViewById(R.id.icButton);
//        final Button fan1 = findViewById(R.id.fanButton1);
//        final Button fan2 = findViewById(R.id.fanButton2);
//        final Button fan3 = findViewById(R.id.fanButton3);
//        final Button fan4 = findViewById(R.id.fanButton4);
//        final Button fan5 = findViewById(R.id.fanButton5);

        // Basket
        final Button basket = findViewById(R.id.basket);

        //Highlights --?
        final Button hammerHighlight = findViewById(R.id.hammerHighlight);
      //  final Button fanHighlight = findViewById(R.id.fanHighlight);

        //Test and score
        Button test = findViewById(R.id.testButton);
        final TextView basketCount = findViewById(R.id.badge_notification_text);
        final TextView score = findViewById(R.id.score);

        //Board Layout
        RelativeLayout boardlayout = findViewById(R.id.boardcanvas);

        //Blink Animation
        final Animation blinkAnimation = new AlphaAnimation(1, 0); // Change alpha from fully invisible to visible
        blinkAnimation.setDuration(500); // duration - half a second
        blinkAnimation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        blinkAnimation.setRepeatCount(3); // Repeat animation infinitely
        blinkAnimation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in

        //Animation (rotate, swirl, move)
        final Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
        final Animation swirlAnimation = AnimationUtils.loadAnimation(this,R.anim.swirl_animation);
        final TranslateAnimation animation = new TranslateAnimation(0.0f, 400.0f,
                0.0f, 100f);
        final TranslateAnimation moveDownAnimation = new TranslateAnimation(0.0f, 0.0f,
                -400f, 0.0f);
        final TranslateAnimation moveUpAnimation = new TranslateAnimation(0.0f, 0.0f,
                0.0f, -400f);
        moveDownAnimation.setDuration(1000);
        moveUpAnimation.setDuration(1000);

        // Sounds
        final MediaPlayer hitMetalSound = MediaPlayer.create(this,R.raw.hitmetal);

        // Broken Items
        brokenItems = new ArrayList<>();

        // Get BluetoothGattService data from BLEServiceContainer and find the service we want!
        //mBluetoothGattService = findService(BLEServiceContainer.getInstance().getBleService().getSupportedGattServices(),SERVICE_UUID);


        ic.setOnTouchListener(new View.OnTouchListener() {
            float y1 = 0, y2 = 0;
            @Override
            public boolean onTouch(View view, MotionEvent event) {                          //gesture detector to detect swipe.
                final int MIN_DISTANCE = 50;
                if(!hand.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Select The Hand first!", Toast.LENGTH_SHORT).show();
                    hand.startAnimation(blinkAnimation);
                    view.clearAnimation();
                }
                else {
                    switch(event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            y1 = event.getY();
                            Log.d("Y1: ", Float.toString(y1));
                            break;
                        case MotionEvent.ACTION_UP:
                            y2 = event.getY();
                            Log.d("Y2: ", Float.toString(y2));
                            float deltaX = y2 - y1;
                            if (Math.abs(deltaX) > MIN_DISTANCE) {
                                Log.d("DELTAX", Float.toString(deltaX));
                                if (deltaX>0) {                             // Up to Bottom Swipe
                                    handTool.startAnimation(moveDownAnimation);
                                }
                                else{                                       // Bottom to Up Swipe
                                    handTool.startAnimation(moveUpAnimation);
                                }
                            }
                            break;
                    }
                }
                return true;                                //always return true to consume event
            }
        });

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

        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
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
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        fan.setOnClickListener(new View.OnClickListener() {
            int fanTapCount = 0;

            @Override
            public void onClick(View v) {
                if (!hammer.isChecked()){
                    Toast.makeText(getApplicationContext(), "Select The Hammer first!", Toast.LENGTH_SHORT).show();
                    hammer.startAnimation(blinkAnimation);
                    v.clearAnimation();
                   // hammerHighlight.setVisibility(View.INVISIBLE);
                }
                else {
                   // writeOnMicro(mBluetoothGattService, CHARACTERISTIC_UUID, 0);
                    Log.d("wrote on micro", "0");

//                    switch (fanTapCount){
//                        case 0:
//                            hitMetalSound.start();
//                            Toast.makeText(getApplicationContext(), "Tap " + (5 - fanTapCount) + " more times!", Toast.LENGTH_SHORT).show();
//                            fan.clearAnimation();
//                            hammerTool.startAnimation(rotateAnimation);
//                            fanTapCount++;
//                            break;
//                        case 1:
//                            fan.setVisibility(View.GONE);
//                            fan1.setVisibility(View.VISIBLE);
//                            hitMetalSound.start();
//                            Toast.makeText(getApplicationContext(), "Tap " + (5 - fanTapCount) + " more times!", Toast.LENGTH_SHORT).show();
//                            fan1.clearAnimation();
//                            hammerTool.startAnimation(rotateAnimation);
//                            fanTapCount++;
//                            break;
//
//                        case 2:
//                            fan1.setVisibility(View.GONE);
//                            fan2.setVisibility(View.VISIBLE);
//                            hitMetalSound.start();
//                            Toast.makeText(getApplicationContext(), "Tap " + (5 - fanTapCount) + " more times!", Toast.LENGTH_SHORT).show();
//                            fan2.clearAnimation();
//                            hammerTool.startAnimation(rotateAnimation);
//                            fanTapCount++;
//                            break;
//
//                        case 3:
//                            fan2.setVisibility(View.GONE);
//                            fan3.setVisibility(View.VISIBLE);
//                            hitMetalSound.start();
//                            Toast.makeText(getApplicationContext(), "Tap " + (5 - fanTapCount) + " more times!", Toast.LENGTH_SHORT).show();
//                            fan3.clearAnimation();
//                            hammerTool.startAnimation(rotateAnimation);
//                            fanTapCount++;
//                            break;
//
//                        case 4:
//                            fan3.setVisibility(View.GONE);
//                            fan4.setVisibility(View.VISIBLE);
//                            hitMetalSound.start();
//                            Toast.makeText(getApplicationContext(), "Tap " + (5 - fanTapCount) + " more times!", Toast.LENGTH_SHORT).show();
//                            fan4.clearAnimation();
//                            hammerTool.startAnimation(rotateAnimation);
//                            fanTapCount++;
//                            break;
//
//                        case 5:
//                            fan4.setVisibility(View.GONE);
//                            fan5.setVisibility(View.VISIBLE);
//                            hitMetalSound.start();
//                            Toast.makeText(getApplicationContext(), "Tap " + (5 - fanTapCount) + " more times!", Toast.LENGTH_SHORT).show();
//                            fan5.clearAnimation();
//                            hammerTool.startAnimation(rotateAnimation);
//                            fanTapCount++;
//                            break;
//
//                        default:
//                            hitMetalSound.start();
//                            hammerTool.startAnimation(rotateAnimation);
//                            //  fan.setVisibility(View.GONE);
//                            //fanHighlight.setVisibility(View.GONE);
//                            int basketCountTemp = Integer.parseInt(basketCount.getText().toString())+1;
//                            basketCount.setText(Integer.toString(basketCountTemp));
//                            int scoreCountTemp = Integer.parseInt(score.getText().toString())+1;
//                            score.setText(Integer.toString(scoreCountTemp));
//
//                            AnimationSet s = new AnimationSet(false);
//
//                            s.addAnimation(swirlAnimation);
//                            s.addAnimation(animation);
//                            fan5.startAnimation(s);
//
//                            brokenItems.add("Fan");
//                            fanTapCount++;
//                            break;
//                    }


                    if (fanTapCount < 5) { // Fan not broken yet!
                        hitMetalSound.start();
                        Toast.makeText(getApplicationContext(), "Tap " + (5 - fanTapCount) + " more times!", Toast.LENGTH_SHORT).show();
                        fan.clearAnimation();
                        hammerTool.startAnimation(rotateAnimation);
                        fanTapCount++;
                    }
                    else if (fanTapCount == 5){ // You broke the fan!
                        hitMetalSound.start();
                        hammerTool.startAnimation(rotateAnimation);
                        //  fan.setVisibility(View.GONE);
                        //fanHighlight.setVisibility(View.GONE);
                        int basketCountTemp = Integer.parseInt(basketCount.getText().toString())+1;
                        basketCount.setText(Integer.toString(basketCountTemp));
                        int scoreCountTemp = Integer.parseInt(score.getText().toString())+1;
                        score.setText(Integer.toString(scoreCountTemp));

                        AnimationSet s = new AnimationSet(false);

                        s.addAnimation(swirlAnimation);
                        s.addAnimation(animation);
                        fan.startAnimation(s);
                        fan.setVisibility(View.GONE);
                        hammerTool.setVisibility(View.GONE);

                        brokenItems.add("Fan");
                        fanTapCount++;
                    }
                }
            }
    });

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(basketCount.getText().toString()) <= 0){
                    Toast.makeText(getApplicationContext(), "You haven't dismantled anything!\nBreak something first!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(GameActivity.this, TestActivity.class);
                    Bundle b = new Bundle();
                    b.putStringArrayList("brokens",brokenItems);
                    b.putInt("score", Integer.parseInt(score.getText().toString()));
                    intent.putExtras(b);                         // Put broken parts Arraylist to next Intent
                    startActivity(intent);
                }
            }
        });
    }

    public BluetoothGattService findService(List<BluetoothGattService> services, String serviceUUID){
        String uuid;
        for (BluetoothGattService gattService : services) {
            uuid = gattService.getUuid().toString();
            if (uuid.equals(serviceUUID)) {
                return gattService;
            }
        }
        return null;
    }

    public void writeOnMicro (BluetoothGattService gattService, String characteristicUUID, int message){
        List<BluetoothGattCharacteristic> mGattCharacteristics = gattService.getCharacteristics();
        BluetoothGattCharacteristic charac = null;

        if (mGattCharacteristics != null) {
            for (BluetoothGattCharacteristic characteristic: mGattCharacteristics){
                if (characteristic.getUuid().toString().equals(characteristicUUID)){
                    charac = characteristic;
                }
            }

            if (charac != null) {
                final int charaProp = charac.getProperties();
                if ((charaProp | BluetoothGattCharacteristic.PERMISSION_WRITE) > 0) {               // Permission to write!
                    byte[] writeValue = {(byte) message,(byte) message,(byte) message,(byte) message};
                    charac.setValue(writeValue);
                    String s1 = Arrays.toString(writeValue);
                    Log.d("writeonmicro CHARACTERSITCI YASAMAN JAN", s1);
                    BLEServiceContainer.getInstance().getBleService().writeCharacteristic(charac);
                }
            }
        }
    }
}
