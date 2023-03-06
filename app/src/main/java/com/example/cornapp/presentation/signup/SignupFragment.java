package com.example.cornapp.presentation.signup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cornapp.LoginActivity;
import com.example.cornapp.MainActivity;
import com.example.cornapp.databinding.FragmentLoginBinding;
import com.example.cornapp.databinding.FragmentSignupBinding;
import com.example.cornapp.utils.PersistanceUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class SignupFragment extends Fragment {

    private FragmentSignupBinding binding;
    private SignupViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSignupBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(SignupViewModel.class);

        setupListeners();
        setupObservers();

        return binding.getRoot();
    }

    private void setupListeners() {
        binding.createUserCreate.setOnClickListener(view -> {
            viewModel.createUser(
                    binding.createUserName.getText().toString(),
                    binding.createUserSurname.getText().toString(),
                    binding.createUserMail.getText().toString(),
                    binding.createUserPhone.getText().toString(),
                    binding.createUserPassword.getText().toString()
            );
        });
    }

    private void setupObservers() {
        viewModel.getCreateUserResult().observe(getViewLifecycleOwner(), msg -> {
            Log.d("5cos", msg.toString());
            if (msg.getCode() == 200) {
                Toast.makeText(requireContext(), "L'usuari s'ha creat correctament.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            } else if (msg.getCode() == 400) {
                Toast.makeText(requireContext(), "L'usuari no s'ha creat.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}