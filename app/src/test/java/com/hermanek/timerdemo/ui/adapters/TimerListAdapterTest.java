package com.hermanek.timerdemo.ui.adapters;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

/**
 * Created by jhermanek on 28.07.2021.
 */
@RunWith(JUnit4.class)
public class TimerListAdapterTest {

    @Test
    public void formatTime() {
        TimerListAdapter timerListAdapter = new TimerListAdapter(new ArrayList<>());

        Assert.assertEquals(timerListAdapter.formatTime(10000L), "00:10.00");
        Assert.assertEquals(timerListAdapter.formatTime(0L), "00:00.00");
        Assert.assertEquals(timerListAdapter.formatTime(10L), "00:00.10");
        Assert.assertEquals(timerListAdapter.formatTime(123456789L), "10:17:36.89");
        Assert.assertEquals(timerListAdapter.formatTime(100000L), "01:40.00");
        Assert.assertEquals(timerListAdapter.formatTime(1000000L), "16:40.00");
    }
}