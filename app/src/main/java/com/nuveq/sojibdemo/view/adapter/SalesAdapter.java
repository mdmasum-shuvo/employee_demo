package com.nuveq.sojibdemo.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.databinding.ItemPlanBinding;
import com.nuveq.sojibdemo.databinding.ItemSalesBinding;
import com.nuveq.sojibdemo.datamodel.sales.Result;
import com.nuveq.sojibdemo.datamodel.visitplan.Plan;
import com.nuveq.sojibdemo.utils.CommonUtils;

import java.util.List;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.ViewFilesHolder> {
    private Context context;
    private List<Result> list;
    private LayoutInflater layoutInflater;
    public static final int REQUEST_UPDATE = 100;


    public SalesAdapter(Context context, List<Result> list) {
        this.context = context;
        this.list = list;

        layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public SalesAdapter.ViewFilesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemSalesBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.item_sales, parent, false);
        return new SalesAdapter.ViewFilesHolder(binding);


    }

    @Override
    public void onBindViewHolder(SalesAdapter.ViewFilesHolder holder, int position) {
        Result plan = new Result();
        try {
            plan.setAddress(list.get(position).getAddress());
            plan.setName(list.get(position).getName());
            plan.setPhone(list.get(position).getPhone());
            plan.setDate(CommonUtils.currentDate(list.get(position).getDate()));
            plan.setTime(CommonUtils.currentTime(list.get(position).getTime()));
            holder.binding.setModel(plan);
        } catch (NullPointerException e) {
        }

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

        private final ItemSalesBinding binding;

        public ViewFilesHolder(final ItemSalesBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}
