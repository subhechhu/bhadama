package com.subhechhu.bhadama.activity.propertyDetailsSeller;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


public class PropertyDetailsViewModel extends AndroidViewModel {
    PropertyDetailsRepository propertyDetailsRepository;

    public PropertyDetailsViewModel(Application application) {
        super(application);
        propertyDetailsRepository = new PropertyDetailsRepository();
    }

    public void makeDeleteRequest(String url, int rc) {
        propertyDetailsRepository.makeDeleteRequest(url, rc);
    }

    public LiveData<String> getDeleteResponse() {
        return propertyDetailsRepository.getDeleteResponse();
    }
}
