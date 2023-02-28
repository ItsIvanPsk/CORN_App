package com.example.cornapp.presentation.payment;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cornapp.domain.SetupPaymentUseCase;
import com.example.cornapp.utils.JsonUtils;
import com.example.cornapp.utils.PersistanceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PaymentViewModel extends ViewModel {

    private MutableLiveData<String> qrToken = new MutableLiveData<>();

    public void setupPayment(String amount, Context context) {
        JSONObject json = new JSONObject();
        ArrayList<String> userData = JsonUtils.readDataFromFile(context, "users.json");
        Log.d("5cos", userData.get(2));
        try {
            json.put("user_id", userData.get(2));
            json.put("amount", amount);
            StringBuffer str = new SetupPaymentUseCase().setupPayment(json);
            JSONObject response = new JSONObject(str.toString());
            JSONObject tokenJson = response.getJSONObject("result");
            qrToken.setValue(
                    tokenJson.getString("transaction_token")
            );
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public LiveData<String> getPaymentToken() {
        return qrToken;
    }
}