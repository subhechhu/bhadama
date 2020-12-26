package com.subhechhu.bhadama.activity.login;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.Map;


public class SigninViewModel extends AndroidViewModel {
    SigninRepository signinRepository;

    public SigninViewModel(Application application) {
        super(application);
        signinRepository = new SigninRepository();
    }

    public void makePostRequest(String url, Map<String, String> u) {
        signinRepository.makeSigninRequest(url, u);
    }

    public LiveData<String> signinResponse() {
        return signinRepository.getSigninResponse();
    }
}
