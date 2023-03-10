package com.example.cornapp.domain.profile;

import com.example.cornapp.data.ApiWs;
import com.example.cornapp.utils.WebServiceConstants;

import org.json.JSONObject;

import java.io.IOException;

interface LogoutUserUseCaseInt{
    StringBuffer logoutUser(JSONObject json) throws IOException;
}

public class LogoutUserUseCase implements LogoutUserUseCaseInt {

    @Override
    public StringBuffer logoutUser(JSONObject json) throws IOException {
        return new ApiWs().sendPost(WebServiceConstants.api + "/logout", json);
    }

}