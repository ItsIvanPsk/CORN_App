package com.example.cornapp.domain.historial;

import com.example.cornapp.data.ApiWs;
import com.example.cornapp.utils.WebServiceConstants;

import org.json.JSONObject;

import java.io.IOException;

interface GetTransactionsUseCaseInt{
    StringBuffer getTransactions(JSONObject json) throws IOException;
}
public class GetTransactionsUseCase implements GetTransactionsUseCaseInt{
    @Override
    public StringBuffer getTransactions(JSONObject json) throws IOException {
        return new ApiWs().sendPost(WebServiceConstants.api + "/get_transactions", json);
    }
}

