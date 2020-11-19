package com.batache.cars.model.adapter.divider

import android.content.Context
import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.batache.cars.R
import com.batache.cars.model.adapter.base.FullSizeModelWithHolder
import com.batache.cars.model.adapter.divider.DividerModel.DividerHolder

@EpoxyModelClass(layout = R.layout.layout_divider)
abstract class DividerModel : FullSizeModelWithHolder<DividerHolder?>() {

  @EpoxyAttribute
  var showResultsText = false

  override fun bind(holder: DividerHolder) {
    super.bind(holder)

    if (showResultsText) {
      holder.resultsTextTv?.visibility = View.VISIBLE
      holder.itemView?.setPadding(0, 0, 0, 0)
    } else {
      holder.resultsTextTv?.visibility = View.GONE
      holder.itemView?.context?.let { context ->
        holder.itemView?.setPadding(pxFromDp(context, 16f), 0, pxFromDp(context, 16f), 0)
      }
    }
  }

  private fun pxFromDp(context: Context, dp: Float): Int {
    return (dp * context.resources.displayMetrics.density).toInt()
  }

  inner class DividerHolder : EpoxyHolder() {
    var itemView: View? = null
    var resultsTextTv: TextView? = null

    override fun bindView(itemView: View) {
      this.itemView = itemView
      resultsTextTv = itemView.findViewById(R.id.tv_results)
    }
  }
}