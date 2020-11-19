package com.batache.cars.model.adapter.base

import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelWithHolder

abstract class FullSizeModelWithHolder<T : EpoxyHolder?> : EpoxyModelWithHolder<T>() {
  override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
    return 2
  }
}