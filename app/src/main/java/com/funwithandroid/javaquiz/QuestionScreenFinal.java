package com.funwithandroid.javaquiz;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.funwithandroid.javaquiz.dbhandler.QuizDbHelperAnonyms;
import com.funwithandroid.javaquiz.dbhandler.QuizDbHelperError;
import com.funwithandroid.javaquiz.dbhandler.QuizDbHelperFill;
import com.funwithandroid.javaquiz.dbhandler.QuizDbHelperSynonyms;
import com.funwithandroid.javaquiz.dialog.ViewDialog;
import com.google.android.material.snackbar.Snackbar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class QuestionScreenFinal extends AppCompatActivity {
    private ConstraintLayout coordinatorLayout;
    private  int quiz_position;
    public static String EXTRA_SCORE;
    private List<Question> questionList;
    private QuizDbHelperAnonyms quizDbHelperAnonyms;
    private QuizDbHelperSynonyms quizDbHelperSynonyms;
    private QuizDbHelperError quizDbHelperError;
    private QuizDbHelperFill quizDbHelperFill;
    private RadioGroup optiongroup;
    private RadioButton option1,option2,option3,option4;
    private TextView scoretext,correcttext,questiontext,timetext;
    private Button nextbutton;
    private  int totalquestion,currentquestion=0;
    private boolean answred;
    private Question question;
    private int score=0;
    private ColorStateList radiontextcolor;
    private  long backPressedTime;
    private CountDownTimer countDownTimer;
    private  long milisecondleft=31000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_screen_final);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(Html.fromHtml("<font color='#000000'>"+getString(R.string.app_name)+"</font>"));
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        initilization();
        getIntentValue();
       // countTimer();
        chooseDataBase(quiz_position);
        setNextQuestionOnScreen();
        countTimer();
        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!answred){
                    if (option1.isChecked() || option2.isChecked() || option3.isChecked() ||option4.isChecked() ) {
                        checkAnswer();
                       countDownTimer.cancel();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    resetToOriginlRadionBackground();
                    setNextQuestionOnScreen();
                    countTimer();
                }
            }
        });
    }
    private  void getIntentValue(){
        Intent intent=getIntent();
        quiz_position=intent.getIntExtra("DATABASE_POSITION",0);
    }
    public  void finishTheQuiz(){
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
    public  void chooseDataBase(int position){
        switch (position){
            case 1:
                quizDbHelperAnonyms =new QuizDbHelperAnonyms(this);
                quizDbHelperAnonyms.fillQuestionsToDb();
                questionList= quizDbHelperAnonyms.getAllQuestions();
                totalquestion=questionList.size();
                Collections.shuffle(questionList);
                correcttext.setText("Question:"+totalquestion);
                break;
            case 2:
                quizDbHelperSynonyms =new QuizDbHelperSynonyms(this);
                quizDbHelperSynonyms.fillQuestionsToDb();
                questionList= quizDbHelperSynonyms.getAllQuestions();
                totalquestion=questionList.size();
                Collections.shuffle(questionList);
                correcttext.setText("Question:"+totalquestion);
                break;
            case 3:
                quizDbHelperError =new QuizDbHelperError(this);
                quizDbHelperError.fillQuestionsToDb();
                questionList= quizDbHelperError.getAllQuestions();
                totalquestion=questionList.size();
                Collections.shuffle(questionList);
                correcttext.setText("Question:"+totalquestion);
                break;
            case 4:
                quizDbHelperFill =new QuizDbHelperFill(this);
                quizDbHelperFill.fillQuestionsToDb();
                questionList= quizDbHelperFill.getAllQuestions();
                totalquestion=questionList.size();
                Collections.shuffle(questionList);
                correcttext.setText("Question:"+totalquestion);
                break;
        }
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
        option4=findViewById(R.id.option4);
        nextbutton=findViewById(R.id.nextbutton);
        radiontextcolor=option1.getTextColors();
        coordinatorLayout=findViewById(R.id.coordinatelayout);
       // timetext.setVisibility(View.INVISIBLE);
    }
    public void setNextQuestionOnScreen(){
        option1.setTextColor(radiontextcolor);
        option2.setTextColor(radiontextcolor);
        option3.setTextColor(radiontextcolor);
        option4.setTextColor(radiontextcolor);
        optiongroup.clearCheck();
        if(currentquestion<totalquestion) {
            question = questionList.get(currentquestion);
            questiontext.setText(question.getQuestion());
            option1.setText(question.getOption1());
            option2.setText(question.getOption2());
            option3.setText(question.getOption3());
            option4.setText(question.getOption4());
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
        option4.setTextColor(Color.RED);
        answred=true;
        RadioButton rbSelected = findViewById(optiongroup.getCheckedRadioButtonId());
        //if answer is correct
        if(question.getAnswer()==optiongroup.indexOfChild(rbSelected)+1) {
            switch (question.getAnswer()) {
                case 1:
                    option1.setTextColor(Color.BLUE);
                    break;
                case 2:
                    option2.setTextColor(Color.BLUE);
                    break;
                case 3:
                    option3.setTextColor(Color.BLUE);
                    break;
                case 4:
                    option4.setTextColor(Color.BLUE);
                    break;
            }
            if(option1.isChecked() || option2.isChecked() || option3.isChecked() || option4.isChecked())
                 correctDialogOnCorreect();
        }else{ //if selected answer is incorrect
            if(option1.isChecked() || option2.isChecked() || option3.isChecked() || option4.isChecked())
                      correctDialogOnWrong();
            switch (question.getAnswer()) {
                case 1:
                    option1.setTextColor(Color.BLUE);
                    option1.setChecked(true);
                    break;
                case 2:
                    option2.setTextColor(Color.BLUE);
                    option2.setChecked(true);
                    break;
                case 3:
                    option3.setTextColor(Color.BLUE);
                    option3.setChecked(true);
                    break;
                case 4:
                    option4.setTextColor(Color.BLUE);
                    option4.setChecked(true);
                    break;
            }

        }
        if (currentquestion < totalquestion) {
            nextbutton.setText("Next");
        } else {
            nextbutton.setText("Finish");
        }
    }
    public  void correctDialogOnCorreect(){
        ViewDialog alert = new ViewDialog();
        alert.showDialogcorrect(this);
    }
    public  void correctDialogOnWrong(){
        ViewDialog alert = new ViewDialog();
        alert.showDialogwrong(this);
    }
    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishTheQuiz();
        } else {
            Snackbar snackbar=Snackbar.make(coordinatorLayout,"Press back again to finish",Snackbar.LENGTH_LONG);
            snackbar.setTextColor(Color.WHITE);
            snackbar.setBackgroundTint(Color.GRAY);
            snackbar.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
    public void colorradioView(View view){
        colorRadioButtomOnTouch();
    }
    public void colorRadioButtomOnTouch(){
        resetToOriginlRadionBackground();
        if(option1.isChecked()){
            option1.setBackgroundResource(R.drawable.backround_radio_button);
        }else if(option2.isChecked()){
            option2.setBackgroundResource(R.drawable.backround_radio_button);
        }else if(option3.isChecked()){
            option3.setBackgroundResource(R.drawable.backround_radio_button);
        }else{
            option4.setBackgroundResource(R.drawable.backround_radio_button);
        }
    }
    public  void resetToOriginlRadionBackground(){
        option1.setBackgroundResource(R.drawable.square);
        option2.setBackgroundResource(R.drawable.square);
        option3.setBackgroundResource(R.drawable.square);
        option4.setBackgroundResource(R.drawable.square);
    }
    public void optionGrpEnableDisable(){
        option1.setEnabled(false);
        option2.setEnabled(false);
        option3.setEnabled(false);
        option4.setEnabled(false);
    }
    public  void countTimer(){
        milisecondleft=31000;
       countDownTimer= new CountDownTimer(milisecondleft,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                milisecondleft = millisUntilFinished;
                int minutes = (int) (milisecondleft / 1000) / 60;
                int seconds = (int) (milisecondleft / 1000) % 60;
                String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                timetext.setText(timeLeftFormatted);
            }
            @Override
            public void onFinish() {
                checkAnswer();
            }
        }.start();

    }

}
