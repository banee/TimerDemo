package com.hermanek.timerdemo.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhermanek on 22.07.2021.
 */

public class DBTimerAdapter {

    private static final String TABLE_NAME = "laps";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CREATED = "created";
    private static final String COLUMN_TIME = "time";

    private final DBHelper dbHelper;
    private SQLiteDatabase db;

    public DBTimerAdapter(Context context) {
        dbHelper = new DBHelper(context);
    }

    public static String createTableSQL() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CREATED + " INTEGER, " +
                COLUMN_TIME + " INTEGER);";
    }

    public static String dropTableSQL() {
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    /**
     * Add time record to database
     *
     * @param time given time
     */
    public void addTimeRecord(Long time) {
        try {
            open();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_CREATED, System.currentTimeMillis());
            cv.put(COLUMN_TIME, time);
            long result = db.insert(TABLE_NAME, null, cv);
            if (result == -1) {
                // TODO log failed save to database
            }
        } catch (Exception e) {
            // TODO log ex during database save
        } finally {
            close();
        }
    }


    /**
     * Load last 20 rows
     *
     * @return last 20 rows
     */
    public List<Long> loadLast20Rows() {
        List<Long> lastRows = new ArrayList<>();
        try {
            open();
            String orderByClause = COLUMN_ID + " DESC LIMIT 20";
            Cursor c = db.query(TABLE_NAME, null, null, null, null, null, orderByClause);
            if (c.moveToFirst()) {
                do {
                    // TODO might parse as object if needed
                    Integer id = c.getInt(0);
                    Long created = c.getLong(1);
                    Long time = c.getLong(2);
                    lastRows.add(time);
                } while (c.moveToNext());
            }
            c.close();
        } catch (Exception e) {
            // TODO log ex during database load
        } finally {
            close();
        }
        return lastRows;
    }

    private void open() {
        db = dbHelper.getWritableDatabase();

    }

    private void close() {
        db.close();
        dbHelper.close();
    }

}
