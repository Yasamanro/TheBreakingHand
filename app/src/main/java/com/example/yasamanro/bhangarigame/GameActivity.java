package com.example.yasamanro.bhangarigame;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class GameActivity extends AppCompatActivity {

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

        //Hardware Parts
        final Button fan = findViewById(R.id.fanButton);

        //Highlights --?
        final Button hammerHighlight = findViewById(R.id.hammerHighlight);
      //  final Button fanHighlight = findViewById(R.id.fanHighlight);

        //Test and score
        Button test = findViewById(R.id.testButton);
        final TextView basketCount = findViewById(R.id.badge_notification_text);
        final TextView score = findViewById(R.id.score);

        //Blink Animation
        final Animation blinkAnimation = new AlphaAnimation(1, 0); // Change alpha from fully invisible to visible
        blinkAnimation.setDuration(500); // duration - half a second
        blinkAnimation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        blinkAnimation.setRepeatCount(3); // Repeat animation infinitely
        blinkAnimation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in

        //Animation (rotate, swirl, move)
        final Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
        final Animation swirlAnimation = AnimationUtils.loadAnimation(this,R.anim.swirl_animation);
//        final Animation moveAnimation = AnimationUtils.loadAnimation(this,R.anim.trainfade);
        final TranslateAnimation animation = new TranslateAnimation(0.0f, 400.0f,
                0.0f, 100f);


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
                    if (fanTapCount < 5) { // Fan not broken yet!
                        Toast.makeText(getApplicationContext(), "Tap " + (5 - fanTapCount) + " more times!", Toast.LENGTH_SHORT).show();
                        fan.clearAnimation();
                        hammerTool.startAnimation(rotateAnimation);
                        fanTapCount++;
                    }
                    else { // You broke the fan!
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
                    b.putString("part","fan");                   // Part
                    intent.putExtras(b);                         //Put part id to next Intent
                    startActivity(intent);
                }
            }
        });


    }
}
