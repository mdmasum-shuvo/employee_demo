package com.nuveq.sojibdemo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.nuveq.sojibdemo.datamodel.AttendancePost;
import com.nuveq.sojibdemo.datamodel.AuthenticationPost;
import com.nuveq.sojibdemo.datamodel.LoginResponse;
import com.nuveq.sojibdemo.datamodel.MacResponse;
import com.nuveq.sojibdemo.datamodel.TrackingPost;
import com.nuveq.sojibdemo.datamodel.registration.Data;
import com.nuveq.sojibdemo.datamodel.registration.Registration;
import com.nuveq.sojibdemo.server_repository.AttendanceRepository;
import com.nuveq.sojibdemo.server_repository.AuthenticationRepository;

public class Viewmodel extends AndroidViewModel {


    private AuthenticationRepository repository;
    private AttendanceRepository attendanceRepository;

    public Viewmodel(@NonNull Application application) {
        super(application);
        repository = new AuthenticationRepository();
        attendanceRepository = new AttendanceRepository();
    }


    public MutableLiveData<Boolean> getRegistrationResponse(Data jsonObject) {

        return repository.getRegistrationResponse(jsonObject);

    }

    public MutableLiveData<String> getMacData(String mac) {

        return repository.getMacData(mac);

    }

    public MutableLiveData<String> getCheckIn(AttendancePost mac) {

        return attendanceRepository.getCheckDataIn(mac);

    }

    public MutableLiveData<String> getCheckOut(AttendancePost mac) {

        return attendanceRepository.getCheckDataOut(mac);

    }

    public MutableLiveData<String> getTracking(TrackingPost data) {
        return attendanceRepository.getTrackData(data);

    }

    public MutableLiveData<LoginResponse> getLoginResponse(AuthenticationPost object) {

        return repository.getLoginData(object);

    }

    public AuthenticationRepository getRepository() {
        return repository;
    }

    public AttendanceRepository getAttendanceRepository() {
        return attendanceRepository;
    }
}
