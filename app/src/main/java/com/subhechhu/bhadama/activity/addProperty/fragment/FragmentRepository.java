package com.subhechhu.bhadama.activity.addProperty.fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;

public class FragmentRepository {
    MutableLiveData<JSONObject> responseFragmentOne = new MutableLiveData<>();
    MutableLiveData<JSONObject> responseFragmentTwo = new MutableLiveData<>();
    MutableLiveData<JSONObject> responseFragmentThree = new MutableLiveData<>();


    public void setFragmentOneData(JSONObject fragmentOneData) {
        responseFragmentOne.postValue(fragmentOneData);
    }

    public void setFragmentTwoData(JSONObject fragmentTwoData) {
        responseFragmentTwo.setValue(fragmentTwoData);
    }

    public void setFragmentThreeData(JSONObject fragmentThreeData) {
        responseFragmentThree.setValue(fragmentThreeData);
    }

    public LiveData<JSONObject> getFragmentOneData() {
        return responseFragmentOne;
    }

    public LiveData<JSONObject> getFragmentTwoData() {
        return responseFragmentTwo;
    }

    public LiveData<JSONObject> getFragmentThreeData() {
        return responseFragmentThree;
    }


}

















