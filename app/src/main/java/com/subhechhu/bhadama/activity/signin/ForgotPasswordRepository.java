package com.subhechhu.bhadama.activity.signin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.subhechhu.bhadama.util.APIRequest;
import com.subhechhu.bhadama.util.GetConstants;

import java.util.Map;

public class ForgotPasswordRepository implements APIRequest.FromAPI {
    MutableLiveData<String> responseForgotPasswordData;
    MutableLiveData<String> responseOTPVerifyData;
    MutableLiveData<String> responseNewPinData;

    public ForgotPasswordRepository() {
        responseForgotPasswordData = new MutableLiveData<>();
        responseOTPVerifyData = new MutableLiveData<>();
        responseNewPinData = new MutableLiveData<>();
    }

    public void makePostRequest(String url, Map<String, Object> params, int requestCode) {
        new APIRequest(this).makePostRequest(url, params, requestCode);
    }
    public void makePutRequest(String url, Map<String, String> u, int rc) {
        new APIRequest(this).makePutRequest(url, u, rc);
    }

    public LiveData<String> getForgotPasswordResponse() {
        return responseForgotPasswordData;
    }
    public LiveData<String> getOTPVerifyResponse() {
        return responseOTPVerifyData;
    }
    public LiveData<String> getnewPinResponse() {
        return responseNewPinData;
    }

    @Override
    public void getResponse(String data, int requestcode) {
        if (requestcode == GetConstants.FORGETPIN_REQUESTCODE)
            responseForgotPasswordData.setValue(data);
        else if(requestcode == GetConstants.VERIFYOTP_REQUESTCODE)
            responseOTPVerifyData.setValue(data);
        else if(requestcode == GetConstants.NEWPIN_REQUESTCODE)
            responseNewPinData.setValue(data);
    }
}

















