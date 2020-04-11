package com.funwithandroid.javaquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.funwithandroid.javaquiz.dbhandler.QuizDbHelper;
import com.funwithandroid.javaquiz.dbhandler.QuizDbHelpersecond;

public class MainScreen extends AppCompatActivity {
     private Button quizutton;
     private  String VERSION="VERSION 1.0.0";
     private TextView highscoretext;
     private  int highscore,quiz_position;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quizutton=findViewById(R.id.quizpage);
        highscoretext=findViewById(R.id.highscoretext);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("QuizHat");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        getIntentValue();
        loadHighScore();//from sharedPreferences
        SharedPreferences getShared = getSharedPreferences("quiz", MODE_PRIVATE);
        VERSION = getShared.getString("version","VERSION 1.0.0");
        quizutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //   if(VERSION.equals("VERSION 1.0.0")) {
                   switch (quiz_position ) {
                       case 1:
                           QuizDbHelper quizDbHelper = new QuizDbHelper(getApplicationContext());
                           quizDbHelper.fillQuestionsToDb();
                            checkForDatabase();
                            break;
                       case 2:
                           QuizDbHelpersecond quizDbHelpersecond=new QuizDbHelpersecond(getApplicationContext());
                           quizDbHelpersecond.fillQuestionsToDb();
                           checkForDatabase();
                           break;
                       default:
                           Toast.makeText(MainScreen.this, "Contact to developer", Toast.LENGTH_SHORT).show();
                           break;
                   }
                   quizPage();
             //  }

            }
        });
    }
    private  void quizPage(){
        Intent intent=new Intent(getApplicationContext(),QuestionScreen.class);
        intent.putExtra("DATA_BASE_POSITION",quiz_position);
        //startActivityForResult(new Intent(getApplicationContext(),QuestionScreen.class),1);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                int score = data.getIntExtra(QuestionScreen.EXTRA_SCORE, 0);
                if (score > highscore) {
                    //switch (quiz_position) {
                         setHighScore(score);
                   // }
                }
            }
        }
    }
    private void setHighScore(int score){
        highscoretext.setText("Highscore:"+score);
        SharedPreferences shrd = getSharedPreferences("storehighscore", MODE_PRIVATE);
        SharedPreferences.Editor editor = shrd.edit();

        switch (quiz_position) {
            case 1:
            editor.putInt("highscore", score).apply();break;
            case 2:
                editor.putInt("highscore2",score).apply();break;
        }
    }
    private void checkForDatabase(){
        SharedPreferences shrd = getSharedPreferences("quiz", MODE_PRIVATE);
        SharedPreferences.Editor editor = shrd.edit();
        editor.putString("version","VERSION 1.0.1");
        editor.apply();
    }
    private void loadHighScore(){
        SharedPreferences getShared = getSharedPreferences("storehighscore", MODE_PRIVATE);
        switch (quiz_position) {
            case 1:
                highscore = getShared.getInt("highscore", 0);
                 highscoretext.setText("Highscore:" + highscore);
                 break;
            case 2:
                highscore=getShared.getInt("highscore2",0);
                highscoretext.setText("Highscore:"+highscore);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbarmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting:
                //Intent intent=new Intent(Settings.ACTION_APPLICATION_SETTINGS);
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
                break;
            case R.id.reset:
                clearhighscoreSharedPreference();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void clearhighscoreSharedPreference(){
        SharedPreferences shrd=getSharedPreferences("storehighscore",MODE_PRIVATE);
        SharedPreferences.Editor editor=shrd.edit();
        switch (quiz_position) {
            case 1:
            editor.putInt("highscore", 0).apply();break;
            case 2:
                editor.putInt("highscore2",0).apply();break;
        }
        Toast.makeText(this, "Highescore refreshed", Toast.LENGTH_SHORT).show();
        highscoretext.setText("Highscore:0");
        highscore=0;
    }
    private  void getIntentValue(){
        Intent intent=getIntent();
       quiz_position=intent.getIntExtra("DATA_BASE_NAME",0);
    }
}
