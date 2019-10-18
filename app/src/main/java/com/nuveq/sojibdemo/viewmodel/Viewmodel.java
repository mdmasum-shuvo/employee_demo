package com.nuveq.sojibdemo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.nuveq.sojibdemo.datamodel.MacResponse;
import com.nuveq.sojibdemo.datamodel.registration.Data;
import com.nuveq.sojibdemo.datamodel.registration.Registration;
import com.nuveq.sojibdemo.server_repository.AuthenticationRepository;

public class Viewmodel extends AndroidViewModel {


    private AuthenticationRepository repository;

    public Viewmodel(@NonNull Application application) {
        super(application);
        repository = new AuthenticationRepository();
    }


    public MutableLiveData<Boolean> getRegistrationResponse(Data jsonObject) {

        return repository.getRegistrationResponse(jsonObject);

    }

    public MutableLiveData<MacResponse> getMacData(String mac) {

        return repository.getMacData(mac);

    }

    public AuthenticationRepository getRepository(){
        return  repository;
    }
}
