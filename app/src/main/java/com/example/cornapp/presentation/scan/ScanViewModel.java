package com.example.cornapp.presentation.scan;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cornapp.data.models.ApiDto;
import com.example.cornapp.data.models.TransactionBo;
import com.example.cornapp.domain.FinishPaymentUseCase;
import com.example.cornapp.domain.StartPaymentUseCase;
import com.example.cornapp.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ScanViewModel extends ViewModel {

    private final MutableLiveData<TransactionBo> transaction = new MutableLiveData<>();
    private final MutableLiveData<ApiDto> apiResult = new MutableLiveData<>();

    public void startPayment(String token, Context context) {
        JSONObject json = new JSONObject();
        ArrayList<String> userData = JsonUtils.readDataFromFile(context, "users.json");
        Log.d("5cos", userData.get(2));
        try {
            json.put("user_id", userData.get(2));
            json.put("transaction_token", token);
            StringBuffer str = new StartPaymentUseCase().startPayment(json);
            JSONObject response = new JSONObject(str.toString());
            if (response.getString("status").equals("OK")){
                JSONObject result = new JSONObject(response.getString("result"));
                JSONObject res = new JSONObject(result.toString());
                JSONObject res2 = new JSONObject(res.toString());
                transaction.setValue(
                        new TransactionBo(
                                result.getString("message"),
                                userData.get(2),
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
            Log.d("5cos", "Enter finish payment");
            Log.d("5cos", "HERE" + transaction.getUserId());
            json.put("user_id", transaction.getUserId());
            json.put("transaction_token", transaction.getTransactionToken());
            json.put("accept", accept);
            json.put("amount", transaction.getAmount());
            StringBuffer str = new FinishPaymentUseCase().finishPayment(json);
            Log.d("5cos", str.toString());
            JSONObject res = new JSONObject(str.toString());
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

    public LiveData<TransactionBo> getTransactionInfo() {
        return transaction;
    }

    public LiveData<ApiDto> getTransactionResult() {
        return apiResult;
    }

    public void setTransactionResult(ApiDto transactionResult) {
        this.apiResult.setValue(transactionResult);
    }

}