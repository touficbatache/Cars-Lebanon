package com.batache.cars.model.adapter.out_of_order

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyRecyclerView
import com.batache.cars.R
import com.batache.cars.api.CarsResponse.Car
import com.batache.cars.model.adapter.base.FullSizeModelWithHolder
import com.batache.cars.model.adapter.out_of_order.OutOfOrderItemModel.OutOfOrderItemModelHolder
import com.batache.cars.ui.controller.OutOfOrderController

@EpoxyModelClass(layout = R.layout.item_out_of_order)
abstract class OutOfOrderItemModel : FullSizeModelWithHolder<OutOfOrderItemModelHolder?>() {

  @EpoxyAttribute
  var car: Car? = null

  override fun bind(holder: OutOfOrderItemModelHolder) {
    super.bind(holder)

    car?.let { car ->
      OutOfOrderController().let {
        holder.recyclerView?.setController(it)
        it.addTable(car)
      }
    }
  }

  inner class OutOfOrderItemModelHolder : EpoxyHolder() {
    var recyclerView: EpoxyRecyclerView? = null

    override fun bindView(itemView: View) {
      recyclerView = itemView.findViewById(R.id.recyclerview)
    }
  }
}