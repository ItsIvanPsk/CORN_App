package com.example.cornapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cornapp.databinding.ActivityLoginBinding;
import com.example.cornapp.databinding.ActivitySignUpBinding;


public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

}