package com.example.cornapp.presentation.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cornapp.LoginActivity;
import com.example.cornapp.MainActivity;
import com.example.cornapp.databinding.ActivityMainBinding;
import com.example.cornapp.databinding.FragmentLoginBinding;
import com.example.cornapp.utils.PersistanceUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        setupListeners();
        setupObservers();


        return binding.getRoot();
    }

    private void setupListeners() {
        binding.loginUserSignup.setOnClickListener(view -> {

        });
        binding.loginUserLogin.setOnClickListener(view -> {
            if (binding.loginUserEmail.getText().toString().equals("")) {

            } else if (binding.loginUserPassword.getText().toString().equals("")) {

            } else {
                viewModel.loginUser(
                        binding.loginUserEmail.getText().toString(),
                        binding.loginUserPassword.getText().toString()
                );
            }
        });
    }

    private void setupObservers() {
        viewModel.getLoginInfo().observe(getViewLifecycleOwner(), msg -> {
            Log.d("5cos", msg);
        });
        viewModel.getLoginResult().observe(getViewLifecycleOwner(), result -> {
            Log.d("5cos", result.toString());
            if (result.getCode() == 200) {
                try {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    JSONObject json = new JSONObject(result.getResult());
                    PersistanceUtils.session_token = json.get("session_token").toString();
                    Log.d("5cos", "Persitoken: " + PersistanceUtils.session_token);
                    startActivity(intent);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}