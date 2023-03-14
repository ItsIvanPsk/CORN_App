package com.example.cornapp.presentation.profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cornapp.LoginActivity;
import com.example.cornapp.R;
import com.example.cornapp.databinding.FragmentProfileBinding;
import com.example.cornapp.utils.PersistanceUtils;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ProfileViewModel viewModel;

    private Boolean isFabVisible = false;
    Animation rotateOpen;
    Animation rotateClose;
    Animation fromBottom;
    Animation toBottom;

    private boolean userEditing = false;
    private boolean contactEditing = false;
    private boolean emailEditing = false;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        setupProfileUI();
        setupListeners();
        setupObservers();
        rotateOpen = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(requireContext(), R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(requireContext(), R.anim.to_bottom_anim);

        viewModel.getUserByToken(requireContext(), PersistanceUtils.session_token);
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
            binding.profileContactTelfValue.setEnabled(contactEditing);
        });
        binding.profileEmailCardview.setOnClickListener(view -> {
            emailEditing = !emailEditing;
            binding.profileEmailEditValue.setEnabled(emailEditing);
        });
        binding.fab.setOnClickListener(view -> {
            if (isFabVisible) {
                setInvisible();
                Log.d("5cos", isFabVisible.toString());
            } else {
                setVisible();
                Log.d("5cos", isFabVisible.toString());
            }
        });
        binding.fabLogout.setOnClickListener(view -> {
            viewModel.userLogout(PersistanceUtils.session_token);
        });
        binding.fabRefresh.setOnClickListener(view -> {
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
        viewModel.syncUser().observe(getViewLifecycleOwner(), apiDto -> {
            AlertDialog alertDialog = showDialog(apiDto.getCode(), apiDto.getResult());
            alertDialog.show();
        });
        viewModel.getLogoutState().observe(getViewLifecycleOwner(), apiDto -> {
            AlertDialog alertDialog = showDialogLogout(apiDto.getCode(), apiDto.getResult(), "L'usuari s'ha desloguejat correctament");
            alertDialog.show();
        });
        viewModel.getUserData().observe(getViewLifecycleOwner(), UserBo -> {
            binding.profileUserNameValue.setText(UserBo.getName());
            binding.profileUserSurnameValue.setText(UserBo.getSurname());
            binding.profileContactTelfValue.setText(String.valueOf(UserBo.getPhone()));
            binding.profileEmailEditValue.setText(UserBo.getEmail());
            switch (UserBo.getStatus()) {
                case "ACCEPTAT":
                    binding.profileStatus.setImageResource(R.drawable.round_green);
                    break;
                case "REFUSAT":
                    binding.profileStatus.setColorFilter(R.drawable.round_red);
                    break;
                case "PER_VERIFICAR":
                    binding.profileStatus.setColorFilter(R.drawable.round_orange);
                    break;
                default:
                    binding.profileStatus.setColorFilter(R.drawable.round_blue);
                    break;
            }
        });
    }

    public AlertDialog showDialog(int code, String message) {
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
            default: {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Unexpected Error");
                builder.setMessage("The app has a unespected error, please check your mobile connection and try again");
                builder.setPositiveButton("Ok", (dialog, id) -> dialog.dismiss());
                return builder.create();
            }
        }
    }

    public AlertDialog showDialogLogout(int code, String msg, String message) {
        if (code == 200) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Logout");
            builder.setMessage(message);
            builder.setPositiveButton("Ok", (dialog, id) -> {
                PersistanceUtils.session_token = "-";
                SharedPreferences sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.remove("session_token");
                editor.apply();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                dialog.dismiss();
            });
            return builder.create();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Unexpected Error");
        builder.setMessage("The app has a unespected error, please check your mobile connection and try again");
        builder.setPositiveButton("Ok", (dialog, id) -> dialog.dismiss());
        return builder.create();
    }

    public void setVisible() {
        binding.fabRefresh.setVisibility(View.VISIBLE);
        binding.fabLogout.setVisibility(View.VISIBLE);
        isFabVisible = true;
        startAnimations(true);
    }

    public void setInvisible() {
        binding.fabRefresh.setVisibility(View.INVISIBLE);
        binding.fabLogout.setVisibility(View.INVISIBLE);
        isFabVisible = false;
        startAnimations(false);
    }

    public void startAnimations(Boolean clicked) {
        if (!clicked) {
            binding.fabRefresh.startAnimation(toBottom);
            binding.fabLogout.startAnimation(toBottom);
            binding.fab.startAnimation(rotateClose);
        } else {
            binding.fabRefresh.startAnimation(fromBottom);
            binding.fabLogout.startAnimation(fromBottom);
            binding.fab.startAnimation(rotateOpen);
        }
    }

}