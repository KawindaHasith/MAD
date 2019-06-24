package com.jayasundara.aifarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "aifarm.db";
    public static final String TABLE_NAME = "customers";
    public static final String COL1 = "Name";
    public static final String COL2 = "Address";
    public static final String COL3 = "ContactNo";
    public static final String COL4 = "Email";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE "+TABLE_NAME+" (Name TEXT, Address TEXT, ContactNo TEXT, Email TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean addData(String name, String address, String contactNo, String email){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1,name);
        contentValues.put(COL2,address);
        contentValues.put(COL3,contactNo);
        contentValues.put(COL4,email);

        long result = db.insert(TABLE_NAME,null,contentValues);

        //if data as inserted incorrectly it will return -1
        if(result==-1)
            return  false;
        else
            return  true;
    }

    public Cursor getListContents(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+ TABLE_NAME,null);
        return  data;
    }


}
