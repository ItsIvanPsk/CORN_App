package com.example.cornapp.presentation.historial;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cornapp.MainActivity;
import com.example.cornapp.data.models.TransactionBo;
import com.example.cornapp.data.models.UserTransactionBo;
import com.example.cornapp.databinding.FragmentHistorialBinding;
import com.example.cornapp.presentation.scan.ScanViewModel;
import com.example.cornapp.utils.PersistanceUtils;

import java.util.ArrayList;

public class HistorialFragment extends Fragment {

    private FragmentHistorialBinding binding;
    private HistorialViewModel viewModel;

    private TransactionsAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHistorialBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(HistorialViewModel.class);
        setupAdapter();
        setupListeners();
        setupObservers();

        SharedPreferences sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);
        String st = sharedPref.getString("session_token", "");
        viewModel.updateUserTransactions(requireContext(), st);

        return binding.getRoot();
    }

    private void setupListeners() {

    }

    private void setupAdapter() {
        adapter = new TransactionsAdapter();
        binding.historialRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.historialRecycler.setAdapter(adapter);
    }
    private void setupObservers() {
        viewModel.getUserTransactions().observe(getViewLifecycleOwner(), transaction -> {
            Log.d("5cos", transaction.toString());
            adapter = new TransactionsAdapter(requireContext(), transaction);
            binding.historialRecycler.setAdapter(adapter);
        });
    }

}