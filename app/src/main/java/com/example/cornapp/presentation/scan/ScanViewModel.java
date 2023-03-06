package com.example.cornapp.presentation.scan;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cornapp.data.models.ApiDto;
import com.example.cornapp.data.models.TransactionBo;
import com.example.cornapp.domain.scan.FinishPaymentUseCase;
import com.example.cornapp.domain.scan.StartPaymentUseCase;
import com.example.cornapp.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ScanViewModel extends ViewModel {

    private final MutableLiveData<TransactionBo> transaction = new MutableLiveData<>();
    public LiveData<TransactionBo> getTransactionInfo() {
        return transaction;
    }

    private final MutableLiveData<ApiDto> apiResult = new MutableLiveData<>();

    public LiveData<ApiDto> getTransactionResult() {
        return apiResult;
    }


    public void startPayment(String token, Context context, String session_token) {
        try {
            JSONObject json = new JSONObject();
            json.put("session_token", session_token);
            json.put("transaction_token", token);
            StringBuffer api_result = new StartPaymentUseCase().startPayment(json);
            JSONObject response = new JSONObject(api_result.toString());
            Log.d("5cos", "START_PAYMENT: " + response.toString());
            if (response.getString("status").equals("OK")){
                JSONObject result = new JSONObject(response.getString("result"));
                Log.d("5cos", "START_PAYMENT: " + result.toString());
                JSONObject data = new JSONObject(result.toString());
                JSONObject jsonResult = new JSONObject(data.toString());
                transaction.setValue(
                        new TransactionBo(
                                result.getString("message"),
                                session_token,
                                token,
                                Integer.parseInt(jsonResult.getString("amount"))
                        )
                );
            } else if (response.getString("status").equals("ERROR")){
                Log.d("5cos", "Error");
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void finishPayment(Boolean accept, TransactionBo transaction, String session_token) {
        JSONObject json = new JSONObject();
        try {
            json.put("session_token", session_token);
            json.put("transaction_token", transaction.getTransactionToken());
            json.put("accept", accept);
            json.put("amount", transaction.getAmount());
            StringBuffer str = new FinishPaymentUseCase().finishPayment(json);
            Log.d("5cos", "FINISH_PAYMENT: " + str.toString());
            JSONObject res = new JSONObject(str.toString());
            Log.d("5cos", "FINISH_PAYMENT: " + res.toString());
            apiResult.setValue(
                    new ApiDto(
                            res.getString("status"),
                            Integer.parseInt(res.getString("code")),
                            res.getJSONObject("result").getString("message")
                    )
            );

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}