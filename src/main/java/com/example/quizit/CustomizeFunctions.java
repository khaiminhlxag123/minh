package com.example.quizit;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;


public class CustomizeFunctions extends Setting {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_functions);



        Button btn1 = findViewById(R.id.btn_fc_setting);

        btn1.setOnClickListener(new View.OnClickListener() {

           private boolean isFirstClick = true;
            @Override
           public void onClick(View view) {

              Intent intent = new Intent(CustomizeFunctions.this, Setting.class);
               if(isFirstClick){
                   startActivity(intent);
                   isFirstClick = false;
               }else {

                    sw_background_music.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            SettingItem.musicBackground = sw_background_music.isChecked();

                            Intent intent = new Intent(CustomizeFunctions.this, Setting.class);
                            boolean sw_backGround_music = sw_background_music.isChecked();
                            intent.putExtra("backgroundMusicEnabled",sw_backGround_music );
                      }
                    });

                    startActivity(intent);
                }


            }
        });


        Button btn2 = findViewById(R.id.btn_fc_quiz);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomizeFunctions.this, SetUpQuiz.class);
                startActivity(intent);
            }
        });

        //Button btn3 = findViewById(R.id.btn_fc_build);
       //btn3.setOnClickListener(new View.OnClickListener() {
            //@Override
           //public void onClick(View view) {
                       //Intent intent = new Intent(CustomizeFunctions.this, BuildQuestion.class);
               //startActivity(intent);
           }
       }//);


    //}
//}