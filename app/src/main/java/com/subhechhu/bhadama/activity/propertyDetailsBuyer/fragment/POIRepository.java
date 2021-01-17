package com.subhechhu.bhadama.activity.propertyDetailsBuyer.fragment;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.subhechhu.bhadama.util.APIRequest;

import java.lang.reflect.Type;
import java.util.List;

public class POIRepository implements APIRequest.FromAPI {
    private final String TAG = POIRepository.class.getSimpleName();

    MutableLiveData<List<POIModel>> responsePOI;

    public POIRepository() {
        responsePOI = new MutableLiveData<>();
    }

    public void makeLocationRequest(String url, int rc) { //new
        new APIRequest(this).makeGetRequest(url, rc);
    }

    public LiveData<List<POIModel>> getLocationResponseResponse() {
        return responsePOI;
    }

    @Override
    public void getResponse(String data, int requestCode) {
        Log.e(TAG, "data: "+data);
        Gson gson = new Gson();
        Type listOfMyClassObject = new TypeToken<List<POIModel>>() {
        }.getType();
        List<POIModel> locationModelList = gson.fromJson(data, listOfMyClassObject);
        responsePOI.setValue(locationModelList);
    }
}
