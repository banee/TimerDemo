package com.hermanek.timerdemo.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hermanek.timerdemo.R;

import java.util.List;

/**
 * Created by jhermanek on 23.07.2021.
 */

public class HistoryListAdapter extends TimerListAdapter {

    public HistoryListAdapter(List<Long> timeList) {
        super(timeList);
    }

    @Override
    public TimerListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.historylist_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TimerListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.timeItem.setText(formatTime(timeList.get(i)));
    }
}
