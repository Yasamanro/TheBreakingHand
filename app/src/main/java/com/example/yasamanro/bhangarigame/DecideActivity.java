package com.example.yasamanro.bhangarigame;

import android.content.ClipData;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class DecideActivity extends AppCompatActivity {

    ArrayList<String> brokenItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decide);

        final ImageView fan = findViewById(R.id.fanObject);
        final ImageView metalBasket = findViewById(R.id.metalBasket);
        final ImageView glassBasket = findViewById(R.id.glassBasket);
        final ImageView paperBasket = findViewById(R.id.paperBasket);
        final ImageView plasticBasket = findViewById(R.id.plasticBasket);

        Button basketButton = findViewById(R.id.basket);
        Button decideButton = findViewById(R.id.decideButton);
        final TextView basketCount = findViewById(R.id.badge_notification_text);
        final TextView score = findViewById(R.id.score);

        // Make the appropriate part visible on the bench
        Bundle b = getIntent().getExtras();
        if(b != null){
            brokenItems = b.getStringArrayList("brokens");
            basketCount.setText(Integer.toString(brokenItems.size()));
            score.setText(Integer.toString(b.getInt("score")));
        }
        else {
            Toast.makeText(getApplicationContext(), "No content from previous activity!", Toast.LENGTH_SHORT).show();
        }

        fan.setOnTouchListener(new ChoiceTouchListener());
        //metalBasket.setOnTouchListener(new ChoiceTouchListener());
        //fan.setOnDragListener(new ChoiceDragListener());
        metalBasket.setOnDragListener(new ChoiceDragListener());
        glassBasket.setOnDragListener(new ChoiceDragListener());
        paperBasket.setOnDragListener(new ChoiceDragListener());
        plasticBasket.setOnDragListener(new ChoiceDragListener());

        basketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(DecideActivity.this);
                builder.setTitle("Basket Content");

                // Make an array of broken items to show
                String[] arr = brokenItems.toArray(new String[brokenItems.size()]);
                builder.setItems(arr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: fan.setVisibility(View.VISIBLE);
                            case 1: fan.setVisibility(View.VISIBLE);
                            case 2:
                            case 3:
                            case 4:
                        }
                    }
                });
                // create and show the alert dialog
                android.app.AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private final class ChoiceTouchListener implements View.OnTouchListener{
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN){
                ClipData data = ClipData.newPlainText("","");
                View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(v);
                v.startDragAndDrop(data,dragShadowBuilder,v,0);
                return true;
            }else {
                return false;
            }
        }
    }

    private final class ChoiceDragListener implements View.OnDragListener{
        @Override
        public boolean onDrag(View v, DragEvent event) {

            switch (event.getAction()){
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    ImageView view = (ImageView)event.getLocalState();
                    view.setVisibility(View.INVISIBLE);
                    break;
            }
            return true;
        }
    }




}
