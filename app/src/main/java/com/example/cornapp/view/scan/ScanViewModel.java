package com.example.cornapp.view.scan;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cornapp.domain.FinishPaymentUseCase;
import com.example.cornapp.domain.SetupPaymentUseCase;
import com.example.cornapp.domain.StartPaymentUseCase;

import org.json.JSONException;
import org.json.JSONObject;

public class ScanViewModel extends ViewModel {

    private MutableLiveData<TransactionBo> transaction = new MutableLiveData<>();

    public void startPayment(String token) {
        JSONObject json = new JSONObject();
        String user_id = "34";
        try {
            json.put("user_id", user_id);
            json.put("transaction_token", token);
            Log.d("5cos", token);
            StringBuffer str = new StartPaymentUseCase().startPayment(json);
            Log.d("5coscos", str.toString());
            JSONObject response = new JSONObject(str.toString());
            Log.d("5cos", response.toString());
            if (response.getString("status").equals("OK")){
                JSONObject result = new JSONObject(response.getString("result"));
                JSONObject res = new JSONObject(result.toString());
                JSONObject res2 = new JSONObject(res.toString());
                Log.d("5cos", "AQUI -> " + res2.getString("amount"));
                transaction.setValue(
                        new TransactionBo(
                                result.getString("message"),
                                user_id,
                                token,
                                Integer.parseInt(res2.getString("amount"))
                        )
                );
            } else if (response.getString("status").equals("ERROR")){
                Log.d("5cos", "Error");
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void finishPayment(Boolean accept, TransactionBo transaction) {
        JSONObject json = new JSONObject();
        try {
            json.put("user_id", transaction.getUserId());
            json.put("transaction_token", transaction.getTransactionToken());
            json.put("accept", accept);
            json.put("amount", transaction.getAmount());
            StringBuffer str = new FinishPaymentUseCase().finishPayment(json);
            JSONObject response = new JSONObject(str.toString());
            Log.d("5cos", response.toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    public LiveData<TransactionBo> getTransactionInfo() {
        return transaction;
    }
}