package com.funwithandroid.javaquiz.dbParams;
import android.provider.BaseColumns;

public final class DbVariables{
    public DbVariables() {}
    public static class QuestionTable implements BaseColumns {
        public static final String DATABASE_NAME="quizdatabase.db";
        public static final String DATABASE_NAME_SECOND="second.db";
        public static final String DATABASE_NAME_THIRD="third.db";
        public static final String DATABASE_NAME_SYNONYMS="synonyms.db";
        public static final int VERSION=1;
        public static final String TABLE_NAME="quiztable";
        public static final String  COLUMN_QUESTION="question";
        public static final String  COLUMN_OPTION1="option1";
        public static final String  COLUMN_OPTION2="option2";
        public static final String  COLUMN_OPTION3="option3";
        public static final String COLUMN_OPTION4="option4";
        public static final String COLUMN_ANSWER="answer";
    }

}
