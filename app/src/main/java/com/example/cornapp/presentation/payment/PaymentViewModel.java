package com.example.cornapp.presentation.payment;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cornapp.domain.payment.SetupPaymentUseCase;
import com.example.cornapp.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PaymentViewModel extends ViewModel {

    private MutableLiveData<String> qrToken = new MutableLiveData<>();

    public void setupPayment(String amount, Context context, String userToken) {
        JSONObject json = new JSONObject();
        try {
            json.put("session_token", userToken);
            json.put("amount", amount);
            StringBuffer api_result = new SetupPaymentUseCase().setupPayment(json);
            JSONObject response = new JSONObject(api_result.toString());
            if (response.getString("code").equals("200")) {
                JSONObject result = response.getJSONObject("result");
                Log.d("5cos", result.toString());
                qrToken.setValue(
                        result.getString("transaction_token")
                );
            }else {
                Log.d("5cos", response.getString("code"));
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public LiveData<String> getPaymentToken() {
        return qrToken;
    }
}