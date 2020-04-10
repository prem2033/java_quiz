package com.funwithandroid.javaquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.funwithandroid.javaquiz.DbVariables.QuestionTable;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    public QuizDbHelper( Context context) {
        super(context, QuestionTable.DATABASE_NAME, null, QuestionTable.VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
            this.db=db;
        Log.d("ccccc","start");
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
        Log.d("ccccc","end");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("ccccc","upgradestatrt");

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
        Log.d("ccccc","fillttdb");
        db=this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS '"+QuestionTable.TABLE_NAME+"'");
        onCreate(db);
        Question q1=new Question("What is 2+2?","4","6","8",1);
        addToDataBase(q1);
        Question q2=new Question("what is 2+4?","4","6","8",2);
        addToDataBase(q2);
        Question q3=new Question("what is 2+6?","4","6","8",3);
        addToDataBase(q3);
        Question q4=new Question("what is 7+10","19","40","none of these",3);
        addToDataBase(q4);
        Question q5=new Question("what is 10+11","22","23","21",3);
        addToDataBase(q5);
        Question q6=new Question("Evaluate (2+2/2)","4","2","3",3);
        addToDataBase(q6);
        Question q7=new Question("Evaluate (2/2/2)","0.5","2.8","3.6",1);
        addToDataBase(q7);
        Question q8=new Question("1,5,21,85,?","325","341","423",2);
        addToDataBase(q8);

    }
    public   void addToDataBase(Question question){
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
        List<Question> questionslist=new ArrayList<Question>();
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
        return  questionslist;
    }
}