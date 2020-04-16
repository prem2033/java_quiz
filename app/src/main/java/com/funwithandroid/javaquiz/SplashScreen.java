package com.funwithandroid.javaquiz;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash_screen);
        Intent intent=new Intent(this,recyler_view_quiz_main_page.class);
        startActivity(intent);
        finish();
    }
}
