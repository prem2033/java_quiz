package com.funwithandroid.javaquiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.funwithandroid.javaquiz.dbhandler.QuizDbHelperAnonyms;
import com.funwithandroid.javaquiz.dbhandler.QuizDbHelperFill;
import com.funwithandroid.javaquiz.dbhandler.QuizDbHelperError;
import com.funwithandroid.javaquiz.dbhandler.QuizDbHelperSynonyms;
import com.funwithandroid.javaquiz.dialog.ViewDialog;

import java.util.Collections;
import java.util.List;

public class QuestionScreenFinal extends AppCompatActivity {
    private Button quizutton;
    private  String VERSION="VERSION 1.0.0";
    private TextView highscoretext;
    private  int highscore,quiz_position;
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
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_screen_final);
        quizutton=findViewById(R.id.quizpage);
        highscoretext=findViewById(R.id.highscoretext);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(Html.fromHtml("<font color='#000000'>"+getString(R.string.app_name)+"</font>"));
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        initilization();
        getIntentValue();
        chooseDataBase(quiz_position);
        setNextQuestionOnScreen();
        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!answred){
                    if (option1.isChecked() || option2.isChecked() || option3.isChecked() ||option4.isChecked() ) {
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
        if(question.getAnswer()==optiongroup.indexOfChild(rbSelected)+1) {
            switch (question.getAnswer()) {
                case 1:
                    option1.setTextColor(Color.BLUE);
                    correctDialogOnCorreect();
                    break;
                case 2:
                    option2.setTextColor(Color.BLUE);
                    correctDialogOnCorreect();
                    break;
                case 3:
                    option3.setTextColor(Color.BLUE);
                    correctDialogOnCorreect();
                    break;
                case 4:
                    option4.setTextColor(Color.BLUE);
                    correctDialogOnCorreect();
                    break;
            }
        }else{
            switch (question.getAnswer()) {
                case 1:
                    option1.setTextColor(Color.BLUE);
                    correctDialogOnWrong();
                    break;
                case 2:
                    option2.setTextColor(Color.BLUE);
                    correctDialogOnWrong();
                    break;
                case 3:
                    option3.setTextColor(Color.BLUE);
                    correctDialogOnWrong();
                    break;
                case 4:
                    option4.setTextColor(Color.BLUE);
                    correctDialogOnWrong();
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
            Toast.makeText(this, "Press back again to finish", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }

}
