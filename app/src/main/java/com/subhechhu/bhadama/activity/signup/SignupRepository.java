package com.subhechhu.bhadama.activity.signup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.subhechhu.bhadama.util.APIRequest;

import java.util.Map;

public class SignupRepository implements APIRequest.FromAPI {
    MutableLiveData<String> responseSignupData;

    public SignupRepository() {
        responseSignupData = new MutableLiveData<>();
    }

    public void makeSignupRequest(String url, Map<String, String> params, int rc) {
        new APIRequest(this).makePostRequest(url, params, rc);
    }

    public LiveData<String> getSignupResponse() {
        return responseSignupData;
    }

    @Override
    public void getResponse(String data, int requestcode) {
        responseSignupData.setValue(data);
    }
}

















