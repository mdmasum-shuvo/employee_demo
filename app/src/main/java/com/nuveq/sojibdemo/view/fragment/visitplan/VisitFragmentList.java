package com.nuveq.sojibdemo.view.fragment.visitplan;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.common.BaseFragment;
import com.nuveq.sojibdemo.databinding.FragmentVisitFragmentListBinding;
import com.nuveq.sojibdemo.view.fragment.AddSalesFragment;

import java.util.ArrayList;
import java.util.List;

public class VisitFragmentList extends BaseFragment {
    FragmentVisitFragmentListBinding binding;
    VisitPendingFragment visitPendingFragment;
    VisitApprovedFragment visitApprovedFragment;
    VisitedFragment visitedFragment;

    @Override
    protected Integer layoutResourceId() {
        return R.layout.fragment_visit_fragment_list;
    }

    @Override
    protected void initFragmentComponents() {
        binding = (FragmentVisitFragmentListBinding) getBinding();
        visitPendingFragment = new VisitPendingFragment();
        visitApprovedFragment = new VisitApprovedFragment();
        visitedFragment = new VisitedFragment();
        setupViewPager(binding.viewpager);
        binding.viewpager.setOffscreenPageLimit(0);
        binding.tab.setupWithViewPager(binding.viewpager);
    }

    @Override
    protected void initFragmentFunctionality() {

    }

    @Override
    protected void initFragmentListener() {

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(visitPendingFragment, "Pending");
        adapter.addFragment(visitApprovedFragment, "Approved");
        adapter.addFragment(visitedFragment, "Visited");

        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
