package com.funwithandroid.javaquiz.dbhandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.funwithandroid.javaquiz.dbParams.DbVariables.QuestionTable;
import com.funwithandroid.javaquiz.Question;

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
        Question question;
        db=this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS '"+QuestionTable.TABLE_NAME+"'");
        onCreate(db);
        question=new Question(
                "It was Sunday on Jan 1, 2006. What was the day of the week Jan 1, 2010?","Sunday","Saturday","Friday",3);
        addToDataBase(question);
        question=new Question("What was the day of the week on 28th May, 2006?","Sunday","Saturday","Friday",1);
        addToDataBase(question);
        question=new Question("What was the day of the week on 17th June, 1998?","Monday","Tuesday","Wednesday",3);
        addToDataBase(question);
        question=new Question("Today is Monday. After 61 days, it will be:","Wednesday","Saturday","Tuesday",2);
        addToDataBase(question);
        question=new Question("How many days are there in x weeks x days?","7x2","8x","7",2);
        addToDataBase(question);
        question=new Question("The last day of a century cannot be","Tuesday","Wednesday","Monday",1);
        addToDataBase(question);
        question=new Question("The calendar for the year 2007 will be the same for the year:","2014","2018","2016",2);
        addToDataBase(question);
        question=new Question("Which of the following is not a leap year?","800","700","1200",2);
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