package com.subhechhu.bhadama.activity.personalProperty;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.Map;


public class PersonalPropertyViewModel extends AndroidViewModel {
    PersonalPropertyRepository personalPropertyRepository;

    public PersonalPropertyViewModel(Application application) {
        super(application);
        personalPropertyRepository = new PersonalPropertyRepository();
    }

    public void makeGetRequest(String url, int rc){
        personalPropertyRepository.makeGetRequest(url, rc);
    }

    public LiveData<String> getPropertyListResponse() {
        return personalPropertyRepository.getPropertyListResponse();
    }
}
