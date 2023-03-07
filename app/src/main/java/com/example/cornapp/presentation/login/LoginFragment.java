package com.example.cornapp.presentation.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cornapp.MainActivity;
import com.example.cornapp.SignUpActivity;
import com.example.cornapp.databinding.FragmentLoginBinding;
import com.example.cornapp.utils.PersistanceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        setupListeners();
        setupObservers();

        SharedPreferences sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);
        String st = sharedPref.getString("session_token", "");
        viewModel.tryAutoLogin(st);
        Log.d("5cos", st);

        return binding.getRoot();
    }

    private void setupListeners() {
        binding.loginUserSignup.setOnClickListener(view -> {

        });
        binding.loginUserLogin.setOnClickListener(view -> {
            if (binding.loginUserEmail.getText().toString().equals("")) {
                showDialog("Inici de sessió", "El email que has introduït está buit.", "Okay", "");
            } else if (binding.loginUserPassword.getText().toString().equals("")) {
                showDialog("Inici de sessió", "La contraseña que has introduït está buida.", "Okay", "");
            } else {
                viewModel.loginUser(
                        binding.loginUserEmail.getText().toString(),
                        binding.loginUserPassword.getText().toString()
                );
            }
        });
        binding.createAccount.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), SignUpActivity.class));
        });
    }

    private void setupObservers() {
        viewModel.getLoginResult().observe(getViewLifecycleOwner(), result -> {
            if (result.getCode() == 200) {
                try {
                    JSONObject json = new JSONObject(result.getResult().toString());
                    Log.d("5cos", "O: " + json.get("session_token").toString());
                    PersistanceUtils.session_token = json.get("session_token").toString();
                    SharedPreferences sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.remove("session_token");
                    editor.putString("session_token", json.get("session_token").toString());
                    editor.apply();
                    Toast.makeText(requireContext(), "Auto login succesfull!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void showDialog(String title, String message, String firstOpt, String secondOpt) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (!Objects.equals(title, "")) {
            builder.setTitle(title);
        } else { builder.setTitle("CORN App"); }
        if (!Objects.equals(message, "")) {
            builder.setMessage(message);
        } else { builder.setTitle(""); }
        builder.setPositiveButton(firstOpt, (dialog, id) -> dialog.dismiss());
        builder.create().show();
    }

}