package com.example.cornapp.domain.login;

import static com.example.cornapp.utils.WebServiceConstants.api;

import com.example.cornapp.data.ApiWs;
import com.example.cornapp.utils.WebServiceConstants;

import org.json.JSONObject;

import java.io.IOException;

interface LoginUserUseCaseInt{
    StringBuffer loginUser(JSONObject json) throws IOException;
}
public class LoginUserUseCase implements LoginUserUseCaseInt{
    @Override
    public StringBuffer loginUser(JSONObject json) throws IOException {
        return new ApiWs().sendPost(WebServiceConstants.api + "/login", json);
    }
}

