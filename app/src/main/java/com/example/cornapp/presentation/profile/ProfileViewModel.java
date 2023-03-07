package com.example.cornapp.presentation.profile;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cornapp.data.models.ApiDto;
import com.example.cornapp.data.models.UserBo;
import com.example.cornapp.domain.profile.GetSyncUserUseCase;
import com.example.cornapp.domain.profile.GetUserDataByTokenUseCase;
import com.example.cornapp.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<ApiDto> syncUp = new MutableLiveData<>();
    private MutableLiveData<UserBo> user = new MutableLiveData<>();

    public MutableLiveData<ApiDto> syncUser(){
        return syncUp;
    }

    public void updateUser(String _name, String _surname, String _phone, String _email, Context context) {
        JSONObject json = new JSONObject();
        try {
            json.put("phone", _phone);
            json.put("name", _name);
            json.put("surname", _surname);
            json.put("email", _email);
            json.put("password", "P@ssw0rd");
            StringBuffer str = new GetSyncUserUseCase().updateUserStatus(json);
            JSONObject _json = new JSONObject(str.toString());
            Log.d("5cos", _json.getString("code"));
            Log.d("5cos", _json.getString("status"));
            Log.d("5cos",  _json.getJSONObject("result").getString("message"));

            syncUp.setValue(
                    new ApiDto(
                            _json.get("status").toString(),
                            Integer.parseInt(_json.getString("code")),
                            _json.getJSONObject("result").getString("message")
                    )
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public LiveData<UserBo> getUserData() {
        return user;
    }

    public void getUserByToken(Context context, String token) {
        JSONObject json = new JSONObject();
        try {
            json.put("session_token", token);
            StringBuffer sb = new GetUserDataByTokenUseCase().getUserData(json);
            JSONObject apiResult = new JSONObject(sb.toString());
            Log.d("5cos", apiResult.toString());
            if (apiResult.get("code").toString().equals("200")) {
                JSONObject inJson = new JSONObject(apiResult.get("result").toString());
                JSONObject data = new JSONObject(inJson.get("data").toString());
                Log.d("5cos", data.toString());
                Log.d("5cos", data.get("email").toString());
                user.setValue(
                        new UserBo(
                            data.get("name").toString(),
                            data.get("surname").toString(),
                            data.get("email").toString(),
                            Integer.parseInt(data.get("phone").toString()),
                                Integer.parseInt(data.get("balance").toString())
                        )
                );
            }
        } catch (JSONException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}