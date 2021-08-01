package com.hermanek.timerdemo.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hermanek.timerdemo.databinding.FragmentHistoryBinding;
import com.hermanek.timerdemo.ui.adapters.HistoryListAdapter;

import java.util.ArrayList;

/**
 * Created by jhermanek on 20.07.2021.
 */

public class HistoryFragment extends Fragment {

    private HistoryListAdapter historyListAdapter;
    private HistoryViewModel historyViewModel;
    private FragmentHistoryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        historyViewModel =
                new ViewModelProvider(this).get(HistoryViewModel.class);

        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        RecyclerView recyclerView = binding.lapHistoryListRecyclerView;
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        historyListAdapter = new HistoryListAdapter(new ArrayList<>());
        recyclerView.setAdapter(historyListAdapter);

        historyViewModel.getLapsMutableLiveData().observe(getViewLifecycleOwner(), times -> historyListAdapter.refreshItems(times));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}