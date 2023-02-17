package com.example.cornapp.view.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.cornapp.R;
import com.example.cornapp.data.models.ApiDto;
import com.example.cornapp.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ProfileViewModel viewModel;

    private boolean userEditing = false;
    private boolean contactEditing = false;
    private boolean emailEditing = false;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        setupProfileUI();
        setupListeners();
        setupObservers();
        return binding.getRoot();
    }

    private void setupProfileUI() {
        binding.profileUserNameValue.setEnabled(false);
        binding.profileUserSurnameValue.setEnabled(false);
        binding.profileEmailEditValue.setEnabled(false);
        binding.profileContactTelfValue.setEnabled(false);
    }

    public void setupListeners(){
        binding.profileUserEdit.setOnClickListener(view -> {
            userEditing = !userEditing;
            binding.profileUserNameValue.setEnabled(userEditing);
            binding.profileUserSurnameValue.setEnabled(userEditing);
        });
        binding.profileContactEdit.setOnClickListener(view -> {
            contactEditing = !contactEditing;
            binding.profileContactTelfValue.setEnabled(userEditing);
        });
        binding.profileEmailCardview.setOnClickListener(view -> {
            emailEditing = !emailEditing;
            binding.profileEmailEditValue.setEnabled(userEditing);
        });
        binding.fab.setOnClickListener(view -> {
            viewModel.updateUser(
                    binding.profileUserNameValue.getText().toString(),
                    binding.profileUserSurnameValue.getText().toString(),
                    binding.profileContactTelfValue.getText().toString(),
                    binding.profileEmailEditValue.getText().toString(),
                    getContext()
            );
        });
    }

    public void setupObservers(){
        viewModel.syncUser().observe(this, new Observer<ApiDto>() {
            @Override
            public void onChanged(ApiDto apiDto) {
                AlertDialog alertDialog = showDialog(apiDto.getCode(), apiDto.getStatus());
                alertDialog.show();
            }
        });
    }

    public AlertDialog showDialog(int code, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(code);
        builder.setMessage(code + ": " + msg);
        builder.setPositiveButton("Ok", (dialog, id) -> dialog.dismiss());return  builder.create();
    }

}