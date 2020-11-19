package com.batache.cars.model.adapter.table

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.batache.cars.R
import com.batache.cars.model.adapter.table.LeftTableItemModel.LeftTableItemModelHolder

@EpoxyModelClass(layout = R.layout.item_table_left_item)
abstract class LeftTableItemModel : EpoxyModelWithHolder<LeftTableItemModelHolder>() {

  @EpoxyAttribute
  var title: String? = null

  override fun bind(holder: LeftTableItemModelHolder) {
    super.bind(holder)
    holder.title?.text = title
  }

  inner class LeftTableItemModelHolder : EpoxyHolder() {
    var title: TextView? = null

    override fun bindView(itemView: View) {
      this.title = itemView as TextView
    }
  }
}