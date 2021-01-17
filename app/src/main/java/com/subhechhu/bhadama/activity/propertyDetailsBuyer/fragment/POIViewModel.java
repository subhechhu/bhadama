package com.subhechhu.bhadama.activity.propertyDetailsBuyer.fragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


public class POIViewModel extends AndroidViewModel {
    POIRepository poiRepository;

    public POIViewModel(@NonNull Application application) {
        super(application);
        poiRepository = new POIRepository();
    }

    public void makeGetRequest(String q, int rc) {
        poiRepository.makeLocationRequest(q, rc);
    }

    public LiveData<List<POIModel>> getPOIRepository() {
        return poiRepository.getLocationResponseResponse();
    }
}
