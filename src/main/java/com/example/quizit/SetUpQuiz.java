package com.example.quizit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class SetUpQuiz extends AppCompatActivity {

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_quiz);

        Button btn = findViewById(R.id.btn_Continue2);
        spinner = findViewById(R.id.spinner_chonmonhoc);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String selectedItem = spinner.getSelectedItem().toString();
                    if (selectedItem.equals("Tếng Anh mức độ Trung Bình")) {
                        Intent intent = new Intent(SetUpQuiz.this, CourseIntroduction.class);
                        startActivity(intent);
                    } else if (selectedItem.equals("Tiếng Anh mức độ Khá")) {
                        Toast.makeText(SetUpQuiz.this, "Chúng tôi chưa cập nhật ngân hàng câu hỏi. Vui lòng chọn mức độ khác!!!", Toast.LENGTH_SHORT).show();
                    } else if (selectedItem.equals("Tiếng Anh mức độ Giỏi")) {
                        Toast.makeText(SetUpQuiz.this, "Chúng tôi chưa cập nhật ngân hàng câu hỏi. Vui lòng chọn mức độ khác!!!", Toast.LENGTH_SHORT).show();
                    }

            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int positon, long l) {
                String item = adapterView.getItemAtPosition(positon).toString();
                Toast.makeText(SetUpQuiz.this, "Bạn đã chọn môn: " + item, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayList<String> list = new ArrayList<>();
        list.add("Tếng Anh mức độ Trung Bình");
        list.add("Tiếng Anh mức độ Khá");
        list.add("Tiếng Anh mức độ Giỏi");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(SetUpQuiz.this, R.layout.color_spinner, list);

        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);
    }
}