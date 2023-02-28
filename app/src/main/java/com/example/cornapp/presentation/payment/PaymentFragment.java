package com.example.cornapp.presentation.payment;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cornapp.databinding.FragmentPaymentBinding;
import com.example.cornapp.utils.JsonUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.ArrayList;
import java.util.Objects;

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
            ArrayList<String> userData = JsonUtils.readDataFromFile(requireContext(), "users.json");
            if (binding.paymentAmountValue.getText().toString().equals("")){
                showDialog(
                        "Invalid amount",
                        "You have entered a incorrect value on the amount field.\nPlease try again.",
                        "Okay",
                        ""
                );
            } else if (userData.get(2) == null || Objects.equals(userData.get(2), "")) {
                showDialog(
                        "Invalid amount",
                        "You do not have a user on the app, please go to the profile and sign up.\nPlease try again.",
                        "Okay",
                        ""
                );
            } else {
                confirmPayment("Payment", "Do you wanna setup the transaction for " + binding.paymentAmountValue.getText().toString() + " CORNs?", "Yes", "No");
            }
        });
    }

    public void setupObservers() {
        viewModel.getPaymentToken().observe(getViewLifecycleOwner(), token ->
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
        if (!Objects.equals(title, "")) {
            builder.setTitle(title);
        } else { builder.setTitle("CORN App"); }
        if (!Objects.equals(message, "")) {
            builder.setMessage(message);
        } else { builder.setTitle(""); }
        builder.setPositiveButton(firstOpt, (dialog, id) -> dialog.dismiss());
        builder.create().show();
    }

    private void confirmPayment(String title, String message, String firstOpt, String secondOpt) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(firstOpt, (dialog, id) -> viewModel.setupPayment(binding.paymentAmountValue.getText().toString(), requireContext()));
        builder.setNegativeButton(secondOpt, (dialog, id) -> {
            dialog.dismiss();
            Toast.makeText(requireContext(), "Se ha cancelado el pago", Toast.LENGTH_SHORT).show();
            binding.paymentAmountValue.setText("");
        });
        builder.create().show();
    }


}