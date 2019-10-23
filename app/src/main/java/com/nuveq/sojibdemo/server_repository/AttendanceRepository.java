package com.nuveq.sojibdemo.server_repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nuveq.sojibdemo.datamodel.AttendDatePost;
import com.nuveq.sojibdemo.datamodel.AttendancePost;
import com.nuveq.sojibdemo.datamodel.CheckOutPost;
import com.nuveq.sojibdemo.datamodel.TrackingPost;
import com.nuveq.sojibdemo.datamodel.attendance.AttendanceDataResponse;
import com.nuveq.sojibdemo.datamodel.attendance.Emp;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.utils.CommonUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceRepository {
    MutableLiveData<String> checkData;
    Gson gson = new Gson();
    private ServerResponseFailedCallback mListener;
    private MutableLiveData<List<Emp>> empttendanceDataList;

    public void setCallbackListener(ServerResponseFailedCallback mListener) {
        this.mListener = mListener;
    }

    public MutableLiveData<String> getCheckDataIn(AttendancePost data) {
        checkData = new MutableLiveData<>();
        String jsonString = gson.toJson(data);
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();

        CommonUtils.getApiService().postCheckIn(jsonObject).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        checkData.setValue(response.body());
                    } else {
                        if (mListener != null) {
                            mListener.onFailed(response.message());
                        }

                    }
                } else {
                    if (mListener != null) {
                        mListener.onFailed(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (mListener != null) {
                    mListener.onFailed(t.getMessage());
                }

            }
        });
        return checkData;

    }

    public MutableLiveData<List<Emp>> getEmpttendanceDataList(AttendDatePost post) {
        empttendanceDataList = new MutableLiveData<>();
        String jsonString = gson.toJson(post);
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
        CommonUtils.getApiService().getAttendanceData(jsonObject).enqueue(new Callback<AttendanceDataResponse>() {
            @Override
            public void onResponse(Call<AttendanceDataResponse> call, Response<AttendanceDataResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            empttendanceDataList.setValue(response.body().getEmp());
                        } else {
                            if (mListener != null) {
                                mListener.onFailed(response.body().getMessage());
                            }
                        }
                    } else {
                        if (mListener != null) {
                            mListener.onFailed(response.message());
                        }
                    }

                } else {
                    if (mListener != null) {
                        mListener.onFailed(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<AttendanceDataResponse> call, Throwable t) {
                if (mListener != null) {
                    mListener.onFailed("Something went wrong in server!");
                }
            }
        });

        return empttendanceDataList;

    }


    public MutableLiveData<String> getCheckDataOut(CheckOutPost data) {
        checkData = new MutableLiveData<>();
        String jsonString = gson.toJson(data);
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();

        CommonUtils.getApiService().postCheckOut(jsonObject).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        checkData.setValue(response.body());
                    } else {
                        if (mListener != null) {
                            mListener.onFailed(response.message());
                        }

                    }
                } else {
                    if (mListener != null) {
                        mListener.onFailed(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (mListener != null) {
                    mListener.onFailed(t.getMessage());
                }
            }
        });
        return checkData;

    }

    public MutableLiveData<String> getTrackData(TrackingPost data) {
        checkData = new MutableLiveData<>();
        String jsonString = gson.toJson(data);
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();

        CommonUtils.getApiService().postTracking(jsonObject).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        checkData.setValue(response.body());
                    } else {
                     /*   if (mListener != null) {
                            mListener.onFailed(response.message());
                        }*/

                    }
                } else {
                  /*  if (mListener != null) {
                        mListener.onFailed(response.message());
                    }*/
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
              /*  if (mListener != null) {
                    mListener.onFailed(t.getMessage());
                }*/
            }
        });
        return checkData;

    }

}
