package com.example.cornapp.presentation.login;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cornapp.data.models.ApiDto;
import com.example.cornapp.domain.login.LoginUserUseCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<String> errMsg = new MutableLiveData<>();
    private MutableLiveData<ApiDto> apiWs = new MutableLiveData<>();

    public LiveData<String> getLoginInfo() {
        return errMsg;
    }

    public LiveData<ApiDto> getLoginResult() {
        return apiWs;
    }

    public void loginUser(String email, String password) {
        if (email.equals("")) {
            errMsg.setValue("Error: El email està buit.");
        } else if (password.equals("")) {
            errMsg.setValue("Error: El email està buit.");
        } else if (password.length() < 8) {
            errMsg.setValue("Error: La contrasenya no es válida");
        } else {
            try {
                JSONObject userJson = new JSONObject();
                userJson.put("email", email);
                userJson.put("password", password);
                JSONObject response = new JSONObject(String.valueOf(new LoginUserUseCase().loginUser(userJson)));
                Log.d("5cos", response.toString());
                ApiDto apiResponse = new ApiDto(
                        response.getString("status"),
                        Integer.parseInt(response.getString("code")),
                        response.getString("result")
                );
                apiWs.setValue(apiResponse);
            } catch (JSONException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}