package com.funwithandroid.javaquiz;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.funwithandroid.javaquiz.dbhandler.QuizDbHelperAnonyms;
import com.funwithandroid.javaquiz.dbhandler.QuizDbHelperError;
import com.funwithandroid.javaquiz.dbhandler.QuizDbHelperFill;
import com.funwithandroid.javaquiz.dbhandler.QuizDbHelperSynonyms;
import com.funwithandroid.javaquiz.dialog.ViewDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class QuestionScreenFinal extends AppCompatActivity {
    public static String EXTRA_SCORE;
    private ConstraintLayout coordinatorLayout;
    private int quiz_position;
    private List<Question> questionList;
    private QuizDbHelperAnonyms quizDbHelperAnonyms;
    private QuizDbHelperSynonyms quizDbHelperSynonyms;
    private QuizDbHelperError quizDbHelperError;
    private QuizDbHelperFill quizDbHelperFill;
    private RadioGroup optiongroup;
    private RadioButton option1, option2, option3, option4;
    private TextView scoretext, correcttext, questiontext, timetext;
    private Button nextbutton;
    private int totalquestion, currentquestion = 0;
    private boolean answred, checkedornot = false;
    private Question question;
    private int score = 0, selectedradionbutton;
    private ColorStateList radiontextcolor;
    private long backPressedTime;
    private CountDownTimer countDownTimer;
    private long milisecondleft = 31000;
    private boolean timerflag = false, dialogshown = false, finishedQuestion = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_screen_final);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(Html.fromHtml("<font color='#000000'>" + getString(R.string.app_name) + "</font>"));
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        initilization();
        getIntentValue();
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "Use Potrait Mode for better results", Toast.LENGTH_SHORT).show();
        }
        if (savedInstanceState == null) {
            Log.d("XXXX", "run at null");
            countTimer();
            chooseDataBase(quiz_position);
            setNextQuestionOnScreen();
        } else {
            // Log.d("XXXXX","else part is called------------------------------------");
            milisecondleft = savedInstanceState.getLong("TIME_LEFT");//store time left for timer
            score = savedInstanceState.getInt("SCORE");//save the previous scroe
            scoretext.setText("Score: " + score); //print the current score
            answred = savedInstanceState.getBoolean("ANSWERED");
            currentquestion = savedInstanceState.getInt("QUESTION_NUMBER") - 1;
            totalquestion = savedInstanceState.getInt("TOTAL_QUESTION");
            correcttext.setText("Questions:" + currentquestion + "/" + totalquestion);
            questionList = savedInstanceState.getParcelableArrayList("ARRAY_LIST");
            quiz_position = savedInstanceState.getInt("QUIZ_POSITION");
            selectedradionbutton = savedInstanceState.getInt("RADIO_SELECTED");
            finishedQuestion = savedInstanceState.getBoolean("FINISHED_QUESTION");
            if (finishedQuestion) {
                congratsDialog();
            }
            //if answer is selected before rotate the screen select
            if (selectedradionbutton != -1) {
                RadioButton radionbuttonSelected = findViewById(selectedradionbutton);
                switch (optiongroup.indexOfChild(radionbuttonSelected) + 1) {
                    case 1:
                        option1.setBackgroundResource(R.drawable.backround_radio_button);
                        break;
                    case 2:
                        option2.setBackgroundResource(R.drawable.backround_radio_button);
                        break;
                    case 3:
                        option3.setBackgroundResource(R.drawable.backround_radio_button);
                        break;
                    case 4:
                        option4.setBackgroundResource(R.drawable.backround_radio_button);
                        break;
                }


            }
            if (answred) {
                setNextQuestionOnScreen();
                showSolution();
                updateTimeText();
            } else {
                countTimer();
                dialogshown = false;
                setNextQuestionOnScreen();
                //showSolution();
            }
        }
        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answred) {
                    if (option1.isChecked() || option2.isChecked() || option3.isChecked() || option4.isChecked()) {
                        checkAnswer();
                        if (timerflag) {
                            countDownTimer.cancel();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    resetToOriginlRadionBackground();//reset background of radiobutton
                    setNextQuestionOnScreen(); //call next question
                    milisecondleft = 31000;//reset the timer before called setNext question
                    countTimer();
                }
            }
        });
    }

    private void getIntentValue() {
        Intent intent = getIntent();
        quiz_position = intent.getIntExtra("DATABASE_POSITION", 0);

    }

    public void finishTheQuiz() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, resultIntent);
        Log.d("XXXX", "finished is called");
        finish();
    }

    public void chooseDataBase(int position) {
        switch (position) {
            case 1:
                quizDbHelperAnonyms = new QuizDbHelperAnonyms(this);
                quizDbHelperAnonyms.fillQuestionsToDb();
                questionList = quizDbHelperAnonyms.getAllQuestions();
                Collections.shuffle(questionList);
                quizDbHelperAnonyms.close();
                break;
            case 2:
                quizDbHelperSynonyms = new QuizDbHelperSynonyms(this);
                quizDbHelperSynonyms.fillQuestionsToDb();
                questionList = quizDbHelperSynonyms.getAllQuestions();
                Collections.shuffle(questionList);
                quizDbHelperSynonyms.close();
                break;
            case 3:
                quizDbHelperError = new QuizDbHelperError(this);
                quizDbHelperError.fillQuestionsToDb();
                questionList = quizDbHelperError.getAllQuestions();
                Collections.shuffle(questionList);
                quizDbHelperError.close();
                break;
            case 4:
                quizDbHelperFill = new QuizDbHelperFill(this);
                quizDbHelperFill.fillQuestionsToDb();
                questionList = quizDbHelperFill.getAllQuestions();
                Collections.shuffle(questionList);
                quizDbHelperFill.close();
                break;
        }
        totalquestion = questionList.size();
    }

    private void initilization() {
        scoretext = findViewById(R.id.scoretext);
        correcttext = findViewById(R.id.correcttext);
        questiontext = findViewById(R.id.questiontext);
        timetext = findViewById(R.id.timetext);
        optiongroup = findViewById(R.id.optiongroup);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        nextbutton = findViewById(R.id.nextbutton);
        radiontextcolor = option1.getTextColors();
        coordinatorLayout = findViewById(R.id.coordinatelayout);
        // timetext.setVisibility(View.INVISIBLE);
    }

    public void setNextQuestionOnScreen() {
        // Log.d("XXXX","setonQuestion is called");
        option1.setTextColor(radiontextcolor);
        option2.setTextColor(radiontextcolor);
        option3.setTextColor(radiontextcolor);
        option4.setTextColor(radiontextcolor);
        optiongroup.clearCheck();
        if (currentquestion < totalquestion) {
            //Log.d("XXXX","current question is less then total");
            question = questionList.get(currentquestion);
            questiontext.setText(question.getQuestion());
            option1.setText(question.getOption1());
            option2.setText(question.getOption2());
            option3.setText(question.getOption3());
            option4.setText(question.getOption4());
            currentquestion++;
            answred = false;
            correcttext.setText("Questions:" + currentquestion + "/" + totalquestion);
            nextbutton.setText("Confirm");
        } else {
            //finishTheQuiz();
            finishedQuestion = true;
            congratsDialog();
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
        answred = true;
        option1.setTextColor(Color.RED);
        option2.setTextColor(Color.RED);
        option3.setTextColor(Color.RED);
        option4.setTextColor(Color.RED);
        answred = true;
        RadioButton radionbuttonSelected = findViewById(optiongroup.getCheckedRadioButtonId());
        //if answer is correct
        if (question.getAnswer() == optiongroup.indexOfChild(radionbuttonSelected) + 1) {
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
            if (option1.isChecked() || option2.isChecked() || option3.isChecked() || option4.isChecked())
                correctDialogOnCorreect();
        } else { //if selected answer is incorrect
            if (option1.isChecked() || option2.isChecked() || option3.isChecked() || option4.isChecked())
                correctDialogOnWrong();
            switch (question.getAnswer()) {
                case 1:
                    option1.setTextColor(Color.BLUE);
                    // option1.setChecked(true);
                    break;
                case 2:
                    option2.setTextColor(Color.BLUE);
                    // option2.setChecked(true);
                    break;
                case 3:
                    option3.setTextColor(Color.BLUE);
                    //option3.setChecked(true);
                    break;
                case 4:
                    option4.setTextColor(Color.BLUE);
                    // option4.setChecked(true);
                    break;
            }

        }
        if (currentquestion < totalquestion) {
            nextbutton.setText("Next");
        } else {
            nextbutton.setText("Finish");
        }
    }

    public void correctDialogOnCorreect() {
        dialogshown = true;
        ViewDialog alert = new ViewDialog();
        alert.showDialogcorrect(this);
    }

    public void correctDialogOnWrong() {
        dialogshown = true;
        ViewDialog alert = new ViewDialog();
        alert.showDialogwrong(this);
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishTheQuiz();
        } else {
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Press back again to finish", Snackbar.LENGTH_LONG);
            snackbar.setTextColor(Color.WHITE);
            snackbar.setBackgroundTint(Color.GRAY);
            snackbar.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    public void colorradioView(View view) {
        colorRadioButtomOnTouch();
    }

    public void colorRadioButtomOnTouch() {
        resetToOriginlRadionBackground();
        checkedornot = true;
        if (option1.isChecked()) {
            option1.setBackgroundResource(R.drawable.backround_radio_button);
        } else if (option2.isChecked()) {
            option2.setBackgroundResource(R.drawable.backround_radio_button);
        } else if (option3.isChecked()) {
            option3.setBackgroundResource(R.drawable.backround_radio_button);
        } else {
            option4.setBackgroundResource(R.drawable.backround_radio_button);
        }
    }

    public void resetToOriginlRadionBackground() {
        option1.setBackgroundResource(R.drawable.square);
        option2.setBackgroundResource(R.drawable.square);
        option3.setBackgroundResource(R.drawable.square);
        option4.setBackgroundResource(R.drawable.square);
    }

    public void optionGrpEnableDisable() {
        option1.setEnabled(false);
        option2.setEnabled(false);
        option3.setEnabled(false);
        option4.setEnabled(false);
    }

    public void countTimer() {
        countDownTimer = new CountDownTimer(milisecondleft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerflag = true;
                milisecondleft = millisUntilFinished;
                updateTimeText();
            }

            @Override
            public void onFinish() {
                timerflag = false;
                checkAnswer();
            }
        }.start();
    }

    private void updateTimeText() {
        int minutes = (int) (milisecondleft / 1000) / 60;
        int seconds = (int) (milisecondleft / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timetext.setText(timeLeftFormatted);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (timerflag) {
            countDownTimer.cancel();
            timerflag = false;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!timerflag) {
            countTimer();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timerflag)
            countDownTimer.onFinish();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("TIME_LEFT", milisecondleft);
        outState.putInt("SCORE", score);
        outState.putParcelableArrayList("ARRAY_LIST", (ArrayList<Question>) questionList);
        outState.putBoolean("ANSWERED", answred);
        outState.putInt("QUESTION_NUMBER", currentquestion);
        outState.putInt("TOTAL_QUESTION", totalquestion);
        outState.putInt("QUIZ_POSITION", quiz_position);
        outState.putBoolean("RADIO_CHECKED", checkedornot);
        outState.putInt("RADIO_SELECTED", optiongroup.getCheckedRadioButtonId());
        outState.putBoolean("FINISHED_QUESTION", finishedQuestion);
    }

    public void congratsDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.congarts_dialog);
        TextView textView = dialog.findViewById(R.id.scoretextdialog);
        textView.setText("Correct=" + score);
        Button dialogButton = dialog.findViewById(R.id.buttonok);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finishTheQuiz();
            }
        });
        dialog.show();
    }

}
