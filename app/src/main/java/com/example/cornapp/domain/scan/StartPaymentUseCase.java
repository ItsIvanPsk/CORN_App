package com.example.cornapp.domain.scan;

import com.example.cornapp.data.ApiWs;
import com.example.cornapp.utils.WebServiceConstants;

import org.json.JSONObject;

import java.io.IOException;

interface StartPaymentUseCaseInt {
    StringBuffer startPayment(JSONObject json);
}

public class StartPaymentUseCase implements StartPaymentUseCaseInt {

    @Override
    public StringBuffer startPayment(JSONObject json) {
        try {
            return new ApiWs().sendPost(WebServiceConstants.api + "/start_payment", json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}