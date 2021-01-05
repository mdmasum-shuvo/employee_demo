package com.nuveq.sojibdemo.common

import com.nuveq.sojibdemo.model.FakeData
import java.util.*

object FakeDataList {
    private val fakeList = ArrayList<FakeData>()

   open fun getFakeVocDataList(): ArrayList<FakeData> {
        for (i in 1..5) {
            fakeList.add(FakeData(0, null, null, null, null))
        }
        return fakeList
    }
}