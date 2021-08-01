package com.hermanek.timerdemo.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * Created by jhermanek on 21.07.2021.
 */

class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "com_hermanek_laps.db";
    public static final int DATABASE_VERSION = 1;

    private static final String CREATE_TIMER_TABLE = DBTimerAdapter.createTableSQL();
    private static final String DROP_TIMER_TABLE = DBTimerAdapter.dropTableSQL();


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TIMER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DROP_TIMER_TABLE);
        onCreate(db);
    }
}
