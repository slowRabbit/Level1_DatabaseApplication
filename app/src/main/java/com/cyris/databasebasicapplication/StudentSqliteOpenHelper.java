package com.cyris.databasebasicapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cyris on 22/5/17.
 */

public class StudentSqliteOpenHelper extends SQLiteOpenHelper {

    public static final String STUDENT_DATABASE="STUDENT_DATABASE";
    public static final String STUDENT_TABLE="STUDENT_TABLE";
    public static final String COL_1_ID="ID";
    public static final String COL_2_STUDENT_NAME="STUDENT_NAME";
    public static final String COL_3_STUDENT_SURNAME="STUDENT_SURNAME";
    public static final String COL_4_STUDENT_MARKS="STUDENT_MARKS";


    public StudentSqliteOpenHelper(Context context) {
        super(context, STUDENT_DATABASE, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+STUDENT_TABLE + " ("+COL_1_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_2_STUDENT_NAME
        +" TEXT, "+COL_3_STUDENT_SURNAME+" TEXT, "+COL_4_STUDENT_MARKS+ " INTEGER )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+STUDENT_TABLE);
        onCreate(db);

    }

    public boolean addData(String name, String surname, String marks)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(COL_2_STUDENT_NAME, name);
        cv.put(COL_3_STUDENT_SURNAME, surname);
        cv.put(COL_4_STUDENT_MARKS, marks);

        long result=db.insert(STUDENT_TABLE, null, cv);
        if(result==-1)
            return false;
        else
            return  true;

    }

    public Cursor readData()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * from "+STUDENT_TABLE, null);
        return  cursor;
    }

}
