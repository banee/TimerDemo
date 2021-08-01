package com.hermanek.timerdemo.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hermanek.timerdemo.R;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by jhermanek on 21.07.2021.
 */
public class TimerListAdapter extends RecyclerView.Adapter<TimerListAdapter.ViewHolder> {
    protected List<Long> timeList;

    public TimerListAdapter(List<Long> timeList) {
        this.timeList = timeList;
    }

    @Override
    public TimerListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.timerlist_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TimerListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.position.setText(new StringBuilder().append(i + 1).append("."));
        viewHolder.timeItem.setText(formatTime(timeList.get(i)));
        if (timeList.size() > 1) {
            viewHolder.timeDiff.setText(new StringBuilder().append("+")
                    .append(formatTime(timeList.get(i) - timeList.get(i - 1))));
        } else {
            viewHolder.timeDiff.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return timeList.size();
    }

    public void refreshItems(List<Long> items) {
        timeList = items;
        notifyDataSetChanged();
    }

    public void addItem(Long item) {
        timeList.add(item);
        notifyItemInserted(timeList.size() - 1);
    }

    public void removeItems() {
        timeList.clear();
        notifyDataSetChanged();
    }

    /**
     * format time to HH:mm:ss.SS
     */
    protected String formatTime(Long durationInMillis) {
        StringBuilder sb = new StringBuilder();
        if (durationInMillis != null) {
            try {
                DecimalFormat df = new DecimalFormat("00");

                int millis = (int) (durationInMillis % 100);
                long second = (durationInMillis / 1000) % 60;
                long minute = (durationInMillis / (1000 * 60)) % 60;
                long hour = (durationInMillis / (1000 * 60 * 60)) % 24;

                if (hour > 0) {
                    sb.append(df.format(hour)).append(":");
                }
                sb.append(df.format(minute)).append(":");
                sb.append(df.format(second)).append(".");
                sb.append(df.format(millis));
            } catch (Exception e) {
                sb = new StringBuilder();
                sb.append("invalid time");
            }
        } else {
            sb.append("invalid time");
        }

        return sb.toString();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected final TextView timeItem;
        private final TextView position;
        private final TextView timeDiff;

        ViewHolder(View view) {
            super(view);
            position = view.findViewById(R.id.position);
            timeItem = view.findViewById(R.id.timeItem);
            timeDiff = view.findViewById(R.id.timeDiff);
        }
    }
}