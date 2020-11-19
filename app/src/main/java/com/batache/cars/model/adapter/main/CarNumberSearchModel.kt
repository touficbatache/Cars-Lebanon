package com.batache.cars.model.adapter.main

import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.batache.cars.R
import com.batache.cars.model.adapter.base.FullSizeModelWithHolder
import com.batache.cars.model.adapter.main.CarNumberSearchModel.CarNumberSearchHolder
import com.batache.cars.ui.controller.CarsController.CarsListener
import com.google.android.material.button.MaterialButton

@EpoxyModelClass(layout = R.layout.layout_search_car_number)
abstract class CarNumberSearchModel : FullSizeModelWithHolder<CarNumberSearchHolder?>() {

  @EpoxyAttribute
  var listener: CarsListener? = null

  private var carLtr = "A"

  override fun bind(holder: CarNumberSearchHolder) {
    super.bind(holder)

    resetViewsState(holder)

    holder.carLetterTv?.setOnClickListener { view ->
      holder.carLetterTv?.context?.let { context ->
        val popup = PopupMenu(context, view)
        popup.menuInflater.inflate(R.menu.menu_car_letters, popup.menu)
        popup.show()
        popup.setOnMenuItemClickListener { item ->
          carLtr = item.title.toString()
          holder.carLetterTv?.text = getCarLetter(carLtr)
          updateLayoutColor(holder)
          true
        }
      }
    }

    holder.submitBtn?.setOnClickListener {
      disableViews(holder)
      listener?.onSearchByCarNumber(getCarLetter(carLtr), holder.carNbEt?.text.toString())
    }
  }

  private fun resetViewsState(holder: CarNumberSearchHolder) {
    carLtr = "A"
    holder.carLetterTv?.text = carLtr
    holder.carNbEt?.setText("")
    holder.submitBtn?.isEnabled = true
  }

  private fun updateLayoutColor(holder: CarNumberSearchHolder) {
    holder.carPlateTemplate?.context?.let { context ->
      var color = R.color.car_private
      when (getCarLetter(carLtr)) {
        "P" -> color = R.color.car_public
        "AP", "D" -> color = R.color.car_political
        "C", "C1" -> color = R.color.car_consulate
      }

      holder.carPlateTemplate?.setBackgroundColor(ContextCompat.getColor(context, color))


      var textColor = R.color.black
      if (getCarLetter(carLtr) == "P") {
        textColor = R.color.car_public
      }
      holder.carLetterTv?.setTextColor(ContextCompat.getColor(context, textColor))
      holder.carNbEt?.setTextColor(ContextCompat.getColor(context, textColor))
    }
  }

  private fun getCarLetter(arabicCarLetter: String): String {
    return arabicCarLetter.replace("\\(.*\\)".toRegex(), "").trim { it <= ' ' }
  }

  private fun disableViews(holder: CarNumberSearchHolder) {
    holder.submitBtn?.isEnabled = false
  }

  class CarNumberSearchHolder : EpoxyHolder() {
    var carPlateTemplate: AppCompatImageView? = null
    var carLetterTv: AppCompatTextView? = null
    var carNbEt: AppCompatEditText? = null
    var submitBtn: MaterialButton? = null

    override fun bindView(itemView: View) {
      carPlateTemplate = itemView.findViewById(R.id.car_plate_template)
      carLetterTv = itemView.findViewById(R.id.tv_car_letter)
      carNbEt = itemView.findViewById(R.id.et_car_number)
      submitBtn = itemView.findViewById(R.id.btn_submit)
    }
  }
}