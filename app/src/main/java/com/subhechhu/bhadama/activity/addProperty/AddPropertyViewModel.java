package com.subhechhu.bhadama.activity.addProperty;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.Map;


public class AddPropertyViewModel extends AndroidViewModel {
    AddPropertyRepository addPropertyRepository;

    public AddPropertyViewModel(Application application) {
        super(application);
        addPropertyRepository = new AddPropertyRepository();
    }

    public void makePatchRequest(String url, Map<String, Object> u, int rc) {
        addPropertyRepository.makeUpdateRequest(url, u, rc);
    }

    public void makePostRequest(String url, Map<String, Object> u, int rc) {
        addPropertyRepository.nakePostRequest(url, u, rc);
    }

    public void makeGetRequest(String url, int rc) {
        addPropertyRepository.nakeGetRequest(url, rc);
    }

    public void makeImageUploadRequest(String url, byte[] u, int rc) {
        addPropertyRepository.makeImageUploadRequest(url, u, rc);
    }

    public LiveData<String> addPropertyResponse() {
        return addPropertyRepository.getAddPropertyResponse();
    }

    public LiveData<String> getImageUploadResponse() {
        return addPropertyRepository.getImageUploadResponse();
    }

    public LiveData<String> getPropertyUpdateResponse(){
        return addPropertyRepository.getUpdateropertyResponse();
    }
}
