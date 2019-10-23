package com.nuveq.sojibdemo.view.fragment;

import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.appdata.SharedPreferencesEnum;
import com.nuveq.sojibdemo.common.BaseFragment;
import com.nuveq.sojibdemo.databinding.FragmentAttendanceListBinding;
import com.nuveq.sojibdemo.datamodel.AttendDatePost;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.utils.MyThread;
import com.nuveq.sojibdemo.view.DashboardAdapter;
import com.nuveq.sojibdemo.viewmodel.Viewmodel;

public class AttendanceListFragment extends BaseFragment implements ServerResponseFailedCallback {
    FragmentAttendanceListBinding binding;

    MyThread thread;
    Viewmodel viewModel;

    @Override

    protected Integer layoutResourceId() {
        return R.layout.fragment_attendance_list;
    }

    @Override
    protected void initFragmentComponents() {
        binding = (FragmentAttendanceListBinding) getBinding();
        thread = new MyThread(getActivity());
        viewModel = ViewModelProviders.of(getActivity()).get(Viewmodel.class);
        viewModel.getAttendanceRepository().setCallbackListener(this);
        binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    protected void initFragmentFunctionality() {
        AttendDatePost post = new AttendDatePost();

        post.setEmpid(SharedPreferencesEnum.getInstance(getActivity()).getString(SharedPreferencesEnum.Key.USER_ID));
        post.setFromdate("10/20/2019");
        post.setTodate("10/20/2019");
        viewModel.getAttenDataList(post).observe(this, data -> {
            if (data != null) {
                DashboardAdapter adapter = new DashboardAdapter(getActivity(), data);
                binding.rv.setAdapter(adapter);

            }
        });
    }

    @Override
    protected void initFragmentListener() {

        binding.btnCheckIn.setOnClickListener(view -> {
            Runnable mRunnable;
            Handler mHandler = new Handler();
            mRunnable = new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "thread", Toast.LENGTH_SHORT).show();
                }
            };
            mHandler.postDelayed(mRunnable, 2 * 1000);

        });
    }


    @Override
    public void onDestroy() {
        thread.interrupt();
        super.onDestroy();
    }

    @Override
    public void onFailed(String msg) {

    }
}
