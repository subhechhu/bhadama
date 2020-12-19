package com.subhechhu.bhadama.networking;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.subhechhu.bhadama.model.LocationModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationRepository {

    private static LocationRepository locationRepository;
    MutableLiveData<List<LocationModel>> locationData;

    public static LocationRepository getInstance() {
        if (locationRepository == null) {
            locationRepository = new LocationRepository();
        }
        return locationRepository;
    }

    LocationApi locationApi;
    public LocationRepository() {
        locationApi = RetrofitService.cteateService(LocationApi.class);
        locationData = new MutableLiveData<>();
    }

    public MutableLiveData<List<LocationModel>> getNews(String source, String key, String countryCode) {
        locationApi.getLocationList(source, key, countryCode).enqueue(new Callback<List<LocationModel>>() {
            @Override
            public void onResponse(Call<List<LocationModel>> call,
                                   Response<List<LocationModel>> response) {
                if (response.isSuccessful()) {
                    Log.e("TAG","newsrepository success response: "+response.body());
                    locationData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<LocationModel>> call, Throwable t) {
                locationData.setValue(null);
            }
        });
        return locationData;
    }
}
