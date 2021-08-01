package com.hermanek.timerdemo.data.repositories;

import android.content.Context;

import com.hermanek.timerdemo.data.db.DBTimerAdapter;

import java.util.List;

/**
 * Created by jhermanek on 21.07.2021.
 */

public class TimerRepository {
    private final DBTimerAdapter dbTimerAdapter;
    private Long time;

    public TimerRepository(Context context) {
        dbTimerAdapter = new DBTimerAdapter(context);
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    /**
     * save current time to database
     */
    public void saveTime() {
        if (time != null) {
            dbTimerAdapter.addTimeRecord(time);
        }
    }

    /**
     * load last 20 rows from database
     */
    public List<Long> loadLast20Rows() {
        return dbTimerAdapter.loadLast20Rows();
    }
}
