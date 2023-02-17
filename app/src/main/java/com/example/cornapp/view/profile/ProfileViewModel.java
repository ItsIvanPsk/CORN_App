package com.example.cornapp.view.profile;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cornapp.data.models.ApiDto;
import com.example.cornapp.domain.GetSyncUserUseCase;

import org.json.JSONObject;

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
            syncUp.setValue(
                    new ApiDto(
                            _json.get("status").toString(),
                            200, // Integer.parseInt(_json.get("code").toString())
                            new JSONObject() // _json.get("result").toString()
                    )
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}