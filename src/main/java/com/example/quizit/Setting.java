package com.example.quizit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class Setting extends AppCompatActivity {


    TextView textView_timer;

    int counter = 0;

    Switch sw_sound_effect;

    Switch sw_background_music;

    Switch sw_countDown_timer;

    EditText et_number_of_question;

    boolean isTimerRunning = false;

    MediaPlayer player_music;

    MediaPlayer player_correct_sound;
    MediaPlayer player_wrong_sound;

    CountDownTimer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        et_number_of_question = findViewById(R.id.et_numberofquestion);
        et_number_of_question.setText(String.valueOf(SettingItem.numQuestions));
        et_number_of_question.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = et_number_of_question.getText().toString().trim();
                if (!input.isEmpty()) {
                    SettingItem.numQuestions = Integer.parseInt(input);
                } else {
                    SettingItem.numQuestions = 5; // Giá trị mặc định nếu không có giá trị đầu vào
                }
            }
        });





        sw_background_music = findViewById(R.id.switch_background_music);
        boolean isSwitchBackGroundMusicEnable = getIntent().getBooleanExtra("backgroundMusicEnabled", SettingItem.musicBackground);
        sw_background_music.setChecked(isSwitchBackGroundMusicEnable);

        MediaPlayer [] player_soundArray = new MediaPlayer[2];
        player_soundArray[0] = MediaPlayer.create(this, R.raw.correctsound);
        player_soundArray[1] = MediaPlayer.create(this, R.raw.wrongsound);

        player_correct_sound = player_soundArray[0];
        player_wrong_sound = player_soundArray[1];
        player_music = MediaPlayer.create(this, R.raw.musicbackground);


        Button btn = findViewById(R.id.btn_confirm);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Setting.this, CustomizeFunctions.class);
                sw_sound_effect.setChecked(SettingItem.soundEffect);

                if (SettingItem.musicBackground) {
                    startMusic(player_music);
                } else {
                    stopMusic(player_music);
                }
                //               startActivity(intent);



                sw_countDown_timer.setChecked(SettingItem.timer);
                if (SettingItem.timer){
                    startTimer();
                }

            }

            private void startTimer() {
            }
        });

        sw_sound_effect = findViewById(R.id.switch_sound_effect);
        sw_sound_effect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingItem.soundEffect = sw_sound_effect.isChecked();
            }
        });

        sw_background_music = findViewById(R.id.switch_background_music);
        sw_background_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingItem.musicBackground = sw_background_music.isChecked();
                boolean isSwitchBackGroundMusicEnable = getIntent().getBooleanExtra("backgroundMusicEnabled", SettingItem.musicBackground);
                if(isSwitchBackGroundMusicEnable){sw_background_music.setChecked(SettingItem.musicBackground = true);
                    sw_background_music.setChecked(true);

                }
            }
        });


        sw_countDown_timer = findViewById(R.id.switch_timer);
        sw_countDown_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SettingItem.timer = sw_countDown_timer.isChecked();

                Intent intent = new Intent(Setting.this, QuizTest.class);
                boolean sw_countDown_timerChecked = sw_countDown_timer.isChecked();
                intent.putExtra("CountDownEnabled", sw_countDown_timerChecked);
            }
        });
    }

    public void playCorrectSound() {
        if (SettingItem.soundEffect) {
            player_correct_sound.start();
        }
    }

    public void playWrongSound() {
        if (SettingItem.soundEffect) {
            player_wrong_sound.start();
        }
    }

    public void startMusic(MediaPlayer player){
        player.setLooping(true);
        player.start();
    }

    public void stopMusic(MediaPlayer player) {
        player.stop();
        player.seekTo(0);
    }


//    public void startTimer() {
//        if (!isTimerRunning) { // Kiểm tra nếu timer chưa chạy
//            timer = new CountDownTimer(SettingItem.millisInFuture, 1000) {
//                public void onTick(long millisUntilFinished) {
//                    textView_timer.setText(String.valueOf(counter));
//                    counter--;
//                }
//
//                public void onFinish() {
//                    isTimerRunning = false; // Đánh dấu là timer đã kết thúc
//                    resetTimer(); // Gọi hàm resetTimer để bắt đầu đếm ngược mới
//                }
//            };
//            timer.start();
//            isTimerRunning = true; // Đánh dấu là timer đang chạy
//        }
//    }
//
//    public void resetTimer() {
//        if (timer != null) {
//            timer.cancel();
//        }
//        counter = 15;
//        isTimerRunning = false; // Đánh dấu là timer đã được reset
//        startTimer(); // Bắt đầu đếm ngược mới
//    }

}