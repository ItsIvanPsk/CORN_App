package com.example.cornapp.presentation.historial;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cornapp.data.models.TransactionBo;
import com.example.cornapp.data.models.UserTransactionBo;
import com.example.cornapp.domain.historial.GetTransactionsUseCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HistorialViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<UserTransactionBo>> userTransactionList = new MutableLiveData<>();

    public LiveData<ArrayList<UserTransactionBo>> getUserTransactions() {
        return userTransactionList;
    }

    public void updateUserTransactions(Context context, String session_token) {

        JSONObject json = new JSONObject();
        ArrayList<UserTransactionBo> auxTransactions = new ArrayList<>();
        try {
            json.put("session_token", session_token);
            Log.d("5cos", json.toString());
            StringBuffer sb = new GetTransactionsUseCase().getTransactions(json);
            Log.d("5cos", sb.toString());

            JSONObject result = new JSONObject(sb.toString());
            JSONObject objResponse = new JSONObject(result.toString());
            JSONObject objResult = objResponse.getJSONObject("result");
            JSONArray transactions = objResult.getJSONArray("transactions");

            for (int transactionId = 0; transactionId < transactions.length(); transactionId++) {
                JSONObject innerTransaction = new JSONObject(transactions.get(transactionId).toString());
                auxTransactions.add(
                        new UserTransactionBo(
                                innerTransaction.get("originName").toString(),
                                innerTransaction.get("destinationName").toString(),
                                innerTransaction.get("timeSetup").toString(),
                                innerTransaction.get("timeAccepted").toString(),
                                Integer.parseInt(innerTransaction.get("origin").toString()),
                                Integer.parseInt(innerTransaction.get("destination").toString()),
                                Integer.parseInt(innerTransaction.get("amount").toString()),
                                Integer.parseInt(innerTransaction.get("accepted").toString())
                        )
                );
            }
            userTransactionList.setValue(auxTransactions);

        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }

    }
}