package com.batache.cars.model.adapter.main

import android.view.View
import android.widget.CheckBox
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.batache.cars.R
import com.batache.cars.model.adapter.base.FullSizeModelWithHolder
import com.batache.cars.ui.controller.CarsController.CarsListener
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

@EpoxyModelClass(layout = R.layout.layout_search_owner_name)
abstract class OwnerNameSearchModel : FullSizeModelWithHolder<OwnerNameSearchModel.OwnerNameSearchHolder?>() {

  @EpoxyAttribute
  var listener: CarsListener? = null

  private var allowInaccurateResults = false

  override fun bind(holder: OwnerNameSearchHolder) {
    super.bind(holder)

    resetViewsState(holder)

    holder.allowInaccurateResultsChbx?.setOnCheckedChangeListener { compoundButton, isChecked -> allowInaccurateResults = isChecked }
    holder.allowInaccurateResultsChbx?.isChecked = false

    holder.submitBtn?.setOnClickListener {
      disableViews(holder)
      listener?.onSearchByOwnerName(holder.firstNameEt?.text.toString(), holder.lastNameEt?.text.toString(), allowInaccurateResults)
    }
  }

  private fun resetViewsState(holder: OwnerNameSearchHolder) {
    holder.firstNameEt?.setText("")
    holder.lastNameEt?.setText("")
    holder.allowInaccurateResultsChbx?.isChecked = false
    holder.allowInaccurateResultsChbx?.isEnabled = true
    holder.submitBtn?.isEnabled = true
  }

  private fun disableViews(holder: OwnerNameSearchHolder) {
    holder.allowInaccurateResultsChbx?.isEnabled = false
    holder.submitBtn?.isEnabled = false
  }

  inner class OwnerNameSearchHolder : EpoxyHolder() {
    var firstNameEt: TextInputEditText? = null
    var lastNameEt: TextInputEditText? = null
    var allowInaccurateResultsChbx: CheckBox? = null
    var submitBtn: MaterialButton? = null

    override fun bindView(itemView: View) {
      firstNameEt = itemView.findViewById(R.id.et_first_name)
      lastNameEt = itemView.findViewById(R.id.et_last_name)
      allowInaccurateResultsChbx = itemView.findViewById(R.id.chbx_allow_inaccurate_results)
      submitBtn = itemView.findViewById(R.id.btn_submit)
    }
  }
}