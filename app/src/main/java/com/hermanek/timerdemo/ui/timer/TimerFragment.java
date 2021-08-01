package com.hermanek.timerdemo.ui.timer;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hermanek.timerdemo.R;
import com.hermanek.timerdemo.databinding.FragmentTimerBinding;
import com.hermanek.timerdemo.ui.adapters.TimerListAdapter;

import java.util.ArrayList;

/**
 * Created by jhermanek on 20.07.2021.
 */

public class TimerFragment extends Fragment {

    private TimerListAdapter timerListAdapter;
    private TimerViewModel timerViewModel;
    private FragmentTimerBinding binding;
    private ChronometerWithMillis chronometer;
    private long pauseOffset;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        timerViewModel =
                new ViewModelProvider(this).get(TimerViewModel.class);

        binding = FragmentTimerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        chronometer = binding.chronometer;
        Button btnStartStop = binding.btnStartStop;
        Button btnLap = binding.btnLap;
        Button btnReset = binding.btnReset;

        RecyclerView recyclerView = binding.lapListRecyclerView;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        timerListAdapter = new TimerListAdapter(new ArrayList<>());
        recyclerView.setAdapter(timerListAdapter);

        btnStartStop.setOnClickListener(view -> timerViewModel.startStopTimer());

        btnLap.setOnClickListener(view -> timerViewModel.lapTimer(SystemClock.elapsedRealtime() - chronometer.getBase()));

        btnReset.setOnClickListener(view -> timerViewModel.resetTimer());

        timerViewModel.getTimerStateMutableLiveData().observe(getViewLifecycleOwner(), timerState -> {
            handleChronometer(timerState);
            if (timerState == TimerState.STOPPED || timerState == TimerState.INIT) {
                btnStartStop.setText(getText(R.string.label_start));
            } else if (timerState == TimerState.RUNNING || timerState == TimerState.STARTED) {
                btnStartStop.setText(getText(R.string.label_stop));
            }
        });

        timerViewModel.getLastLapMutableLiveData().observe(getViewLifecycleOwner(), lastLappedTime -> {
            if (lastLappedTime != null) {
                timerListAdapter.addItem(lastLappedTime);
                recyclerView.smoothScrollToPosition(timerListAdapter.getItemCount());
            } else {
                // null means to remove all items
                timerListAdapter.removeItems();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        timerViewModel.stopTimer();
    }

    private void handleChronometer(TimerState state) {
        if (state == TimerState.INIT) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.stop();
        } else if (state == TimerState.STARTED) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
        } else if (state == TimerState.RUNNING) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
        } else if (state == TimerState.STOPPED) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
        }
    }
}