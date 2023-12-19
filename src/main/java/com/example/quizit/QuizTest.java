package com.example.quizit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class QuizTest extends Setting implements View.OnClickListener {

    TextView tv_question, tv_currentquestion;
    Button b_answer1, b_answer2, b_answer3, b_answer4, b_Next, b_Previous;

    List<QuestionItem> questionItems_True_False;
    List<QuestionItem> questionItems_4_Option;

    int totalQuestions;
    int currentQuestionIndex;
    int CurrentQuestion = 0;
    int correct = 0;
    int wrong = 0;
    String selectedAnswer;

    MediaPlayer sound;
    boolean isTimerRunning = false;
    CountDownTimer timer;
    long timeleftInMillis;

    int skippedQuestions = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_test);


        isTimerRunning = false;

        ImageView image_clock = findViewById(R.id.img_clock);
        boolean isSwitchCountDownEnabled = getIntent().getBooleanExtra("CountDownEnabled", SettingItem.timer);

        if (isSwitchCountDownEnabled) {

            if (SettingItem.timer) {
                startTimer();
            }
//                if (b_Next.getId() == R.id.btn_Next) {
//                    if(timer != null){
//                        startTimer();
//                    }
//
//            }else {
//                resetTimer(timer);
//            }
            image_clock.setVisibility(View.VISIBLE);// Hiển thị ImageView
        } else {
            image_clock.setVisibility(View.GONE);// Ẩn ImageView
        }



        textView_timer = findViewById(R.id.tv_timer);

        tv_question = findViewById(R.id.question);
        b_answer1 = findViewById(R.id.answer1);
        b_answer2 = findViewById(R.id.answer2);
        b_answer3 = findViewById(R.id.answer3);
        b_answer4 = findViewById(R.id.answer4);
        b_Next = findViewById(R.id.btn_Next);
        b_Previous = findViewById(R.id.btn_Previous);
        tv_currentquestion = findViewById(R.id.txt_SoCau);

        b_answer1.setOnClickListener(this);
        b_answer2.setOnClickListener(this);
        b_answer3.setOnClickListener(this);
        b_answer4.setOnClickListener(this);
        b_Next.setOnClickListener(this);
        b_Previous.setOnClickListener(this);


        //get all the questions

        loadAllQuestions();


        //shuffle the question if you want
//        Collections.shuffle(questionItems_4_Option);
//        Collections.shuffle(questionItems_True_False);
        //load first question


        setQuestionScreen(CurrentQuestion);



//        b_Next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (timer != null) {
//                    timer.cancel(); // Cancel the current timer
//                    startTimer(); // Start a new timer
//                }
//                onClick(view);
//            }
//        });


    }


    // load file json from assets folder
    public String loadJSONFromAsset(String file) {
        String json = "";
        try {
            InputStream is = getAssets().open(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
    public void loadAllQuestions() {
        questionItems_4_Option = new ArrayList<>();
        questionItems_True_False = new ArrayList<>();

        // Load all questions into json string
        String jsonStr = loadJSONFromAsset("questions.json");

        // Load all data into the lists
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            JSONArray questions_4_Option = jsonObj.getJSONArray("questions_4_Option");
            JSONArray questions_True_False = jsonObj.getJSONArray("questions_True_False");

            int numQuestionsToLoad = Math.min(SettingItem.numQuestions, questions_4_Option.length() + questions_True_False.length());

            // Load questions with 4 options
            for (int i = 0; i < numQuestionsToLoad && i < questions_4_Option.length(); i++) {
                JSONObject question = questions_4_Option.getJSONObject(i);
                String questionString = question.getString("question");
                String answer1String = question.getString("answer1");
                String answer2String = question.getString("answer2");
                String answer3String = question.getString("answer3");
                String answer4String = question.getString("answer4");
                String correctString = question.getString("correct");

                questionItems_4_Option.add(new QuestionItem(
                        questionString,
                        answer1String,
                        answer2String,
                        answer3String,
                        answer4String,
                        correctString,
                        false
                ));
            }

            // Load true/false questions
            for (int i = 0; i < numQuestionsToLoad - questionItems_4_Option.size() && i < questions_True_False.length(); i++) {
                JSONObject question = questions_True_False.getJSONObject(i);
                String questionString = question.getString("question");
                String answer2String = question.getString("answer2");
                String answer3String = question.getString("answer3");
                String correctString = question.getString("correct");

                questionItems_True_False.add(new QuestionItem(
                        questionString,
                        answer2String,
                        answer3String,
                        correctString,
                        true
                ));
            }
//            // Combine both lists into one
//            List<QuestionItem> combinedList = new ArrayList<>();
//            combinedList.addAll(questionItems_4_Option);
//            combinedList.addAll(questionItems_True_False);
//
//            // Shuffle the combined list
//            Collections.shuffle(combinedList);
//
//            // Clear previous lists and add shuffled questions back to their respective lists
//            questionItems_4_Option.clear();
//            questionItems_True_False.clear();
//            for (int i = 0; i < combinedList.size(); i++) {
//                QuestionItem questionItem = combinedList.get(i);
//                if (i < questions_4_Option.length()) {
//                    questionItems_4_Option.add(questionItem);
//                } else {
//                    questionItems_True_False.add(questionItem);
//                }
//            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void setQuestionScreen(int number) {
        int totalQuestions = questionItems_4_Option.size() + questionItems_True_False.size();
        String currentQuestionText = (number + 1) + "/" + totalQuestions;
        tv_currentquestion.setText(currentQuestionText);

        if (number < totalQuestions) {
            QuestionItem questionItem = null;
            boolean isTrueFalseQuestion = true;

            if (number < questionItems_4_Option.size()) {
                // Câu hỏi có 4 lựa chọn
                questionItem = questionItems_4_Option.get(number);
                isTrueFalseQuestion = false; // Set this to false for 4-option questions
            } else {
                // Câu hỏi đúng sai
                int trueFalseIndex = number - questionItems_4_Option.size();
                questionItem = questionItems_True_False.get(trueFalseIndex);
                isTrueFalseQuestion = true; // Set this to true for true/false questions
            }

            tv_question.setText(questionItem.getQuestion());
            b_answer1.setText(questionItem.getAnswer1());
            b_answer2.setText(questionItem.getAnswer2());
            b_answer3.setText(questionItem.getAnswer3());
            b_answer4.setText(questionItem.getAnswer4());

            // Show or hide buttons based on the question type
            if (isTrueFalseQuestion) {
                // Câu hỏi đúng sai: Ẩn nút b_answer1 và b_answer4
                b_answer1.setVisibility(View.GONE);
                b_answer4.setVisibility(View.GONE);
            } else {
                // Câu hỏi có 4 lựa chọn: Hiển thị đầy đủ 4 nút
                b_answer1.setVisibility(View.VISIBLE);
                b_answer4.setVisibility(View.VISIBLE);
            }
        }

        if (number == 0) {
            b_Previous.setVisibility(View.GONE);
        } else {
            b_Previous.setVisibility(View.VISIBLE);
        }
    }


    private void updateTimerText() {
        textView_timer.setText(String.valueOf(timeleftInMillis / 1000));
    }

    private void moveToNextQuestion() {
        if (CurrentQuestion < questionItems_4_Option.size() + questionItems_True_False.size() - 1) {
            CurrentQuestion++;
            setQuestionScreen(CurrentQuestion);
        } else {
            displayResults();
        }
    }

    public void onClick(View view) {

        Button clickedButton = (Button) view;
        b_answer1.setBackgroundResource(R.drawable.corner_background);
        b_answer2.setBackgroundResource(R.drawable.corner_background);
        b_answer3.setBackgroundResource(R.drawable.corner_background);
        b_answer4.setBackgroundResource(R.drawable.corner_background);

        if (timer != null) {
            timer.cancel(); // Cancel the current timer
            startTimer(); // Start a new timer
        }


        if (clickedButton.getId() == R.id.btn_Next) {

            if (CurrentQuestion < questionItems_4_Option.size()) {

                QuestionItem questionItem = questionItems_4_Option.get(CurrentQuestion);
                if (selectedAnswer == null) { // Kiểm tra nếu selectedAnswer là null
                    selectedAnswer = ""; // Gán giá trị mặc định là chuỗi rỗng
                }
                if (questionItem.getCorrect(selectedAnswer).equals(questionItem.getCorrect())) {
                    // Correct answer
                    playCorrectSound();
                    correct++;
                    Toast.makeText(QuizTest.this, "Đúng!", Toast.LENGTH_SHORT).show();
                } else {
                    playWrongSound();
                    // Wrong answer
                    wrong++;
                    Toast.makeText(QuizTest.this, "Sai! Câu trả lời đúng: " + questionItem.getCorrect(), Toast.LENGTH_SHORT).show();
                }
            } else {
                int trueFalseIndex = CurrentQuestion - questionItems_4_Option.size();
                QuestionItem questionItem = questionItems_True_False.get(trueFalseIndex);
                if (questionItem.getCorrect(selectedAnswer).equals(questionItem.getCorrect())) {
                    // Correct answer
                    playCorrectSound();
                    correct++;
                    Toast.makeText(QuizTest.this, "Đúng!", Toast.LENGTH_SHORT).show();
                } else {
                    playWrongSound();
                    // Wrong answer
                    wrong++;
                    Toast.makeText(QuizTest.this, "Sai! Câu trả lời đúng: " + questionItem.getCorrect(), Toast.LENGTH_SHORT).show();
                }
            }
            if (CurrentQuestion < questionItems_4_Option.size() + questionItems_True_False.size() - 1) {
                CurrentQuestion++;
                resetTimer();
                setQuestionScreen(CurrentQuestion);
            } else {
                displayResults();
            }
//            moveToNextQuestion();
//            resetTimer();
//            setQuestionScreen(CurrentQuestion);

//            } else if(timeleftInMillis <= 0) {
//                skippedQuestions++;
//                moveToNextQuestion();
//                resetTimer();
//            }else {
//                displayResults();


        } else if (clickedButton.getId() == R.id.btn_Previous) {
            if (CurrentQuestion > 0) {
                CurrentQuestion--;
                setQuestionScreen(CurrentQuestion);
            }
        } else {
            clickedButton.setBackgroundResource(R.drawable.corner_bg_click);
            selectedAnswer = clickedButton.getText().toString().trim();
        }


    }
    public void startTimer() {
        if (!isTimerRunning) { // Kiểm tra nếu timer chưa chạy
            timer = new CountDownTimer(SettingItem.millisInFuture, 1000) {
                public void onTick(long millisUntilFinished) {
                    timeleftInMillis = millisUntilFinished;
                    updateTimerText();
                }

                public void onFinish() {
                    isTimerRunning = false; // Đánh dấu là timer đã kết thúc
                    moveToNextQuestion(); // Chuyển sang câu hỏi kế tiếp
                    resetTimer(); // Gọi hàm resetTimer để bắt đầu đếm ngược mới
                }
            };
            timer.start();
            isTimerRunning = true; // Đánh dấu là timer đang chạy
        }
    }

    public void resetTimer() {
        if (timer != null) {
            timer.cancel();
        }
        counter = 15;
        isTimerRunning = false; // Đánh dấu là timer đã được reset
        startTimer(); // Bắt đầu đếm ngược mới
    }


    public void displayResults() {

//        int totalQuestions = questionItems_4_Option.size() + questionItems_True_False.size();
        int score = correct * 10;
        Intent intent = new Intent(QuizTest.this, ResultView.class);
        intent.putExtra("Điểm số", score);
        intent.putExtra("Đúng", correct);
        intent.putExtra("Sai", wrong);
//        intent.putExtra("skippedQuestions", skippedQuestions);
        startActivity(intent);
        finish();
    }

}

