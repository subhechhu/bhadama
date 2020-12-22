package com.subhechhu.bhadama.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.subhechhu.bhadama.Model.LocationModel;
import com.subhechhu.bhadama.Networking.LocationRepository;

import java.util.List;


public class LocationViewModel extends ViewModel {

    private MutableLiveData<List<LocationModel>> mutableLiveData;
    private LocationRepository locationRepository;

    public void init(){
        if (mutableLiveData != null) {
            return;
        }
        locationRepository = LocationRepository.getInstance();
    }

    public void getLocation(String query){
        mutableLiveData = locationRepository.getNews(query, "pk.e9c44f4c1263e101648dec5cba7ca4b3", "NP");
    }


    public LiveData<List<LocationModel>> getLocationRepository() {
        return mutableLiveData;
    }

}
