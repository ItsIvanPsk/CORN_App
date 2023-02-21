package com.example.cornapp.view.scan;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.cornapp.databinding.FragmentScanBinding;
import com.google.zxing.Result;

public class ScanFragment extends Fragment {
    private CodeScanner mCodeScanner;
    private static final String[] CAMERA_PERMISSION = new String[]{Manifest.permission.CAMERA};
    private static final int CAMERA_REQUEST_CODE = 10;
    private FragmentScanBinding binding;
    private ScanViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ScanViewModel.class);
    }

    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(
                getActivity(),
                CAMERA_PERMISSION,
                CAMERA_REQUEST_CODE
        );
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentScanBinding.inflate(inflater, container, false);
        mCodeScanner = new CodeScanner(getActivity(), binding.scannerView);

        requestPermission();
        if (hasCameraPermission()) {
            mCodeScanner.setDecodeCallback(new DecodeCallback() {
                @Override
                public void onDecoded(@NonNull final Result result) {
                    getActivity().runOnUiThread(
                            () -> {
                                Toast.makeText(getActivity(), result.getText(), Toast.LENGTH_SHORT).show();
                                viewModel.startPayment(result.getText());
                            }
                    );
                }
            });
            binding.scannerView.setOnClickListener(view -> mCodeScanner.startPreview());
        }
        setupObservers();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    public void setupObservers() {

        viewModel.getTransactionInfo().observe(this, new Observer<TransactionBo>() {
            @Override
            public void onChanged(TransactionBo transaction) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Vas a pagar la cantidad de " + String.valueOf(transaction.getAmount()) + " CORN");
                builder.setPositiveButton("Pay", (dialog, id) -> {
                    viewModel.finishPayment(true, transaction);
                });
                builder.setNegativeButton("Cancel", (dialog, id) -> {
                    viewModel.finishPayment(false, transaction);
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

}