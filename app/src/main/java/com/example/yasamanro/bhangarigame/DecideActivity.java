package com.example.yasamanro.bhangarigame;

import android.content.ClipData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

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


        // Make the appropriate part visible on the bench
        ImageView currentObject = null;
        Bundle b = getIntent().getExtras();
        String part = "";
        if(b != null){
            brokenItems = b.getStringArrayList("brokens");
        }
            part = b.getString("part");
        if (part.equals("fan")){
            ImageView obj = findViewById(R.id.fanObject);
            fan.setVisibility(View.VISIBLE);
            currentObject = obj;
        }

        fan.setOnTouchListener(new ChoiceTouchListener());
        //metalBasket.setOnTouchListener(new ChoiceTouchListener());
        //fan.setOnDragListener(new ChoiceDragListener());
        metalBasket.setOnDragListener(new ChoiceDragListener());
        glassBasket.setOnDragListener(new ChoiceDragListener());
        paperBasket.setOnDragListener(new ChoiceDragListener());
        plasticBasket.setOnDragListener(new ChoiceDragListener());
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
