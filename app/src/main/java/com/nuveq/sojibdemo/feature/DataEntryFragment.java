package com.nuveq.sojibdemo.feature;


import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.common.BaseFragment;
import com.nuveq.sojibdemo.databinding.DataEntryLayoutBinding;


public class DataEntryFragment extends BaseFragment {

    private DataEntryLayoutBinding binding;
    private String location = null;
    private int areaItemPosition = -1;
    private String[] areaList;
    private Integer[] areaIdList;

    @Override
    protected Integer layoutResourceId() {
        return R.layout.data_entry_layout;
    }

    @Override
    protected void initFragmentComponents() {
        binding = (DataEntryLayoutBinding) getBinding();

    }

    @Override
    protected void initFragmentFunctionality() {

    }

    @Override
    protected void initFragmentListener() {

    }


}
