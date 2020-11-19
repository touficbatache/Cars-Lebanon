package com.batache.cars.model.adapter.table

import android.view.View
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.batache.cars.R
import com.batache.cars.model.adapter.base.FullSizeModelWithHolder

@EpoxyModelClass(layout = R.layout.item_table_top_border_item)
abstract class TopTableBorderItemModel : FullSizeModelWithHolder<TopTableBorderItemModel.TopTableBorderItemHolder>() {
  class TopTableBorderItemHolder : EpoxyHolder() {
    override fun bindView(itemView: View) {
    }
  }
}