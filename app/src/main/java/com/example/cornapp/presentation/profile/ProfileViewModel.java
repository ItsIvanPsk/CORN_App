package com.example.cornapp.presentation.profile;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cornapp.data.models.ApiDto;
import com.example.cornapp.domain.GetSyncUserUseCase;

import org.json.JSONObject;

import java.util.Map;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<ApiDto> syncUp = new MutableLiveData<>();

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
            json.put("password", "");
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
}