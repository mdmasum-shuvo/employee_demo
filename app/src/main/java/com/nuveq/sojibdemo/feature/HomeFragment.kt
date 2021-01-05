package com.nuveq.sojibdemo.feature

import com.nuveq.sojibdemo.R
import com.nuveq.sojibdemo.common.BaseFragment
import com.nuveq.sojibdemo.common.FakeDataList.getFakeVocDataList
import com.nuveq.sojibdemo.databinding.HomeFragmentBinding
import com.nuveq.sojibdemo.feature.FakeAdapter.Interaction
import com.nuveq.sojibdemo.view.activity.DataEntryActivity

class HomeFragment : BaseFragment(), Interaction {
    private var binding: HomeFragmentBinding? = null
    private var adapter: FakeAdapter? = null
    override fun layoutResourceId(): Int {
        return R.layout.home_fragment
    }

    override fun initFragmentComponents() {
        binding = getBinding() as HomeFragmentBinding
    }

    override fun initFragmentFunctionality() {
        adapter = FakeAdapter(this)
        adapter!!.submitList(getFakeVocDataList())
        binding!!.adapter = adapter
    }

    override fun initFragmentListener() {}
    override fun onItemSelected(position: Int) {

        startActivity(activity!!,DataEntryActivity::class.java,false)
    }
}