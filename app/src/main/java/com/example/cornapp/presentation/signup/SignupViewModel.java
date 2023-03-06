package com.example.cornapp.presentation.signup;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cornapp.data.models.ApiDto;
import com.example.cornapp.domain.login.LoginUserUseCase;
import com.example.cornapp.domain.signup.CreateUserUseCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class SignupViewModel extends ViewModel {

    private final MutableLiveData<ApiDto> creationStatus = new MutableLiveData<>();

    public LiveData<ApiDto> getCreateUserResult() {
        return creationStatus;
    }

    public void createUser(String name, String surname, String email, String phone, String password) {
        if (name.equals("") | surname.equals("") | email.equals("") | phone.equals("") | password.equals("") ) {
            Log.d("5cos", "Invalid");
        } else {
            Log.d("5cos", "Valid");
            try {
                JSONObject json = new JSONObject();
                json.put("name", name);
                json.put("surname", surname);
                json.put("email", email);
                json.put("phone", phone);
                json.put("password", password);
                JSONObject jsonObject = new JSONObject(new CreateUserUseCase().createUser(json).toString());
                Log.d("5cos", jsonObject.toString());
            } catch (JSONException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}