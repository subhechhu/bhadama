package com.subhechhu.bhadama.activity.propertyDetailsSeller;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.subhechhu.bhadama.util.APIRequest;

import static com.subhechhu.bhadama.util.GetConstants.*;

public class PropertyDetailsRepository implements APIRequest.FromAPI {
    MutableLiveData<String> responseGetPropertyListData;

    public PropertyDetailsRepository() {
        responseGetPropertyListData = new MutableLiveData<>();
    }

    public void makeDeleteRequest(String url, int rc) {
        new APIRequest(this).makeDeleteRequest(url, rc);
    }

    public LiveData<String> getDeleteResponse() {
        return responseGetPropertyListData;
    }

    @Override
    public void getResponse(String data, int requestCode) {
        if (requestCode == DELETE_PROPERTY_REQUESTCODE) {
            responseGetPropertyListData.setValue(data);
        }
    }
}

















