package com.subhechhu.bhadama.activity.addProperty.fragment;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.Map;


public class FragmentViewModel extends AndroidViewModel {
    FragmentRepository fragmentRepository;

    public FragmentViewModel(Application application) {
        super(application);
        fragmentRepository = new FragmentRepository();
    }

    public void postFragmentOneData(JSONObject object) {
        fragmentRepository.setFragmentOneData(object);
    }

    public void postFragmentTwoData(JSONObject object) {
        fragmentRepository.setFragmentTwoData(object);
    }

    public void postFragmentThreeData(JSONObject object) {
        fragmentRepository.setFragmentThreeData(object);
    }

    public LiveData<JSONObject> fragmentOneResponse() {
        return fragmentRepository.getFragmentOneData();
    }
    public LiveData<JSONObject> fragmentTwoResponse() {
        return fragmentRepository.getFragmentTwoData();
    }
    public LiveData<JSONObject> fragmentThreeResponse() {
        return fragmentRepository.getFragmentThreeData();
    }
}
