package com.example.cornapp.domain.payment;

import com.example.cornapp.data.ApiWs;
import com.example.cornapp.utils.WebServiceConstants;

import org.json.JSONObject;

import java.io.IOException;

interface SetupPaymentUseCaseInt {
    StringBuffer setupPayment(JSONObject json);
}

public class SetupPaymentUseCase implements SetupPaymentUseCaseInt {

    @Override
    public StringBuffer setupPayment(JSONObject json) {
        try {
            return new ApiWs().sendPost(WebServiceConstants.api + "/setup_payment", json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}