package com.example.cornapp.domain;

import com.example.cornapp.data.ApiWs;

import org.json.JSONObject;

import java.io.IOException;

interface LoginUserUseCaseInt{
    StringBuffer loginUser(JSONObject json) throws IOException;
}
public class LoginUserUseCase implements LoginUserUseCaseInt{
    @Override
    public StringBuffer loginUser(JSONObject json) throws IOException {
        return new ApiWs().sendPost("/login", json);
    }
}

