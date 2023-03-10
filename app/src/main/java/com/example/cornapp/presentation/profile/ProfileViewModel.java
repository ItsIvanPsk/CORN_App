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
import com.example.cornapp.domain.profile.LogoutUserUseCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ProfileViewModel extends ViewModel {

    private final MutableLiveData<ApiDto> syncUp = new MutableLiveData<>();
    private final MutableLiveData<UserBo> user = new MutableLiveData<>();
    private final MutableLiveData<ApiDto> logout = new MutableLiveData<>();

    public MutableLiveData<ApiDto> syncUser() { return syncUp; }
    public LiveData<ApiDto> getLogoutState() { return logout; }
    public LiveData<UserBo> getUserData() {
        return user;
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
                user.setValue(
                        new UserBo(
                                data.get("name").toString(),
                                data.get("surname").toString(),
                                data.get("email").toString(),
                                data.get("account_status").toString(),
                                Integer.parseInt(data.get("phone").toString()),
                                Integer.parseInt(data.get("balance").toString())
                        )
                );
            }
        } catch (JSONException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void userLogout(String session_token) {
        try {
            JSONObject json = new JSONObject();
            json.put("session_token", session_token);
            JSONObject result = new JSONObject(new LogoutUserUseCase().logoutUser(json).toString());
            if (result.get("code").equals("200")) {
                logout.setValue(
                        new ApiDto(
                                result.get("status").toString(),
                                Integer.parseInt(result.get("code").toString()),
                                "L'usuari ha tancat la sessió"
                        )
                );
            } else if (result.get("code").equals("404")) {
                logout.setValue(
                        new ApiDto(
                                result.get("status").toString(),
                                Integer.parseInt(result.get("code").toString()),
                                result.get("result").toString()
                        )
                );
            } else {
                logout.setValue(
                        new ApiDto(
                                "Error fatal",
                                Integer.parseInt(result.get("code").toString()),
                                result.get("result").toString()
                        )
                );
            }
        } catch (JSONException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}