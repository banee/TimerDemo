package com.hermanek.timerdemo.ui.timer;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.hermanek.timerdemo.data.repositories.TimerRepository;

import org.jetbrains.annotations.NotNull;

/**
 * Created by jhermanek on 20.07.2021.
 */

public class TimerViewModel extends AndroidViewModel {

    private final MutableLiveData<TimerState> timerStateMutableLiveData;
    private final MutableLiveData<Long> lastLapMutableLiveData;


    public TimerViewModel(@NonNull @NotNull Application application) {
        super(application);
        timerStateMutableLiveData = new MutableLiveData<>();
        timerStateMutableLiveData.setValue(TimerState.INIT);
        lastLapMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<TimerState> getTimerStateMutableLiveData() {
        return timerStateMutableLiveData;
    }

    public MutableLiveData<Long> getLastLapMutableLiveData() {
        return lastLapMutableLiveData;
    }

    public void startStopTimer() {
        if (timerStateMutableLiveData.getValue() == TimerState.INIT) {
            timerStateMutableLiveData.setValue(TimerState.STARTED);
        } else if (timerStateMutableLiveData.getValue() == TimerState.STARTED) {
            timerStateMutableLiveData.setValue(TimerState.STOPPED);
        } else if (timerStateMutableLiveData.getValue() == TimerState.STOPPED) {
            timerStateMutableLiveData.setValue(TimerState.RUNNING);
        } else if (timerStateMutableLiveData.getValue() == TimerState.RUNNING) {
            timerStateMutableLiveData.setValue(TimerState.STOPPED);
        }
    }

    public void stopTimer() {
        if (timerStateMutableLiveData.getValue() == TimerState.STARTED ||
                timerStateMutableLiveData.getValue() == TimerState.RUNNING) {
            timerStateMutableLiveData.setValue(TimerState.STOPPED);
        }
    }

    public void resetTimer() {
        timerStateMutableLiveData.setValue(TimerState.INIT);
        lastLapMutableLiveData.setValue(null);
    }

    public void lapTimer(long time) {
        if (timerStateMutableLiveData.getValue() == TimerState.RUNNING ||
                timerStateMutableLiveData.getValue() == TimerState.STARTED) {
            lastLapMutableLiveData.setValue(time);
            TimerRepository repository = new TimerRepository(getApplication());
            repository.setTime(time);
            repository.saveTime();
        }
        // otherwise do not log laps
    }

}