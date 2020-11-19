package com.batache.cars.model.adapter.main

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.batache.cars.R
import com.batache.cars.model.adapter.base.FullSizeModelWithHolder
import com.batache.cars.ui.controller.CarsController.CarsListener
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

@EpoxyModelClass(layout = R.layout.layout_search_owner_phone_number)
abstract class OwnerPhoneNumberSearchModel : FullSizeModelWithHolder<OwnerPhoneNumberSearchModel.OwnerPhoneNumberSearchHolder?>() {

  @EpoxyAttribute
  var listener: CarsListener? = null

  override fun bind(holder: OwnerPhoneNumberSearchHolder) {
    super.bind(holder)

    resetViewsState(holder)

    holder.submitBtn?.setOnClickListener {
      disableViews(holder)
      listener?.onSearchByOwnerPhoneNumber(holder.phoneNumberEt?.text.toString())
    }
  }

  private fun resetViewsState(holder: OwnerPhoneNumberSearchHolder) {
    holder.phoneNumberEt?.setText("")
    holder.submitBtn?.isEnabled = true
  }

  private fun disableViews(holder: OwnerPhoneNumberSearchHolder) {
    holder.submitBtn?.isEnabled = false
  }

  inner class OwnerPhoneNumberSearchHolder : EpoxyHolder() {
    var phoneNumberEt: TextInputEditText? = null
    var submitBtn: MaterialButton? = null

    override fun bindView(itemView: View) {
      phoneNumberEt = itemView.findViewById(R.id.et_phone_number)
      submitBtn = itemView.findViewById(R.id.btn_submit)
    }
  }
}