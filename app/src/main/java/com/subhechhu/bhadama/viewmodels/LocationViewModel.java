package com.subhechhu.bhadama.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.subhechhu.bhadama.model.LocationModel;
import com.subhechhu.bhadama.networking.LocationRepository;

import java.util.List;


public class LocationViewModel extends ViewModel {

    private MutableLiveData<List<LocationModel>> mutableLiveData;
    private LocationRepository locationRepository;

    public void init(String query){
        if (mutableLiveData != null){
            return;
        }
        locationRepository = LocationRepository.getInstance();
        mutableLiveData = locationRepository.getNews(query, "pk.e9c44f4c1263e101648dec5cba7ca4b3", "NP");

    }

    public LiveData<List<LocationModel>> getLocationRepository() {
        return mutableLiveData;
    }

}