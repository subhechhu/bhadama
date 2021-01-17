package com.subhechhu.bhadama.activity.personalProperty;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.subhechhu.bhadama.util.APIRequest;
import com.subhechhu.bhadama.util.GetConstants;

import java.util.Map;

public class PersonalPropertyRepository implements APIRequest.FromAPI {
    MutableLiveData<String> responseGetPropertyListData;

    public PersonalPropertyRepository() {
        responseGetPropertyListData = new MutableLiveData<>();
    }

    public void makePostRequest(String url, Map<String, Object> params, int rc) {
        new APIRequest(this).makePostRequest(url, params, rc);
    }

    public void makeGetRequest(String url, int rc) {
        new APIRequest(this).makeGetRequest(url, rc);
    }

    public LiveData<String> getPropertyListResponse() {
        return responseGetPropertyListData;
    }

    @Override
    public void getResponse(String data, int requestCode) {
        if (requestCode == GetConstants.GET_PROPERTIES_REQUESTCODE) {
            responseGetPropertyListData.setValue(data);
        }
    }
}

















