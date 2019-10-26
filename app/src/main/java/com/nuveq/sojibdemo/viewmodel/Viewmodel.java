package com.nuveq.sojibdemo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.nuveq.sojibdemo.datamodel.AttendDatePost;
import com.nuveq.sojibdemo.datamodel.AttendancePost;
import com.nuveq.sojibdemo.datamodel.AuthenticationPost;
import com.nuveq.sojibdemo.datamodel.CheckOutPost;
import com.nuveq.sojibdemo.datamodel.LoginResponse;
import com.nuveq.sojibdemo.datamodel.TrackingPost;
import com.nuveq.sojibdemo.datamodel.visitplan.Plan;
import com.nuveq.sojibdemo.datamodel.visitplan.VisitPlanDataPost;
import com.nuveq.sojibdemo.datamodel.attendance.Emp;
import com.nuveq.sojibdemo.datamodel.global.CategoryDatum;
import com.nuveq.sojibdemo.datamodel.registration.Data;
import com.nuveq.sojibdemo.datamodel.registration.Registration;
import com.nuveq.sojibdemo.server_repository.AttendanceRepository;
import com.nuveq.sojibdemo.server_repository.AuthenticationRepository;
import com.nuveq.sojibdemo.server_repository.GlobalRepository;
import com.nuveq.sojibdemo.server_repository.VisitPlanRepository;

import java.util.List;

public class Viewmodel extends AndroidViewModel {


    private AuthenticationRepository repository;
    private AttendanceRepository attendanceRepository;
    private GlobalRepository globalRepository;
    private VisitPlanRepository visitRepository;

    public Viewmodel(@NonNull Application application) {
        super(application);
        repository = new AuthenticationRepository();
        attendanceRepository = new AttendanceRepository();
        globalRepository = new GlobalRepository();
        visitRepository = new VisitPlanRepository();
    }


    public MutableLiveData<String> getRegistrationResponse(Data jsonObject) {

        return repository.getRegistrationResponse(jsonObject);

    }

    public MutableLiveData<String> getMacData(String mac) {

        return repository.getMacData(mac);

    }

    public MutableLiveData<String> getCheckIn(AttendancePost mac) {

        return attendanceRepository.getCheckDataIn(mac);

    }

    public MutableLiveData<String> getCheckOut(CheckOutPost mac) {

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

    public GlobalRepository getGlobalRepository() {
        return globalRepository;
    }

    public VisitPlanRepository getplanRespository() {
        return visitRepository;
    }

    public MutableLiveData<List<Emp>> getAttenDataList(AttendDatePost post) {
        return attendanceRepository.getEmpttendanceDataList(post);
    }

    public MutableLiveData<List<Registration>> getBrachData() {
        return globalRepository.getBranchDataList();
    }


    public MutableLiveData<List<CategoryDatum>> getVisitCatData() {
        return globalRepository.getVisitCatDataList();
    }

    public MutableLiveData<List<com.nuveq.sojibdemo.datamodel.global.area.Emp>> getVisitAreaData(String post) {
        return globalRepository.getVisitAreaDataList(post);
    }

    public MutableLiveData<String> getVisitPlanData(VisitPlanDataPost post) {
        return visitRepository.getAddPlanResponse(post);
    }  public MutableLiveData<List<Plan>> getVisitPlanDataList(AttendDatePost post) {
        return visitRepository.getPlanDataList(post);
    }
}
