package com.subhechhu.bhadama.activity.location;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.subhechhu.bhadama.util.APIRequest;

import java.lang.reflect.Type;
import java.util.List;

public class LocationRepository implements APIRequest.FromAPI {

    MutableLiveData<List<LocationModel>> responseLocationData;

    public LocationRepository() {
        responseLocationData = new MutableLiveData<>();
    }

    public void makeLocationRequest(String url) { //new
        new APIRequest(this).makeGetRequest(url);
    }

    public LiveData<List<LocationModel>> getLocationResponseResponse() {
        return responseLocationData;
    }

    @Override
    public void getResponse(String data) {
        Gson gson = new Gson();
        Type listOfMyClassObject = new TypeToken<List<LocationModel>>() {}.getType();
        List<LocationModel> locationModelList = gson.fromJson(data,listOfMyClassObject);
        responseLocationData.setValue(locationModelList);
    }
}
