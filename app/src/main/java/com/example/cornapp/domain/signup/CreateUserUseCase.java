package com.example.cornapp.domain.signup;

import com.example.cornapp.data.ApiWs;
import com.example.cornapp.utils.WebServiceConstants;

import org.json.JSONObject;

import java.io.IOException;

interface CreateUserUseCaseInt {
    StringBuffer createUser(JSONObject json) throws IOException;
}
public class CreateUserUseCase implements CreateUserUseCaseInt{
    @Override
    public StringBuffer createUser(JSONObject json) throws IOException {
        return new ApiWs().sendPost(WebServiceConstants.api + "/signup", json);
    }
}
