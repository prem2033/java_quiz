package com.funwithandroid.javaquiz;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Collections;
import java.util.List;

public class QuestionScreen extends AppCompatActivity {
    public static String EXTRA_SCORE;
    private List<Question> questionList;
    private  QuizDbHelper quizDbHelper;
    private RadioGroup optiongroup;
    private RadioButton option1,option2,option3;
    private TextView scoretext,correcttext,questiontext,timetext;
    private Button nextbutton;
    private  int totalquestion,currentquestion=0;
    private boolean answred;
    private Question question;
    private int i=0,score=0;
    private ColorStateList radiontextcolor;
    private  long backPressedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_screen);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Question1");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        initilization();
        quizDbHelper=new QuizDbHelper(this);
        questionList=quizDbHelper.getAllQuestions();
        totalquestion=questionList.size();
        Collections.shuffle(questionList);
        correcttext.setText("Question:"+totalquestion);
        setNextQuestionOnScreen();
        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!answred){
                    if (option1.isChecked() || option2.isChecked() || option3.isChecked()) {
                        checkAnswer();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                }else {
                          setNextQuestionOnScreen();
                  }
            }
        });
    }
    private  void initilization(){
        scoretext=findViewById(R.id.scoretext);
        correcttext=findViewById(R.id.correcttext);
        questiontext=findViewById(R.id.questiontext);
        timetext=findViewById(R.id.timetext);
        optiongroup=findViewById(R.id.optiongroup);
        option1=findViewById(R.id.option1);
        option2=findViewById(R.id.option2);
        option3=findViewById(R.id.option3);
        nextbutton=findViewById(R.id.nextbutton);
        radiontextcolor=option1.getTextColors();
    }
    public void setNextQuestionOnScreen(){
        option1.setTextColor(radiontextcolor);
        option2.setTextColor(radiontextcolor);
        option3.setTextColor(radiontextcolor);
        optiongroup.clearCheck();
        if(currentquestion<totalquestion) {
            question = questionList.get(currentquestion);
            questiontext.setText(question.getQuestion());
            option1.setText(question.getOption1());
            option2.setText(question.getOption2());
            option3.setText(question.getOption3());
            currentquestion++;
            answred=false;
            correcttext.setText("Questions:"+currentquestion+"/"+totalquestion);
            nextbutton.setText("Confirm");
        }else{
            finishTheQuiz();
        }
    }
    private void checkAnswer() {
        RadioButton rbSelected = findViewById(optiongroup.getCheckedRadioButtonId());
        int answerNr = optiongroup.indexOfChild(rbSelected) + 1;

        if (answerNr == question.getAnswer()) {
            score++;
            scoretext.setText("Score: " + score);
        }

        showSolution();
    }
    private void showSolution() {
        answred=true;
        option1.setTextColor(Color.RED);
        option2.setTextColor(Color.RED);
        option3.setTextColor(Color.RED);
        answred=true;
        RadioButton rbSelected = findViewById(optiongroup.getCheckedRadioButtonId());
        if(question.getAnswer()==optiongroup.indexOfChild(rbSelected)+1) {
            switch (question.getAnswer()) {
                case 1:
                    option1.setTextColor(Color.BLUE);
                    //questiontext.setText("correct");
                    correctDialogOnCorreect();
                    break;
                case 2:
                    option2.setTextColor(Color.BLUE);
                    correctDialogOnCorreect();
                    //questiontext.setText("correct");
                    break;
                case 3:
                    option3.setTextColor(Color.BLUE);
                    correctDialogOnCorreect();
                   // questiontext.setText("correct");
                    break;
            }
        }else{
            switch (question.getAnswer()) {
                case 1:
                    option1.setTextColor(Color.BLUE);
                    correctDialogOnWrong();
                    //questiontext.setText("Wrong");
                    break;
                case 2:
                    option2.setTextColor(Color.BLUE);
                    correctDialogOnWrong();
                    //questiontext.setText("Wrong");
                    break;
                case 3:
                    option3.setTextColor(Color.BLUE);
                    correctDialogOnWrong();
                   // questiontext.setText("Wrong");
                    break;
            }
        }
        if (currentquestion < totalquestion) {
            nextbutton.setText("Next");
        } else {
            nextbutton.setText("Finish");
        }
    }
    public  void finishTheQuiz(){
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishTheQuiz();
        } else {
            Toast.makeText(this, "Press back again to finish", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }
    public  void correctDialogOnCorreect(){
         final AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Correct");
        final AlertDialog alert = dialog.create();
        alert.show();
        new CountDownTimer(1000, 500) {
            @Override
            public void onTick(long millisUntilFinished) {         }
            @Override
            public void onFinish() {
                alert.dismiss();
            }
        }.start();
    }
    public  void correctDialogOnWrong(){
        final AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Wrong");
        final AlertDialog alert = dialog.create();
        alert.show();
        new CountDownTimer(1000, 500) {
            @Override
            public void onTick(long millisUntilFinished) {         }
            @Override
            public void onFinish() {
                alert.dismiss();
            }
        }.start();
    }


}
