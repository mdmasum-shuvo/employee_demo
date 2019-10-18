package com.nuveq.sojibdemo.view.fragment;

import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.common.BaseFragment;
import com.nuveq.sojibdemo.databinding.FragmentAttendanceListBinding;
import com.nuveq.sojibdemo.utils.MyThread;

public class AttendanceListFragment extends BaseFragment {
    FragmentAttendanceListBinding binding;

    MyThread thread;

    @Override
    protected Integer layoutResourceId() {
        return R.layout.fragment_attendance_list;
    }

    @Override
    protected void initFragmentComponents() {
        binding = (FragmentAttendanceListBinding) getBinding();
        thread = new MyThread(getActivity());

    }

    @Override
    protected void initFragmentFunctionality() {

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
}
