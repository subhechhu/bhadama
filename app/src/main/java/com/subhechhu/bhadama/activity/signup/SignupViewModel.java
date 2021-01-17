package com.subhechhu.bhadama.activity.signup;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.Map;


public class SignupViewModel extends AndroidViewModel {
    SignupRepository loginRepository;

    public SignupViewModel(Application application) {
        super(application);
        loginRepository = new SignupRepository();
    }

    public void getPostRequest(String url, Map<String, Object> u, int rc) {
        loginRepository.makeSignupRequest(url, u, rc);
    }

    public LiveData<String> signupResponse() {
        return loginRepository.getSignupResponse();
    }
}
