package com.funwithandroid.javaquiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.funwithandroid.javaquiz.adapter.RecyclerViewAdapter;

import java.util.ArrayList;

public class recyler_view_quiz_main_page extends AppCompatActivity {
    RecyclerView recylerView;
    RecyclerViewAdapter recylerViewAdapter;
    ArrayList<String>  quizquestionlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyler_view_quiz_main_page);
        recylerView=findViewById(R.id.recylerview);
        quizquestionlist=new ArrayList<>();
        ActionBar actionBar=getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("QuizHat");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        AddCardView();
        getRecylerView();
        recylerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(getApplicationContext(),MainScreen.class));
            }
        });
    }
    private void AddCardView(){
        quizquestionlist.add("Quiz 1");
        quizquestionlist.add("Quiz 2");
        quizquestionlist.add("Quiz 3");
        quizquestionlist.add("Quiz 4");
        quizquestionlist.add("Quiz 5");
        quizquestionlist.add("Quiz 6");
        quizquestionlist.add("Quiz 7");
        quizquestionlist.add("Quiz 8");
        quizquestionlist.add("Quiz 9");
        quizquestionlist.add("Quiz 10");
    }
    private void getRecylerView() {
        recylerView.setHasFixedSize(true);
        recylerView.setLayoutManager(new LinearLayoutManager(this));
        recylerViewAdapter=new RecyclerViewAdapter(recyler_view_quiz_main_page.this,quizquestionlist);
        recylerView.setAdapter(recylerViewAdapter);
    }
}
