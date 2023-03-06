package com.example.cornapp.presentation.login;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cornapp.data.models.ApiDto;
import com.example.cornapp.domain.login.LoginUserUseCase;
import com.example.cornapp.domain.profile.GetUserDataByTokenUseCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<String> errMsg = new MutableLiveData<>();
    private final MutableLiveData<ApiDto> apiWs = new MutableLiveData<>();

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
                Log.d("5cos", new LoginUserUseCase().loginUser(userJson).toString());
                JSONObject response = new JSONObject(String.valueOf(new LoginUserUseCase().loginUser(userJson)));
                Log.d("5cos", "RESPONSE: " + response);

                apiWs.setValue(new ApiDto(
                        response.getString("status"),
                        Integer.parseInt(response.getString("code")),
                        response.getString("result")
                ));
            } catch (JSONException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void tryAutoLogin(String session_token) {
        if (!session_token.equals("")) {
            try {
                JSONObject json = new JSONObject();
                json.put("session_token", session_token);
                JSONObject response = new JSONObject(new GetUserDataByTokenUseCase().getUserData(json).toString());
                if (response.get("code").equals("200")) {
                    JSONObject data = new JSONObject(response.get("result").toString());
                    JSONObject userData = new JSONObject(data.get("data").toString());
                    Log.d("5cos", "USERDATA -> " + userData);
                    if (userData.get("name").toString().equals("")) {
                        apiWs.setValue(
                                new ApiDto(
                                        "Error",
                                        500,
                                        "The auto login failed."
                                )
                        );
                    } else {
                        apiWs.setValue(
                                new ApiDto(
                                        "Login",
                                        200,
                                        session_token
                                )
                        );
                    }
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
    }
}