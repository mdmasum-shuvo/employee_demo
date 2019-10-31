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
import com.nuveq.sojibdemo.datamodel.global.branch.Result;
import com.nuveq.sojibdemo.datamodel.sales.SalesPost;
import com.nuveq.sojibdemo.datamodel.visitplan.AddVisitPost;
import com.nuveq.sojibdemo.datamodel.visitplan.Plan;
import com.nuveq.sojibdemo.datamodel.visitplan.VisitPlanDataPost;
import com.nuveq.sojibdemo.datamodel.attendance.Emp;
import com.nuveq.sojibdemo.datamodel.registration.Data;
import com.nuveq.sojibdemo.server_repository.AttendanceRepository;
import com.nuveq.sojibdemo.server_repository.AuthenticationRepository;
import com.nuveq.sojibdemo.server_repository.GlobalRepository;
import com.nuveq.sojibdemo.server_repository.SalesRepository;
import com.nuveq.sojibdemo.server_repository.VisitPlanRepository;

import java.util.List;

public class Viewmodel extends AndroidViewModel {


    private AuthenticationRepository repository;
    private AttendanceRepository attendanceRepository;
    private GlobalRepository globalRepository;
    private VisitPlanRepository visitRepository;
    private SalesRepository salesRepository;

    public Viewmodel(@NonNull Application application) {
        super(application);
        repository = new AuthenticationRepository();
        attendanceRepository = new AttendanceRepository();
        globalRepository = new GlobalRepository();
        visitRepository = new VisitPlanRepository();
        salesRepository = new SalesRepository();
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

    public SalesRepository getSalesRepository() {
        return salesRepository;
    }

    public MutableLiveData<List<Emp>> getAttenDataList(AttendDatePost post) {
        return attendanceRepository.getEmpttendanceDataList(post);
    }

    public MutableLiveData<List<Result>> getBrachData() {
        return globalRepository.getBranchDataList();
    }


    public MutableLiveData<List<com.nuveq.sojibdemo.datamodel.global.cat.Result>> getVisitCatData() {
        return globalRepository.getVisitCatDataList();
    }

    public MutableLiveData<List<com.nuveq.sojibdemo.datamodel.global.area.Result>> getVisitAreaData(String post) {
        return globalRepository.getVisitAreaDataList(post);
    }

    public MutableLiveData<List<com.nuveq.sojibdemo.datamodel.global.area.Result>> getDoctorAreaData() {
        return globalRepository.getDoctorDataList();
    }

    public MutableLiveData<String> getVisitPlanData(VisitPlanDataPost post) {
        return visitRepository.getAddPlanResponse(post);
    }

    public MutableLiveData<List<Plan>> getPendingPlanDataList(AttendDatePost post) {
        return visitRepository.getPendingPlanList(post);
    }

    public MutableLiveData<List<Plan>> getApprovedPlanDataList(AttendDatePost post) {
        return visitRepository.getApprovedPlanList(post);
    }

    public MutableLiveData<List<Plan>> getVisitedPlanDataList(AttendDatePost post) {
        return visitRepository.getVisitedPlanList(post);
    }

    public MutableLiveData<String> getSalesEntry(SalesPost post) {
        return salesRepository.getSalesEntryResponse(post);
    }


    public MutableLiveData<List<com.nuveq.sojibdemo.datamodel.sales.Result>> getSalesList(AttendDatePost post) {
        return salesRepository.getSalesList(post);
    }

    public MutableLiveData<String> addVisit(AddVisitPost post) {
        return visitRepository.getAddVisitResponse(post);
    }

    public MutableLiveData<List<com.nuveq.sojibdemo.datamodel.global.area.Result>> getShiftList() {
        return globalRepository.getShiftList();
    }
}
