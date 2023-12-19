package com.example.quizit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultView extends AppCompatActivity {

    TextView tv_SoCauDung, tv_SoCauSai, tv_TotalSoDiem, tv_SoCauHetGio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_view);


        tv_TotalSoDiem = findViewById(R.id.txt_TotalSoDiem);
        tv_SoCauDung = findViewById(R.id.txt_SoCauDung);
        tv_SoCauSai = findViewById(R.id.txt_SoCauSai);
//        tv_SoCauHetGio = findViewById(R.id.txt_SoCauHetGio);

        int correct = getIntent().getIntExtra("Đúng", 0);
        int wrong = getIntent().getIntExtra("Sai", 0);
        int score = getIntent().getIntExtra("Điểm số", 0);
//        int skippedQuestions = getIntent().getIntExtra("skippedQuestions", 0);




        tv_TotalSoDiem.setText("" + score);
        tv_SoCauDung.setText("" + correct);
        tv_SoCauSai.setText("" + wrong);
//        tv_SoCauHetGio.setText("" + skippedQuestions);

    }

    public void btnRestart(View view) {
        Intent intent = new Intent(ResultView.this, SetUpQuiz.class);
        startActivity(intent);
    }
}