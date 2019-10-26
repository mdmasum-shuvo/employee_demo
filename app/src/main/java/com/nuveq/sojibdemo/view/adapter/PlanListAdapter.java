package com.nuveq.sojibdemo.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.databinding.ItemDetailsBinding;
import com.nuveq.sojibdemo.databinding.ItemPlanBinding;
import com.nuveq.sojibdemo.datamodel.attendance.Emp;
import com.nuveq.sojibdemo.datamodel.visitplan.Plan;
import com.nuveq.sojibdemo.utils.CommonUtils;

import java.util.List;

public class PlanListAdapter extends RecyclerView.Adapter<PlanListAdapter.ViewFilesHolder> {
    private Context context;
    private List<Plan> list;
    private LayoutInflater layoutInflater;
    public static final int REQUEST_UPDATE = 100;


    public PlanListAdapter(Context context, List<Plan> list) {
        this.context = context;
        this.list = list;

        layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public PlanListAdapter.ViewFilesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemPlanBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.item_plan, parent, false);
        return new PlanListAdapter.ViewFilesHolder(binding);


    }

    @Override
    public void onBindViewHolder(PlanListAdapter.ViewFilesHolder holder, int position) {
        Plan plan = new Plan();
        plan.setDate(CommonUtils.currentDate(list.get(position).getDate()));
        plan.setTime(CommonUtils.currentTime(list.get(position).getTime()));
        holder.binding.setModel(plan);
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

        private final ItemPlanBinding binding;

        public ViewFilesHolder(final ItemPlanBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}
