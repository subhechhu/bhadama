package com.subhechhu.bhadama.activity.addProperty;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.subhechhu.bhadama.util.APIRequest;

import java.util.Map;
import static com.subhechhu.bhadama.util.GetConstants.*;

public class AddPropertyRepository implements APIRequest.FromAPI {
    MutableLiveData<String> responseAddPropertyData;
    MutableLiveData<String> responsePostImage;;
    MutableLiveData<String> updatePropertyData;;

    public AddPropertyRepository() {
        responseAddPropertyData = new MutableLiveData<>();
        responsePostImage = new MutableLiveData<>();
        updatePropertyData = new MutableLiveData<>();
    }

    public void makeUpdateRequest(String url, Map<String, Object> params, int rc){
        new APIRequest(this).makePatchRequest(url,params, rc);
    }

    public void nakePostRequest(String url, Map<String, Object> params, int rc) {
        new APIRequest(this).makePostRequest(url, params, rc);
    }

    public void nakeGetRequest(String url, int rc) {
        new APIRequest(this).makeGetRequest(url, rc);
    }

    public void makeImageUploadRequest(String url, byte[] params, int rc) {
        new APIRequest(this).makeImageUploadRequest(url, params, rc);
    }

    public LiveData<String> getAddPropertyResponse() {
        return responseAddPropertyData;
    }

    public LiveData<String> getUpdateropertyResponse() {
        return updatePropertyData;
    }

    public LiveData<String> getImageUploadResponse() {
        return responsePostImage;
    }

    @Override
    public void getResponse(String data, int requestCode) {
        if (requestCode == VERIFYPROPERTY_REQUESTCODE) {
            responseAddPropertyData.setValue(data);
        }else if (requestCode == UPLOAD_IMAGE_REQUESTCODE) {
            responsePostImage.setValue(data);
        }else if(requestCode == UPDATE_PROPERTY_REQUESTCODE)
            updatePropertyData.setValue(data);
    }
}

















