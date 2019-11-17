package com.nuveq.sojibdemo.feature.visitplan;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.appdata.SharedPreferencesEnum;
import com.nuveq.sojibdemo.common.BaseFragment;
import com.nuveq.sojibdemo.databinding.FragmentPlanListBinding;
import com.nuveq.sojibdemo.datamodel.AttendDatePost;
import com.nuveq.sojibdemo.datamodel.visitplan.Plan;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.utils.CommonUtils;
import com.nuveq.sojibdemo.view.adapter.PlanListAdapter;
import com.nuveq.sojibdemo.viewmodel.Viewmodel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class VisitedFragment extends BaseFragment implements ServerResponseFailedCallback {
    private FragmentPlanListBinding binding;
    private Viewmodel viewModel;
    private Calendar calendar;
    private List<Plan> planList = new ArrayList<>();
    private PlanListAdapter adapter;
    private AlertDialog askIdDialog;

    @Override
    protected Integer layoutResourceId() {
        return R.layout.fragment_plan_list;
    }

    @Override
    protected void initFragmentComponents() {
        binding = (FragmentPlanListBinding) getBinding();
        binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewModel = ViewModelProviders.of(this).get(Viewmodel.class);
        viewModel.getplanRespository().setCallbackListener(this);
        adapter = new PlanListAdapter(getActivity(), planList);
        binding.rv.setAdapter(adapter);
        calendar = Calendar.getInstance();

    }

    @Override
    protected void initFragmentFunctionality() {
        callApi("2019/11/1", CommonUtils.currentDate());

    }

    @Override
    protected void initFragmentListener() {
        binding.btnFloatFilter.setOnClickListener(v -> {
            showAskIdDialog();
        });
    }

    private void callApi(String from, String to) {
        if (!planList.isEmpty()) {
            planList.clear();
        }

        AttendDatePost post = new AttendDatePost();
        post.setEmpid(String.valueOf(SharedPreferencesEnum.getInstance(getActivity()).getInt(SharedPreferencesEnum.Key.USER_ID)));
        post.setFromdate(from);
        post.setTodate(to);
        showProgressDialog();
        viewModel.getVisitedPlanDataList(post).observe(this, data -> {
            if (data != null) {
                hideProgressDialog();
                planList.addAll(data);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void showAskIdDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.date_filter_layout, null, false);
        builder.setView(view);
        final EditText etDateFrom = view.findViewById(R.id.etDateFrom);
        final EditText etDateTo = view.findViewById(R.id.etDateTo);
        Button btnSearch = view.findViewById(R.id.btnFilter);

        etDateFrom.setOnClickListener(v -> {
            CommonUtils.showDatePicker(getContext(), etDateFrom, calendar);
        });
        etDateTo.setOnClickListener(v -> {
            CommonUtils.showDatePicker(getContext(), etDateTo, calendar);
        });

        askIdDialog = builder.create();
        askIdDialog.show();
        //         //user="demo1254";
        //         //pass="demo#123";
        btnSearch.setOnClickListener(view1 -> {
            String fromDate = etDateFrom.getText().toString();
            String toDate = etDateTo.getText().toString();
            if (fromDate.equals("") || toDate.equals("")) {
                hideProgressDialog();
                CommonUtils.showCustomAlert(getActivity(), getString(R.string.failed), getString(R.string.user_empty), false);
                return;
            }

            askIdDialog.dismiss();
            callApi(fromDate, toDate);


        });
    }


    @Override
    public void onFailed(String msg) {
        hideProgressDialog();
        adapter.notifyDataSetChanged();
    }
}
