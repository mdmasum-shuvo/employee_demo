package com.nuveq.sojibdemo.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.databinding.ItemDetailsBinding;
import com.nuveq.sojibdemo.datamodel.attendance.Emp;
import com.nuveq.sojibdemo.feature.admin.datamodel.attendance.Result;
import com.nuveq.sojibdemo.utils.CommonUtils;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewFilesHolder> {
    private Context context;
    private List<Result> list;
    private LayoutInflater layoutInflater;
    public static final int REQUEST_UPDATE = 100;


    public AttendanceAdapter(Context context, List<Result> list) {
        this.context = context;
        this.list = list;

        layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public AttendanceAdapter.ViewFilesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemDetailsBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.item_details, parent, false);
        return new AttendanceAdapter.ViewFilesHolder(binding);


    }

    @Override
    public void onBindViewHolder(AttendanceAdapter.ViewFilesHolder holder, int position) {
        Result emp = new Result();
        emp.setCheckInTime("CHECK IN\n" + CommonUtils.currentTime(list.get(position).getCheckInTime()));
        emp.setCheckOutTime("CHECK OUT\n" + CommonUtils.currentTime(list.get(position).getCheckOutTime()));
        emp.setDate(CommonUtils.currentDate(list.get(position).getDate()));
        emp.setCheckInLocation(list.get(position).getCheckInLocation());
        emp.setCheckOutLocation(list.get(position).getCheckOutLocation());
        emp.setCheckInRemarks(list.get(position).getCheckInRemarks());
        emp.setCheckOutRemarks(list.get(position).getCheckOutRemarks());
        holder.binding.setModel(emp);
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public class ViewFilesHolder extends RecyclerView.ViewHolder {

        private final ItemDetailsBinding binding;

        public ViewFilesHolder(final ItemDetailsBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}
