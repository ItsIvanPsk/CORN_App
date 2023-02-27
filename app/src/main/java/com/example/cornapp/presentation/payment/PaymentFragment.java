package com.example.cornapp.presentation.payment;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cornapp.databinding.FragmentPaymentBinding;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class PaymentFragment extends Fragment {

    private FragmentPaymentBinding binding;
    private PaymentViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPaymentBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(PaymentViewModel.class);
        setupListeners();
        setupObservers();
        return binding.getRoot();
    }

    public void setupListeners(){
        binding.paymentSetupPayment.setOnClickListener(view -> {
            if (binding.paymentAmountValue.getText().toString().equals("")){
                Log.d("5cos", "Está vacio");
                showDialog(
                        "Invalid amount",
                        "You have entered a incorrect value on the amount field.\nPlease try again.",
                        "Okay",
                        ""
                );
            } else {
                Log.d("5cos", binding.paymentAmountValue.getText().toString());
                viewModel.setupPayment(binding.paymentAmountValue.getText().toString());
            }
        });
    }

    public void setupObservers() {
        viewModel.getPaymentToken().observe(this, token ->
                binding.paymentQr.setImageBitmap(
                    generateQRCode(token)
                )
        );
    }

    public Bitmap generateQRCode(String text) {
        int width = 800;
        int height = 800;
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, null);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private void showDialog(String title, String message, String firstOpt, String secondOpt) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(firstOpt, (dialog, id) -> dialog.dismiss());
    }

}