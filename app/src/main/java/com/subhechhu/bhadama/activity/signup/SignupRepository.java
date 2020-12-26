package com.subhechhu.bhadama.activity.signup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.subhechhu.bhadama.networking.APIRequest;

import java.util.Map;

public class SignupRepository implements APIRequest.FromAPI {
    MutableLiveData<String> responseSignupData;

    public SignupRepository() {
        responseSignupData = new MutableLiveData<>();
    }

    public void makeSignupRequest(String url, Map<String, String> params) {
        new APIRequest(this).makePostRequest(url, params);
    }

    public LiveData<String> getSignupResponse() {
        return responseSignupData;
    }

    @Override
    public void getResponse(String data) {
        responseSignupData.setValue(data);
    }
}

















