package com.funwithandroid.javaquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.funwithandroid.javaquiz.adapter.RecyclerViewAdapter;
import com.funwithandroid.javaquiz.contactdeveloper.Contact;
import com.funwithandroid.javaquiz.permission.GetPermission;
import com.funwithandroid.javaquiz.recylerData.RecylerData;
import java.util.ArrayList;

public class recyler_view_quiz_main_page extends AppCompatActivity {
   private RecyclerView recylerView;
    private RecyclerViewAdapter recylerViewAdapter;
    private ArrayList<RecylerData>  quizquestionlist;
    public String DATA_BASE_NAME=null;
    private  int highscore,quiz_position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyler_view_quiz_main_page);
        recylerView=findViewById(R.id.recylerview);
        quizquestionlist=new ArrayList<RecylerData>();
        ActionBar actionBar=getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(Html.fromHtml("<font color='#000000'>"+getString(R.string.app_name)+"</font>"));
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        AddCardView();
        getRecylerView();
        LoadHighScoreAtBeginning();
        recylerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                position++;
                quiz_position = position;
                if(quiz_position==5)
                    Toast.makeText(recyler_view_quiz_main_page.this, "Not yet developed", Toast.LENGTH_SHORT).show();
                else {
                    callIntent(position);
                }
            }
        });
    }
    //calling intent to question
    private  void callIntent(int position){
        Intent intent=new Intent(getApplicationContext(),QuestionScreenFinal.class);
        intent.putExtra("DATABASE_POSITION",position);
        startActivityForResult(intent,1);
    }
    //setting recylerView
    private void AddCardView(){
        RecylerData recylerData1=new RecylerData("Antonyms(opposite word)","Highscore:0");
        quizquestionlist.add(recylerData1);
        RecylerData recylerData2=new RecylerData("Synonyms(Simliar word)","Highscore:0");
        quizquestionlist.add(recylerData2);
        RecylerData recylerData3=new RecylerData("Fill in the Blanks ","Highscore:0");
        quizquestionlist.add(recylerData3);
        RecylerData recylerData4=new RecylerData("Find Error in Sentences","Highscore:0");
        quizquestionlist.add(recylerData4);
        RecylerData recylerData5=new RecylerData("One word Phrase","Highscore:0");
        quizquestionlist.add(recylerData5);
    }
    private void getRecylerView() {
        recylerView.setHasFixedSize(true);
        recylerView.setLayoutManager(new LinearLayoutManager(this));
        recylerViewAdapter=new RecyclerViewAdapter(recyler_view_quiz_main_page.this,quizquestionlist);
        recylerView.setAdapter(recylerViewAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_recyle_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.contactdeveloper:
                callDeveloper();
                break;
            case R.id.messagedeveoper:
                messageDeveloper();
                break;
            case R.id.maildeveloper:
                Contact.mailDeveloper(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public  void callDeveloper(){
        if(GetPermission.CheckPermissions(this)){
            Contact.callDeveloper(this);
        }else{
            GetPermission.RequestPermissions(this);
        }
    }
    public  void messageDeveloper(){
        if(GetPermission.CheckPermissionsSMS(this)) {
            Contact.messageDeveloper(this);
        }else{
            GetPermission.RequestPermissionsSMS(this);

        }
    }
//on return of the intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        highscore=getHighScoreOfCard();
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
               int score = data.getIntExtra(QuestionScreenFinal.EXTRA_SCORE, 0);
               if (score > highscore) {
                 setHighScore(score);
               }
            }
        }
    }
    private void setHighScore(int score){
        SharedPreferences shrd = getSharedPreferences("storehighscore", MODE_PRIVATE);
        SharedPreferences.Editor editor = shrd.edit();
        switch (quiz_position) {
            case 1:
                editor.putInt("highscore1", score).apply();
                break;
            case 2:
                editor.putInt("highscore2",score).apply();break;
            case 3:
                editor.putInt("highscore3",score).apply();break;
            case 4:
                editor.putInt("highscore4",score).apply();break;
            case 5:
                editor.putInt("highscore5",score).apply();break;
        }
        quizquestionlist.get(quiz_position-1).setHighscore("Highscore:"+score);
        recylerViewAdapter.notifyItemChanged(quiz_position-1);
    }

private void LoadHighScoreAtBeginning(){
    SharedPreferences getShared = getSharedPreferences("storehighscore", MODE_PRIVATE);
    highscore = getShared.getInt("highscore1", 0);
    quizquestionlist.get(0).setHighscore("Highscore:"+highscore);
    recylerViewAdapter.notifyItemChanged(0);
    highscore = getShared.getInt("highscore2", 0);
    quizquestionlist.get(1).setHighscore("Highscore:"+highscore);
    recylerViewAdapter.notifyItemChanged(1);
    highscore = getShared.getInt("highscore3", 0);
    quizquestionlist.get(2).setHighscore("Highscore:"+highscore);
    recylerViewAdapter.notifyItemChanged(2);
    highscore = getShared.getInt("highscore4", 0);
    quizquestionlist.get(3).setHighscore("Highscore:"+highscore);
    recylerViewAdapter.notifyItemChanged(3);
    highscore = getShared.getInt("highscore5", 0);
    quizquestionlist.get(4).setHighscore("Highscore:"+highscore);
    recylerViewAdapter.notifyItemChanged(4);
}
private int getHighScoreOfCard(){
    SharedPreferences getShared = getSharedPreferences("storehighscore", MODE_PRIVATE);
        switch (quiz_position){
            case 1:
                highscore = getShared.getInt("highscore1", 0);
                break;
            case 2:
                highscore = getShared.getInt("highscore2", 0);
                break;
            case 3:
                highscore = getShared.getInt("highscore3", 0);
                break;
            case 4:
                highscore = getShared.getInt("highscore4", 0);
                break;
            case 5:
                highscore = getShared.getInt("highscore5", 0);
                break;
        }
        return highscore;
}
}
