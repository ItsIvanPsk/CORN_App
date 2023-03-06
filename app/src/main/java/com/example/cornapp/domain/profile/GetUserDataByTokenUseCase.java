package com.example.cornapp.domain.profile;

import static com.example.cornapp.utils.WebServiceConstants.api;

import com.example.cornapp.data.ApiWs;
import com.example.cornapp.utils.WebServiceConstants;

import org.json.JSONObject;

import java.io.IOException;

interface GetUserDataByTokenUseCaseInt{
    StringBuffer getUserData(JSONObject json) throws IOException;
}

public class GetUserDataByTokenUseCase implements GetUserDataByTokenUseCaseInt {

    @Override
    public StringBuffer getUserData(JSONObject json) throws IOException {
        return new ApiWs().sendPost(WebServiceConstants.api + "/user_data", json);
    }

}
