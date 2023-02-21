package com.example.cornapp.view.payment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cornapp.domain.SetupPaymentUseCase;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentViewModel extends ViewModel {

    private MutableLiveData<String> qrToken = new MutableLiveData<>();

    public void setupPayment(String amount) {
        JSONObject json = new JSONObject();
        String user_id = "555";
        try {
            json.put("user_id", user_id);
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