package com.subhechhu.bhadama.activity.signin;

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

    public void makePostRequest(String url, Map<String, Object> u, int rc) {
        signinRepository.makeSigninRequest(url, u, rc);
    }

    public LiveData<String> signinResponse() {
        return signinRepository.getSigninResponse();
    }
}
