package com.example.cornapp.domain.profile;

import com.example.cornapp.data.ApiWs;
import com.example.cornapp.utils.WebServiceConstants;

import org.json.JSONObject;
import java.io.IOException;

interface GetSyncUserUseCaseInt {
    StringBuffer updateUserStatus(JSONObject json);
}

public class GetSyncUserUseCase implements  GetSyncUserUseCaseInt{

    @Override
    public StringBuffer updateUserStatus(JSONObject json) {
        try {
            return new ApiWs().sendPost(WebServiceConstants.api + "/signup", json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}