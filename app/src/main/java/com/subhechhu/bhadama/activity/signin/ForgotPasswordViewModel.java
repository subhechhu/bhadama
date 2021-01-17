package com.subhechhu.bhadama.activity.signin;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.Map;


public class ForgotPasswordViewModel extends AndroidViewModel {
    ForgotPasswordRepository forgotPasswordRepository;

    public ForgotPasswordViewModel(Application application) {
        super(application);
        forgotPasswordRepository = new ForgotPasswordRepository();
    }

    public void makePostRequest(String url, Map<String, Object> u, int rc) {
        forgotPasswordRepository.makePostRequest(url, u, rc);
    }

    public void makePutRequest(String url, Map<String, String> u, int rc) {
        forgotPasswordRepository.makePutRequest(url, u, rc);
    }


    public LiveData<String> forgetPasswordResponse() {
        return forgotPasswordRepository.getForgotPasswordResponse();
    }

    public LiveData<String> verifyOTPResponse() {
        return forgotPasswordRepository.getOTPVerifyResponse();
    }

    public LiveData<String> newPinResponse() {
        return forgotPasswordRepository.getnewPinResponse();
    }
}
