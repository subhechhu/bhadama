package com.subhechhu.bhadama.activity.location;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.subhechhu.bhadama.util.GetUrl;

import java.util.List;


public class LocationViewModel extends AndroidViewModel {
    LocationRepository locationRepository;

    public LocationViewModel(@NonNull Application application) {
        super(application);
        locationRepository = new LocationRepository();
    }

    public void makeGetRequest(String q) {
        locationRepository.makeLocationRequest(GetUrl.LOCATION_URL + q);
    }

    public LiveData<List<LocationModel>> getLocationRepository() {
        return locationRepository.getLocationResponseResponse();
    }
}
