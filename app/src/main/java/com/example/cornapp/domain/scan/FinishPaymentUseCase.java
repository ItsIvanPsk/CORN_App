package com.example.cornapp.domain.scan;

import com.example.cornapp.data.ApiWs;
import com.example.cornapp.utils.WebServiceConstants;

import org.json.JSONObject;

import java.io.IOException;

interface FinishPaymentUseCaseInt {
    StringBuffer finishPayment(JSONObject json);
}

public class FinishPaymentUseCase implements FinishPaymentUseCaseInt {

    @Override
    public StringBuffer finishPayment(JSONObject json) {
        try {
            return new ApiWs().sendPost(WebServiceConstants.api + "/finish_payment", json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
