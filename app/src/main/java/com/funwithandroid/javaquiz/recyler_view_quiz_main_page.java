package com.funwithandroid.javaquiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import  com.funwithandroid.javaquiz.permission.GetPermission;
import  com.funwithandroid.javaquiz.contactdeveloper.Contact;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.funwithandroid.javaquiz.adapter.RecyclerViewAdapter;

import java.util.ArrayList;

public class recyler_view_quiz_main_page extends AppCompatActivity {
   private RecyclerView recylerView;
    private RecyclerViewAdapter recylerViewAdapter;
    private ArrayList<String>  quizquestionlist;
    public String DATA_BASE_NAME=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyler_view_quiz_main_page);
        recylerView=findViewById(R.id.recylerview);
        quizquestionlist=new ArrayList<>();
        ActionBar actionBar=getSupportActionBar();
        assert actionBar != null;
       // actionBar.setTitle("QuizHat");
        actionBar.setTitle(Html.fromHtml("<font color='#03DAC5'>"+getString(R.string.app_name)+"</font>"));
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        AddCardView();
        getRecylerView();
        recylerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(getApplicationContext(),MainScreen.class);
                intent.putExtra("DATA_BASE_NAME",position+1);
               // Toast.makeText(recyler_view_quiz_main_page.this, ""+position, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }
    private void AddCardView(){
        quizquestionlist.add("Calander Quiz");
        quizquestionlist.add("Find the odd man out");
        quizquestionlist.add("Verbal Ability::Antonyms");
        quizquestionlist.add("Verbal Ability::Synonyms");
        quizquestionlist.add("Quiz 5");
        quizquestionlist.add("Quiz 6");
        quizquestionlist.add("Quiz 7");
        quizquestionlist.add("Quiz 8");
        quizquestionlist.add("Quiz 9");
        quizquestionlist.add("Quiz 10");
        quizquestionlist.add("Quiz 11");
        quizquestionlist.add("Quiz 12");
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


}
