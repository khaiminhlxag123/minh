package com.example.quizit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class BuildQuestion extends QuizTest {

    private EditText et_Question, et_Answer1, et_Answer2, et_Answer3, et_Answer4, et_Correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_question);

        et_Question = findViewById(R.id.et_question);
        et_Answer1 = findViewById(R.id.et_answer1);
        et_Answer2 = findViewById(R.id.et_answer2);
        et_Answer3 = findViewById(R.id.et_answer3);
        et_Answer4 = findViewById(R.id.et_answer4);
        et_Correct = findViewById(R.id.et_correct);

        Button btnAddQuestion = findViewById(R.id.btn_add_question);
        btnAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = et_Question.getText().toString().trim();
                String answer1 = et_Answer1.getText().toString().trim();
                String answer2 = et_Answer2.getText().toString().trim();
                String answer3 = et_Answer3.getText().toString().trim();
                String answer4 = et_Answer4.getText().toString().trim();
                String correct = et_Correct.getText().toString().trim();

                // Thêm câu hỏi mới vào tệp tin JSON
                addQuestionToJSON(question, answer1, answer2, answer3, answer4, correct);
            }
        });
    }

    public void writeQuestionsToFile(String jsonString) {
        try {
            OutputStream outputStream = getAssets().openFd("questions.json").createOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(jsonString);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addQuestionToJSON(String question, String answer1, String answer2, String answer3, String answer4, String correct) {
        try {
            // Load existing JSON data
            String jsonStr = loadJSONFromAsset("questions.json");

            JSONObject jsonObj = new JSONObject(jsonStr);

            // Get the "questions" array
            JSONArray questions = jsonObj.getJSONArray("questions");

            // Create a new question object
            JSONObject newQuestion = new JSONObject();
            newQuestion.put("question", question);
            newQuestion.put("answer1", answer1);
            newQuestion.put("answer2", answer2);
            newQuestion.put("answer3", answer3);
            newQuestion.put("answer4", answer4);
            newQuestion.put("correct", correct);

            // Add the new question to the "questions" array
            questions.put(newQuestion);

            // Save the updated JSON back to the file
            writeQuestionsToFile(jsonObj.toString());

            // Reload all questions with the updated JSON data
           // loadAllQuestion_Answer_4_Option();

            Toast.makeText(this, "Question added successfully", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to add question", Toast.LENGTH_SHORT).show();
        }

    }
}