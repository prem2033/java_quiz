package com.funwithandroid.javaquiz.dbhandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.funwithandroid.javaquiz.Question;
import com.funwithandroid.javaquiz.dbParams.DbVariables.QuestionTable;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelpersecond extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    public QuizDbHelpersecond(Context context) {
        super(context, QuestionTable.DATABASE_NAME_SECOND, null, QuestionTable.VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
            this.db=db;
        final  String CREATE_DATA_BASE="CREATE TABLE "+
                QuestionTable.TABLE_NAME+"("+
                QuestionTable._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                QuestionTable.COLUMN_QUESTION+" TEXT,"+
                QuestionTable.COLUMN_OPTION1+" TEXT,"+
                QuestionTable.COLUMN_OPTION2+" TEXT,"+
                QuestionTable.COLUMN_OPTION3+" TEXT,"+
                QuestionTable.COLUMN_ANSWER+" INTEGER"+
                ")";
        db.execSQL(CREATE_DATA_BASE);
       // Log.d("ccccc","end");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // Log.d("ccccc","upgradestatrt");

        try {
            db.execSQL("DROP TABLE IF EXISTS '"+QuestionTable.TABLE_NAME+"'");
            db.delete(QuestionTable.TABLE_NAME, null, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        onCreate(db);
        Log.d("ccccc","upgradeend");
    }
    public  void fillQuestionsToDb(){
        Question question;
        db=this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS '"+QuestionTable.TABLE_NAME+"'");
        onCreate(db);
        question=new Question(
                "3, 5, 11, 14, 17, 21","3","14","21",2);
        addToDataBase(question);
        question=new Question("8, 27, 64, 100, 125, 216, 343","100","125","8",1);
        addToDataBase(question);
        question=new Question("10, 25, 45, 54, 60, 75, 80","10","75","54",3);
        addToDataBase(question);
        question=new Question("6, 9, 15, 21, 24, 28, 30","21","28","none of these",2);
        addToDataBase(question);
        question=new Question("3,5,7,41,17,21","21","5","17",1);
        addToDataBase(question);
        question=new Question("2,80,46,54,67","80","54","67",3);
        addToDataBase(question);
        question=new Question("1.2,5.9,6.9,2,7.9","2","1.2","None",1);
        addToDataBase(question);
        question=new Question("16, 25, 36, 72, 144, 196, 225","72","16","36",1);
        addToDataBase(question);

    }
   private void addToDataBase(Question question){
       db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(QuestionTable.COLUMN_QUESTION,question.getQuestion());
        contentValues.put(QuestionTable.COLUMN_OPTION1,question.getOption1());
        contentValues.put(QuestionTable.COLUMN_OPTION2,question.getOption2());
        contentValues.put(QuestionTable.COLUMN_OPTION3,question.getOption3());
        contentValues.put(QuestionTable.COLUMN_ANSWER,question.getAnswer());
        db.insert(QuestionTable.TABLE_NAME,null,contentValues);
        db.close();
    }
    public List<Question> getAllQuestions(){
        db=getReadableDatabase();
        List<Question> questionslist=new ArrayList<>();
        Cursor cursor=db.rawQuery("SELECT * FROM "+QuestionTable.TABLE_NAME,null);
        if(cursor.moveToFirst()){
            do{
                Question question=new Question();
                question.setQuestion(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_QUESTION)));
                question.setOption1(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPTION1)));
                question.setOption2(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPTION2)));
                question.setOption3(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPTION3)));
                question.setAnswer(cursor.getInt(cursor.getColumnIndex(QuestionTable.COLUMN_ANSWER)));
                questionslist.add(question);
            }while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return  questionslist;
    }
}