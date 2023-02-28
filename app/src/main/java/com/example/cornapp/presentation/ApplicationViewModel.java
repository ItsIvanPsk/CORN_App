package com.example.cornapp.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cornapp.data.models.UserBo;

public class ApplicationViewModel extends ViewModel {

    private final MutableLiveData<UserBo> activeUser = new MutableLiveData<>();

    public void setupUser(UserBo userBo){
        activeUser.setValue(userBo);
    }

    public LiveData<UserBo> getActiveUser() {
        return activeUser;
    }

}
