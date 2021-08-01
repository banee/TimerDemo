package com.hermanek.timerdemo.ui.history;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.hermanek.timerdemo.data.repositories.TimerRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by jhermanek on 20.07.2021.
 */

public class HistoryViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Long>> lapsMutableLiveData;


    public HistoryViewModel(@NonNull @NotNull Application application) {
        super(application);
        lapsMutableLiveData = new MutableLiveData<>();
        lapsMutableLiveData.setValue(new TimerRepository(getApplication()).loadLast20Rows());
    }

    public MutableLiveData<List<Long>> getLapsMutableLiveData() {
        return lapsMutableLiveData;
    }

}