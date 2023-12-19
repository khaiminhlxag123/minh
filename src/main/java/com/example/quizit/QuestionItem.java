package com.example.quizit;
public class QuestionItem extends QuizTest  {
    public String question, answer1, answer2, answer3, answer4, correct;
    private boolean isTrueFalse;
    public QuestionItem(String question, String answer1, String answer2, String answer3, String answer4, String correct, boolean isTrueFalse) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.correct = correct;
        this.isTrueFalse = isTrueFalse;
    }

    public QuestionItem(String question, String answer2, String answer3, String correct, boolean isTrueFalse) {
        this.question = question;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.correct = correct;
        this.isTrueFalse = isTrueFalse;
    }


    public String getQuestion() {
        return question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public String getCorrect(String selectedAnswer) {
        return selectedAnswer;

    }

    public boolean isTrueFalse() {
        return isTrueFalse;
    }
    public String getCorrect() {
        return correct;}
}

