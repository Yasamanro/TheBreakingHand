package com.example.yasamanro.bhangarigame;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button playButton = findViewById(R.id.playButton);
        Button howButton = findViewById(R.id.howButton);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        howButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                customDialog("How To Play", "1. Choose a Tool!\n2. Extract an electronic part using your tool\n3. Test your part!\n4. Decide whether to reuse or recycle!");
            }
        });
    }


    public void customDialog(String title, String message){
        final android.support.v7.app.AlertDialog.Builder builderSingle = new android.support.v7.app.AlertDialog.Builder(this, R.style.MyDialogTheme);
        builderSingle.setIcon(R.mipmap.info);
        builderSingle.setTitle(title);
        builderSingle.setMessage(message);

        builderSingle.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        toastMessage("Now you know how to play!");
                    }
                });

        builderSingle.show();
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
