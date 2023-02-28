package com.example.cornapp.presentation.profile;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cornapp.data.models.ApiDto;
import com.example.cornapp.data.models.UserBo;
import com.example.cornapp.domain.GetSyncUserUseCase;
import com.example.cornapp.utils.JsonUtils;

import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<ApiDto> syncUp = new MutableLiveData<>();
    private MutableLiveData<UserBo> userJson = new MutableLiveData<>();

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

            ArrayList<String> dataList = new ArrayList<>();
            dataList.add(_name);
            dataList.add(_surname);
            dataList.add(_phone);
            dataList.add(_email);
            JsonUtils.saveDataToFile(context, "users.json", dataList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void readUser(Context context) {
        ArrayList<String> retrievedDataList = JsonUtils.readDataFromFile(context, "users.json");
        UserBo user = new UserBo();
        Log.d("5cos", retrievedDataList.toString());
        if (retrievedDataList.size() != 0) {
            if (retrievedDataList.get(0).length() != 0) {
                user.setName(retrievedDataList.get(0));
                user.setSurname(retrievedDataList.get(1));
                user.setPhone(Integer.parseInt(retrievedDataList.get(2)));
                user.setEmail(retrievedDataList.get(3));
                userJson.setValue(user);
            }
        }
    }

    public LiveData<UserBo> getReadedUser() {
        return userJson;
    }

}