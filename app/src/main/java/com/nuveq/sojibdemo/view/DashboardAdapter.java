package com.nuveq.sojibdemo.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.databinding.ItemDetailsBinding;
import com.nuveq.sojibdemo.datamodel.attendance.Emp;
import com.nuveq.sojibdemo.utils.CommonUtils;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewFilesHolder> {
    private Context context;
    private List<Emp> list;
    private LayoutInflater layoutInflater;
    public static final int REQUEST_UPDATE = 100;


    public DashboardAdapter(Context context, List<Emp> list) {
        this.context = context;
        this.list = list;

        layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public DashboardAdapter.ViewFilesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemDetailsBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.item_details, parent, false);
        return new DashboardAdapter.ViewFilesHolder(binding);


    }

    @Override
    public void onBindViewHolder(DashboardAdapter.ViewFilesHolder holder, int position) {
        Emp emp = new Emp();
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
