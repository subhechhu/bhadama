package com.subhechhu.bhadama.activity.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.subhechhu.bhadama.util.APIRequest;

import java.util.Map;

public class SigninRepository implements APIRequest.FromAPI {
    MutableLiveData<String> responseSigninData;

    public SigninRepository() {
        responseSigninData = new MutableLiveData<>();
    }

    public void makeSigninRequest(String url, Map<String, String> params, int rc) {
        new APIRequest(this).makePostRequest(url, params, rc);
    }

    public LiveData<String> getSigninResponse() {
        return responseSigninData;
    }

    @Override
    public void getResponse(String data,int requestCode) {
        responseSigninData.setValue(data);
    }
}

















