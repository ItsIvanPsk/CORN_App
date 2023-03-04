package com.example.cornapp.presentation.login;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cornapp.MainActivity;
import com.example.cornapp.databinding.ActivityMainBinding;
import com.example.cornapp.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        setupListeners();
        setupObservers();

        ((MainActivity) requireActivity()).getBinding();

        return binding.getRoot();
    }

    private void setupListeners() {
        binding.loginUserSignup.setOnClickListener(view -> {

        });
        binding.loginUserLogin.setOnClickListener(view -> {
            viewModel.loginUser(
                    binding.loginUserEmail.getText().toString(),
                    binding.loginUserPassword.getText().toString()
            );
        });
    }

    private void setupObservers() {
        viewModel.getLoginInfo().observe(getViewLifecycleOwner(), msg -> {
            Log.d("5cos", msg);
        });
        viewModel.getLoginResult().observe(getViewLifecycleOwner(), result -> {
            Log.d("5cos", result.toString());
            Log.d("5cos", result.toString());
        });
    }

}