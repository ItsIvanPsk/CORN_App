package com.example.cornapp.presentation.historial;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cornapp.databinding.FragmentHistorialBinding;
import com.example.cornapp.utils.PersistanceUtils;

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
        binding.historialProgress.setVisibility(View.VISIBLE);
        Log.d("5cos", PersistanceUtils.session_token);
        binding.historialSaldo.setText("SALDO:  " + PersistanceUtils.saldo);
        viewModel.updateUserTransactions(requireContext(), PersistanceUtils.session_token);

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
            if (!transaction.isEmpty()) {
                adapter = new TransactionsAdapter(requireContext(), transaction, 1);
                binding.historialRecycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                binding.historialProgress.setVisibility(View.GONE);
            }
        });
    }

}