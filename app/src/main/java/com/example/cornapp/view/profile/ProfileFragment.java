package com.example.cornapp.view.profile;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cornapp.data.models.UserBo;
import com.example.cornapp.databinding.FragmentProfileBinding;
import com.example.cornapp.presentation.ApplicationViewModel;
import com.example.cornapp.presentation.profile.ProfileViewModel;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ProfileViewModel viewModel;
    private ApplicationViewModel appViewModel;

    private UserBo _user = new UserBo("", "", "", 999999999);

    private boolean userEditing = false;
    private boolean contactEditing = false;
    private boolean emailEditing = false;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        appViewModel = new ViewModelProvider(this).get(ApplicationViewModel.class);
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

    public void setupObservers() {
        viewModel.syncUser().observe(this, apiDto -> {
            AlertDialog alertDialog = showDialog(apiDto.getCode(), apiDto.getStatus(), apiDto.getResult());
            alertDialog.show();
            if (apiDto.getCode() == 200) {
                appViewModel.setupUser(_user);
            }
        });
    }

    public AlertDialog showDialog(int code, String msg, String message) {
        switch (code) {
            case 200 : {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Sync up");
                builder.setMessage(message);
                builder.setPositiveButton("Ok", (dialog, id) -> dialog.dismiss());
                return builder.create();
            }
            case 400 : {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Missing parameters");
                builder.setMessage(message);
                builder.setPositiveButton("Ok", (dialog, id) -> dialog.dismiss());
                return builder.create();
            }
            default : {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Unexpected Error");
                builder.setMessage("The app has a unespected error, please check your mobile connection and try again");
                builder.setPositiveButton("Ok", (dialog, id) -> dialog.dismiss());
                return builder.create();
            }
        }

    }

}